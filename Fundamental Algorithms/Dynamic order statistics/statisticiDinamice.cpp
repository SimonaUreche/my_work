//Ureche Simona Elena - grupa 30224

//operatia de os_select(sa gasim al k element) se face mult mai rapid pe un array, decat pe un arbore; 
//complexitatea operatiei pe un array este O(1), pe cand pe un arbore binar de cautare este O(n);
//Os_trees au in plus campul de size, care marcheaza pentru fiecare nod dimensiunea subarborelui din care face parte;
//toate funzele au size 1; size-ul oricarui nod este suma size copii + 1;

//putem asemana os_select cu algoritmul de quickselect facut in unul din laboratoarele trecute; la fel ca la os_select, la quick_select cautam al i-lea cel mai mic element, dar dintr-un vector;

#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"
#define NR_TESTS 5
Profiler p("lab7");

typedef struct _Node
{
    int size;
	int key;
	struct _Node* left;
	struct _Node* right;
} Node;

Node* createNode(int key)
{
    Node* node = (Node*)malloc(sizeof(Node));
    if (node != NULL)
    {
        node->key = key;
        node->size = 1;
        node->left = NULL;
        node->right = NULL;
    }
    return node;
}
Node* buildTree(int array[], int start, int final, int dim)
{
    if (start > final)
    {
        return NULL;
    }

    int middle = (start + final) / 2;

    p.countOperation("Build_op", dim, 6);
    Node* root = createNode(array[middle]);

    p.countOperation("Build_op", dim, 2);
    root->left = buildTree(array, start, middle - 1, dim);
    root->right = buildTree(array, middle + 1, final, dim);

    p.countOperation("Build_op", dim, 2);
    if (root->left != NULL)
    {
        p.countOperation("Build_op", dim, 1);
        root->size += root->left->size;
    }
    if (root->right != NULL)
    {
        p.countOperation("Build_op", dim, 1);
        root->size += root->right->size;
    }

    return root;
}
void pprint(Node* root, int level = 0)
{
    if (root != NULL)
    {
        for (int i = 0; i < level; i++)
        {
            printf("    ");
        }

        printf("%d\n", root->key);

        pprint(root->left, level + 1);
        pprint(root->right, level + 1);
    }
}
void demo_build()
{
    int v[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18};
    int n = sizeof(v) / sizeof(v[0]);

    Node* root = buildTree(v, 0, n-1, n);
    pprint(root);
}

// initial suntem in nodul node si trebuie sa ii gasim rank-ul;
//cand rank-ul este egal cu al i-lea element, inseamna ca l-am gasit si il returnam;
//cand rank-ul este mai mic decat al i-lea element, cautam tot al i-lea element doar ca in subarborele stang;
//cand rank-ul este mai mare decat al i-lea element, cautam al 'i-rank' nod, doar ca in partea dreapra;
Node* OS_SELECT(Node* node, int i, int dim)
{
    int rank;
    p.countOperation("Select_op", dim , 1);
    if (node->left == NULL)
    {
        rank = 1;
    }
    else
    {
        p.countOperation("Select_op", dim, 1);
        rank = (node->left)->size + 1;
    }
    
    if (i == rank)
    {
        return node;
    }
    else
        if (i < rank)
        {
            return OS_SELECT(node->left, i, dim);
        }
        else
        {
            return OS_SELECT(node->right, i-rank, dim);

        }
}
void demo_os_select()
{
    int v[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18 };
    int n = sizeof(v) / sizeof(v[0]);
    int v1[1];

    Node* root = buildTree(v, 0, n - 1, n);

    for (int i = 0; i < 4; i++)
    {
        FillRandomArray(v, 1, 1, n, false, 1);

        Node* minKey = OS_SELECT(root, v[0], n);
        if (minKey != NULL)
        {
            printf("Elementul  cu a-%d-a cea mai mica cheie este: %d\n", v[0], minKey->key);
        }
        else
        {
            printf("Nu există element cu rangul %d.\n", v[0]);
        }
    }
}


Node* findMax(Node* node) {
    while (node->right != NULL) {
        node = node->right;
    }
    return node;
}
Node* OS_DELETE(Node* node, int i, int dim)
{
    if (node == NULL)
    {
        return NULL;
    }
    p.countOperation("Delete_op", dim, 1);
    if (i < node->key) 
    {
        p.countOperation("Delete_op", dim, 1);
        node->size = node->size - 1;
        node->left = OS_DELETE(node->left, i, dim);
    }
    else
    {
        p.countOperation("Delete_op", dim, 1);
        if (i > node->key)
        {
            p.countOperation("Delete_op", dim, 1);
            node->size = node->size - 1;
            node->right = OS_DELETE(node->right, i, dim);
        }

        else
        {
           // p.countOperation("Delete_op", dim, 1);
            if (node->left == NULL && node->right == NULL)
            {
                free(node);
                return NULL;
            }

            else
            {
               // p.countOperation("Delete_op", dim, 1);
                if (node->left == NULL)
                {
                    p.countOperation("Delete_op",dim, 1);
                    Node* pastrareNod = node->right;
                    free(node);
                    return pastrareNod;
                }
                else
                {
                   // p.countOperation("Delete_op", dim, 1);
                    if (node->right == NULL)
                    {
                        p.countOperation("Delete_op", dim, 1);
                        Node* pastrareNod = node->left;
                        free(node);
                        return pastrareNod;
                    }

                    else
                    {
                        p.countOperation("Delete_op", dim, 2);
                        Node* predecesor = findMax(node->left);
                        node->key = predecesor->key;
                        node->size = node->size - 1;
                        node->left = OS_DELETE(node->left, predecesor->key, dim);
                    }
                }
            }
        }
    }
    return node;
}
void demo_os_delete()
{
    int v[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18 };
    int n = sizeof(v) / sizeof(v[0]);

    Node* root = buildTree(v, 0, n - 1, n);

    //sterge frunza
    OS_DELETE(root, 2, n);
    printf("Dimensiunea arborelui dupa stergerea frunzei este: %d.\n", root->size);
    pprint(root);

    //sterge nod intern cu un copil
   OS_DELETE(root, 4, n);
   printf("Dimensiunea arborelui dupa stergerea nodului intern cu un copil este: %d.\n", root->size);
   pprint(root);

    //sterge radacina
   OS_DELETE(root, 6, n);
   printf("Dimensiunea arborelui dupa stergerea radacinii este: %d.\n", root->size);

   //printarea arborelui dupa cele 3 stergeri efectuate
   pprint(root);

}
void demo_os_delete_all()
{
    int v[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18 };
    int n = sizeof(v) / sizeof(v[0]);
   
    Node* root = buildTree(v, 0, n - 1, n);

        root = OS_DELETE(root, 1, n); 
        root = OS_DELETE(root, 2, n);
        root = OS_DELETE(root, 3, n);
        root = OS_DELETE(root, 4, n);
        root = OS_DELETE(root, 5, n);
        root = OS_DELETE(root, 6, n);
        root = OS_DELETE(root, 7, n);
        root = OS_DELETE(root, 8, n);
        root = OS_DELETE(root, 9, n);
        root = OS_DELETE(root, 10, n);
        root = OS_DELETE(root, 18, n);

    if (root == NULL) 
    {
        printf("Arborele este gol.\n");
    }
    else
    {
        printf("Dimensiunea arborelui dupa stergerile efectuate este: %d.\n", root->size);
        pprint(root);
    }

}

void perf()
{
    int v[10000];

    for (int n = 100; n < 10000; n += 100)
    {
        for (int test = 0; test < NR_TESTS; test++)
        {
            FillRandomArray(v, n, 1, n, true, 1);
            Node* root = buildTree(v, 0, n - 1, n);

            for (int i = 0; i < n; i++)
            {
                int indice = rand() % (n - i) + 1;
                Node* elementIndice = OS_SELECT(root, indice, n);
                OS_DELETE(root, indice, n);
                
                
            }
        }
    }
    p.divideValues("Build_op", NR_TESTS);
    p.divideValues("Select_op", NR_TESTS);
    p.divideValues("Delete_op", NR_TESTS);

    p.createGroup("SelectVsDelete", "Select_op", "Delete_op");
    p.createGroup("Build_op", "Build_op");
    p.showReport();
}

int main()
{
   //demo_build();
   //demo_os_select();
  // demo_os_delete();
   demo_os_delete_all();
   //perf();
    return 0;
}

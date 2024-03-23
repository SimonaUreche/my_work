//Ureche Simona Elena - grupa 30224

#include <stdlib.h>
#include "Profiler.h"
Profiler p("lab8");

//prima parte, pentru 5;

typedef struct _Node
{   
    int key;
	struct _Node* left;
	struct _Node* right;
    struct _Node* parent;
} Node;

//avem in plus si legatura catre parinte
Node* createNode(int key)
{
    Node* node = (Node*)malloc(sizeof(Node));
    if (node != NULL)
    {
        node->key = key;
        node->left = NULL;
        node->right = NULL;
        node->parent = NULL;
    }
    return node;
}

Node* buildTree(int array[], int start, int final, int radacina)
{
    if (start > final)
    {
        return NULL;
    }

    Node* root = createNode(array[radacina]);

    root->left = buildTree(array, start, radacina - 1, (start + radacina - 1) / 2);
    if (root->left != NULL)
    {
        root->left->parent = root;
    }

    root->right = buildTree(array, radacina + 1, final, (final + radacina + 1 ) / 2);
    if (root->right != NULL)
    {
        root->right->parent = root;
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

void recursiv(Node* root)
{
    if (root != NULL)
    {
        recursiv(root->left);
        printf("%d ", root->key);
        recursiv(root->right);
    }
}

//facem oarecum un 'contur' al arborelui;
//mergem pe conceptul de directie in care d=1 inseamnca ca venim de sus, d=2 din stanga si d=3 din dreapta;
//daca venim de sus o luam la stanga, daca venim din stanga o luam la dreapta, iar daca venim din dreapta jos o luam din nou in sus; 
void iterativ(Node* root)
{
    int d = 1;

    while (root != NULL || d != 3)
    {
        if (d == 1)
        {
            if (root->left != NULL)
            {
                root = root->left;
            }
            else
            {
                d = 2;
            }
        }
        else
        {
            if (d == 2)
            {
               printf("%d ", root->key);

                if (root->right != NULL)
                {
                    root = root->right;
                    d = 1;
                }
                else
                {
                    d = 3;
                }
            }

            else
            {
                if (d == 3)
                {
                    if (root->parent != NULL)
                    {
                        //daca nodul in care ne aflam este singurul copil din stanga al parintelui sau;
                        if (root == root->parent->left)
                        {
                            d = 2;
                        }

                        root = root->parent;
                    }
                    else
                    {
                        root = NULL;
                    }
                }
            }
        }
    }
}

void demo()
{
    int v[10];
    int v1[1];
    int n = (sizeof(v) / sizeof(v[0]));

    //generam un vector aleator
   FillRandomArray(v, n, 1, 20, true, 0);

   //generam un element aleator si il alegem ca radacina
   FillRandomArray(v1, 1, 0, n-1, true, 0);

   //construim arborele cu radacina aleasa random
   Node* root = buildTree(v, 0, n - 1, v1[0]);
   pprint(root);

   printf("Parcurgerea arborelui in inordine recursiv: ");
   recursiv(root);
   printf("\n");

   printf("Parcurgerea arborelui in inordine iterativ: ");
   iterativ(root);
}
int main()
{
   demo();
   return 0;
}

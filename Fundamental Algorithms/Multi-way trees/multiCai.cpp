//Ureche Simona Elena - grupa 30224

//La reprezentarea R1, din vectorul de parinti am afisat arborele multicai, nodurile acestuia fiind luate in ordine crescatoare pentru vectorul meu;

//Transformarea T1, din reprezentarea R1 In reprezentarea R2, am realizat-o in 4 etape, folosind 4 bucle 'for' consecutive, dar nu una in cealalta, avand in final o performanta de O(n);
// Etapa 1: alocam un array de noduri si il initializam(la a[i].key = i, a[i].nrChildren o sa fie 0 la inceput iar a[i].children o sa fie NULL;
// Etapa 2: numărăm câți copii are fiecare nod pentru ca in etapa 3 sa alocam memorie exact cat avem nevoie; tot aici identificam si radacina si salvam un pointer catre radacina;
// Etapa 3: parcurgem din nou vectorul si alocam memorie cata e nevoie, in functie de numarul de copii de la etapa 3; dupa ce am alocat memorie resetăm contorul nrChildren la 0;
// Etapa 4: refacem legaturile propriu zise; folosim contorul nrChildren resetat anterior pentru a adauga elemente;

//Transformarea T2, primeste ca paramentru un pointer de tip Node2 si stabileste legaturile intre R2 si R3; rezulta o complexitate de O(n), fiind alocata memorie pentru fiecare nod.
//Avem o functie care este apelata recursiv pentru fiecare copil; 

#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"
Profiler p("lab6");

//structura pentru reprezentarea R2
typedef struct _Node2
{
	int key;
	int nrChildren;
	struct _Node2** children;
} Node2;

//structura pentru reprezentarea R3
typedef struct _Node3
{
	int key;
	struct _Node3* left;
	struct _Node3* right;
} Node3;

//R1
void pprint1(int v[], int n, int k, int level = 0) 
{
    if (v[k] == -1)
    {
        printf("%d\n", k);
    }

    for (int i = 0; i < n; i++)
    {

        if (v[i] == k)
        {
           for (int j = 0; j < level + 1 ; j++) 
           {
                printf("    ");
           }
            
            printf("%d\n", i);
            pprint1(v, n, i, level + 1);

        }
    }
}
void demo_pprint1()
{

    printf("R1:\n");
   int v[] = { 10, 0, 10, 10, 7, 3, 5, 3, 3, 10, -1, 0 };
    int n = (sizeof(v) / sizeof(v[0]));

    int rootIndext;
    for (int i = 0; i < n; i++)
    {
        if (v[i] == -1) 
        {
            rootIndext = i;
            break;
        }
    }
    pprint1(v, n, rootIndext);
}

//R2
Node2* T1(int v[], int n)
{
 
    Node2* a = (Node2*)malloc(n * sizeof(Node2));
    for (int i = 0; i < n; i++) {
        a[i].key = i;
        a[i].nrChildren = 0;
        a[i].children = NULL;
    }


    Node2* root = NULL;
    for (int i = 0; i < n; i++)
    {
        if (v[i] == -1) 
        {
            root = &a[i];
        }
        else
        {
            a[v[i]].nrChildren++;
        }
    }


    for (int i = 0; i < n; i++) 
    {
        if (a[i].nrChildren > 0) 
        {
            a[i].children = (Node2**)malloc(a[i].nrChildren * sizeof(Node2*));
            a[i].nrChildren = 0; 
        }
    }


    for (int i = 0; i < n; i++)
    {
        if (v[i] != -1)
        {
            Node2* parent = &a[v[i]];
            parent->children[parent->nrChildren] = &a[i];
            parent->nrChildren++;
        }
    }

    return root;
}
void pprint2(Node2* root, int level = 0)
{

  for (int i = 0; i < level; i++)
  {
        printf("    ");
  }

    printf("%d\n", root->key);

    for (int i = 0; i < root->nrChildren; i++)
    {
        pprint2(root->children[i], level + 1);
    }
}
void demo_pprint2()
{
    printf("\nR2:\n");
    int v[] = { 10, 0, 10, 10, 7, 3, 5, 3, 3, 10, -1, 0 };
    int n = sizeof(v) / sizeof(v[0]);

    Node2* root = T1(v, n);
    pprint2(root);
}

//R3
Node3* T2(Node2* node2) 
{

    Node3* node3 = (Node3*)malloc(sizeof(Node3));
    node3->key = node2->key;
    node3->left = NULL;
    node3->right = NULL;

    
    if (node2->nrChildren > 0)
    {
        node3->left = T2(node2->children[0]);
    }

    //tinem minte care a fost ultimul copil
    Node3* lastNode = node3->left;
    for (int i = 1; i < node2->nrChildren; i++)
    {
       Node3* current = T2(node2->children[i]);
        lastNode->right = current; 
        lastNode = current; 
    }

    return node3;
}

void pprint3(Node3* root, int level = 0)
{
    if (root == NULL)
    {
        return;
    }

    for (int i = 0; i < level; i++) 
    {
        printf("    ");
    }

    printf("%d\n", root->key);

    pprint3(root->left, level + 1);
    pprint3(root->right, level);
}
void demo_pprint3()
{
    printf("\nR3:\n");
    int v[] = { 10, 0, 10, 10, 7, 3, 5, 3, 3, 10, -1, 0 };
    int n = sizeof(v) / sizeof(v[0]);

    Node2* R2 = T1(v, n);
    Node3* R3 = T2(R2);

    pprint3(R3);
}

int main() 
{
    //demo_pprint1();
    //demo_pprint2();
    demo_pprint3();
    return 0;
  
} 

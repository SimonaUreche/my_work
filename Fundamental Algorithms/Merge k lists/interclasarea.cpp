// Ureche Simona - CTI româna
// grupa 30224

//Complexitatea algoritmului de interclasare a k liste sortate crescator este de O (n * log k)

//Pentru cazul mediu static unde k variaza, avand pe rand una dintre cele 3 valori, 5, 10 si 100.
//Observam ca nuamrul de operatii creste pe masura ce valaorea lui k(numarul sirurilor crescatoare generate) creste; de asemenea si numarul elementelor din sirul inteclasat(n) , variaza intre 100 si 10000 cu increment de 100.
//Pentru k = 100, numarul de operatii este evident mai mare decat pentru k = 5 sau k = 10;

//In cazul al doilea, unde numarul elementelor din sirul inteclasat(n) este 10000, iar valoarea lui k variaza intre 10 si 500 cu increment de 10, observam ca numarul de operatii creste pe masura ce numarul de siruri generate crescator(k) creste;
//Tot aici, la fiecare pas al buclei for, se adauga o 'serie' nouă la la grafic;

#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"

Profiler p("lab4");

typedef struct Node {
    int id;
    struct Node* next;
} NodeT;

struct List {
    NodeT* head;
    NodeT* tail;
};

void swap(NodeT** x, NodeT** y) {
    NodeT* aux = *x;
    *x = *y;
    *y = aux;
}


int left(int k) {
    return 2 * k + 1;
}
int right(int k) {
    return 2 * k + 2;
}
void heapify(NodeT** Node, int* n, int i, int dim, Operation* assign, Operation* comp) {

    int largest = i;
    int l = left(i);
    int r = right(i);

   (*comp).count(1);
    if (l < *n && Node[l]->id < Node[largest]->id) 
    {
        largest = l;
    }
    else
    {

        largest = i;
    }

   (*comp).count(1);
    if (r < *n && Node[r]->id < Node[largest]->id) 
    {
        largest = r;
    }

  // (*comp).count(1);
    if (largest != i)
    {
      (*assign).count(3);
        swap(&Node[i], &Node[largest]);
        heapify(Node, n, largest, dim, assign, comp);
    }
}
void Build_min_heap(NodeT** Node, int* n, int dim, Operation* assign, Operation* comp)
{

    for (int i = dim / 2; i >= 0; i--)
    {
        heapify(Node, n, i, dim, assign, comp);
    }
}


List* merge_sorted_lists(List** L, int* heap_size, int length, int dim, Operation* assign, Operation* comp)
{
    //alocam dinamic o lista goala
    List* Res = (List*)malloc(sizeof(List));
    Res->head = NULL;
    Res->tail = NULL;

    NodeT** A = (NodeT**)malloc(length * sizeof(NodeT*));

    for (int i = 0; i < length; i++) {
        (*assign).count(1);
        A[i] = L[i]->head;
    }

    Build_min_heap(A, heap_size, *heap_size, assign, comp);

    //(*comp).count(1);
    while (*heap_size > 0) {

        NodeT* smallestNode = A[0];

       // (*comp).count(1);
        if (Res->tail != NULL)
        {
            //(*assign).count(2);
            Res->tail->next = smallestNode;
            Res->tail = Res->tail->next;
        }
        else 
        {
           // (*assign).count(2);
            Res->head = smallestNode;
            Res->tail = Res->head;
        }

        (*assign).count(1);
        A[0] = A[0]->next;

        (*comp).count(1);
        if (A[0] == NULL)
        {
           (*assign).count(1);
            A[0] = A[*heap_size - 1];
            (*heap_size)--;
        }

       // (*comp).count(1);
        if (*heap_size > 1)
        {
            heapify(A, heap_size, 0, *heap_size,assign, comp);
        }
    }

    //dezalocam memoria
    free(A);
    for (int i = 0; i < length; i++) 
    {
        free(L[i]);
    }
    free(L);

    return Res;
}

void insert_first(List** list, int* array, int dim)
{
    *list = (List*)malloc(sizeof(List));
    (*list)->head = NULL;
    (*list)->tail = NULL;

    for (int i = 0; i < dim; i++)
    {
        // alocam memorie pentru fiecare nod si il punem din array in lista
        NodeT* newNode = (NodeT*)malloc(sizeof(NodeT));
        newNode->id = array[i];
        newNode->next = NULL;

        // inseram nodul la începutul listei
        if ((*list)->head == NULL)
        {
            (*list)->head = newNode;
            (*list)->tail = newNode;
        }
        else
        {
            newNode->next = (*list)->head;
            (*list)->head = newNode;
        }
    }
}


void print_list(List* list) 
{

    NodeT* current = list->head;

    while (current != NULL)
    {
        printf("%d ", current->id);
        current = current->next;
    }
    printf("\n");
}

void generate_lists(List*** L, int n, int k) 
{

    if (n < k) {
        printf("Numarul total de elemente este mai mic decat numarul de liste");
        return;
    }

    *L = (List**)malloc(k * sizeof(List*));

    //generam doar cele k-1 liste in acest for pentru ca este posibil sa nu se imparta exact la n; tocmai de asta ultima lista o sa fie generata in afara for-ului
    for (int i = 0; i < k - 1; i++) 
    {
        int* array = (int*)calloc(n / k, sizeof(int));
       
        FillRandomArray(array, n / k, 1, 50, false, 2);
        //pastram de fiecare data sirul intr-o lista
        insert_first(&(*L)[i], array, n / k);
    
        free(array);
        print_list((*L)[i]);
    }

    // n/k + restul
    int* array = (int*)calloc((n / k) + (n % k), sizeof(int));
    FillRandomArray(array, (n / k) + (n % k), 1, 50, false, 2);

    insert_first(&(*L)[k - 1], array, (n / k) + (n % k));

    free(array);
    print_list((*L)[k - 1]);
}

void demo_generate_lists()
{
    List** L;

    int n = 20;
    int k = 4;

    generate_lists(&L, n, k);
}

void demo_sort_k_lists()
{
   
    List** L;
    List* Res;

    int n = 20;
    int k = 4;
    Operation atribuiri = p.createOperation("Atribuiri", k);
    Operation comparatii = p.createOperation("Comparatii", k);
   
    generate_lists(&L, n, k);
    Res = merge_sorted_lists(L, &k, k, n, &atribuiri, &atribuiri);
    print_list(Res);
}

void k_5(List*** L)
{
    for (int n = 100; n < 10000; n += 100)
    {
        Operation a = p.createOperation("k_5_Assign", n);
        Operation c = p.createOperation("k_5_Comp", n);
        int k = 5;
        generate_lists(L, n, k);
        merge_sorted_lists(*L, &k, k, n, &a, &c);
    }
    p.addSeries("k = 5", "k_5_Assign", "k_5_Comp");
}
void k_10(List*** L)
{
    for (int n = 100; n < 10000; n += 100)
    {
        Operation a = p.createOperation("k_10_Assign", n);
        Operation c = p.createOperation("k_10_Comp", n);
        int  k = 10;
        generate_lists(L, n, k);
        merge_sorted_lists(*L, &k, k, n, &a, &c);
    }
    p.addSeries("k = 10", "k_10_Assign", "k_10_Comp");
}
void k_100(List*** L)
{
    for (int n = 100; n < 10000; n += 100)
    {
        Operation a = p.createOperation("k_100_Assign", n);
        Operation c = p.createOperation("k_100_Comp", n);
        int k = 100;
        generate_lists(L, n, k);
        merge_sorted_lists(*L, &k, k, n, &a, &c);
    }
    p.addSeries("k = 100", "k_100_Assign", "k_100_Comp");
}
void perf_I()
{
    List** L = (List**)malloc(sizeof(List*));
    k_5(&L);
    k_10(&L);
    k_100(&L);

    p.createGroup("I", "k = 5", "k = 10", "k = 100");
    p.showReport();
}

void n_10000(List*** L)
{
    int n = 10000;
    for (int k = 10; k <= 500; k += 10)
    {
        Operation a = p.createOperation("n_10000_Assign", k);
        Operation c = p.createOperation("n_10000_Comp", k);
        generate_lists(L, n, k);
        int heap_size = k;
        merge_sorted_lists(*L, &heap_size, k, n, &a, &c);
    }
    p.addSeries("II", "n_10000_Assign", "n_10000_Comp");
}
void perf_II()
{
    List** L;
    n_10000(&L);
    p.showReport();
}


int main() {

    //demo_generate_lists();
   demo_sort_k_lists();
   
   //perf_I();
  // perf_II();

    return 0;
}





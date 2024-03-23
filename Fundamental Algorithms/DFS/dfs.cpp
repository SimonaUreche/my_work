//Ureche Simona Elena - grupa 30224

//back-edge ne ajuta sa stim daca avem sau nu cicluri
//sortarea topologica - scriem nodurile din graf intr-o anumita ordine
//daca avem un ciclu apar dependente circulare
//la sortare topologica facem pe 2 exemple; un graf cu cicluri si un graf fara cicluri
//luam toate nodurile in ordine descrescatoare a timpului de finish; cand setam tipmul de finish, coloram nodul cu negru

//pe langa sortarea topologica se cere algoritmul lui Tarjan

//partea de performanta doar pentru DFS;
//generam grafuri random insa fara sa se repete muchiile

#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"
Profiler p("lab11");
int timp;
int index;
int nrComponents;

//DFS apeleaza DFS-VISIT pentru fiecare nod care este alb => o padure, nu un arbore(mai multi arbori);
//discoveryTime: cand am gaist pentru prima data nodul, atunci cand el a devenit gri;
//finishTime: cand am terminat definitiv cu nodul respectiv, atunci cand el a devenit negru;
//primul nod cand il decsoperim primeste discoveryTime = 1, al doilea discoveryTime = 2..
enum Color
{
    COLOR_WHITE = 0,
    COLOR_GRAY,
    COLOR_BLACK
};

//fiecarui nod ii corespunde o culoare, 2 timpi(cand devine gri si cand devine negru), un parinte, un numar de vecini si un vector de vecini;
typedef struct _Node
{
    int key;
    Color color;
    int discoveryTime;
    int finishTime;
    struct _Node* parent;
    int adj_size;
    struct _Node** adj;

    //pentru TARJAN
    int index;
    int lowLink;
    bool onStack;
    int comp;
}Node;

typedef struct {
    int nrNodes;
    Node** v;
}Graph;

//DFS
void DFS_Visit(Graph* graph, Node* u, int dim)
{

    timp++;
    u->discoveryTime = timp;
    u->color = COLOR_GRAY;

    for (int i = 0; i < u->adj_size; i++)
    {
        p.countOperation("DFS-Operation", dim);
        Node* v = u->adj[i];
        if (v->color == COLOR_WHITE)
        {
            p.countOperation("DFS-Operation", dim);
            v->parent = u;
            DFS_Visit(graph, v, dim);
        }
    }
    u->color = COLOR_BLACK;
    timp++;
    u->finishTime = timp;

}
void DFS(Graph* graph, int dim)
{

    for (int i = 0; i < graph->nrNodes; i++)
    {
        p.countOperation("DFS-Operation", dim);
        Node* u = graph->v[i];
        u->color = COLOR_WHITE;
        u->parent = NULL;
    }

    for (int i = 0; i < graph->nrNodes; i++)
    {
        p.countOperation("DFS-Operation", dim);
        Node* v = graph->v[i];
        if (v->color == COLOR_WHITE)
        {
            p.countOperation("DFS-Operation", dim);
            DFS_Visit(graph, v, dim);
        }
    }
}

//sortarea topologica
typedef struct node {
    int key;
    struct node* next;
} NodeT;
NodeT* nod(int givenKey)
{
    NodeT* p = (NodeT*)malloc(sizeof(NodeT));
    p->next = NULL;
    p->key = givenKey;
    return p;
}
void print_list(NodeT* list) {
    while (list != NULL)
    {
        printf("%d ", list->key);
        list = list->next;
    }
}
void insert_first(NodeT** first, int givenKey) {
    NodeT* p = nod(givenKey);
    p->next = *first;
    *first = p;
}
void TopoSort(Graph* graph, Node* u, NodeT** list, int* ok)
{
    timp++;
    u->discoveryTime = timp;
    u->color = COLOR_GRAY;

    for (int i = 0; i < u->adj_size; i++)
    {
        Node* v = u->adj[i];
        if (v->color == COLOR_GRAY)
        {
            *ok = 1;
            return;
        }
        if (v->color == COLOR_WHITE)
        {
            v->parent = u;
            TopoSort(graph, v, list, ok);
        }
    }

    u->color = COLOR_BLACK;
    timp++;
    u->finishTime = timp;

    insert_first(list, u->key);
}
int DFS_Topo(Graph* graph, NodeT** list)
{
    int ok = 0;

    for (int i = 0; i < graph->nrNodes; i++)
    {
        Node* v = graph->v[i];
        if (v->color == COLOR_WHITE)
        {
            TopoSort(graph, v, list, &ok);
            if (ok)
            {
                return 0;
            }
        }
    }
    return 1;
}

void addEdge(Graph* graph, int a, int b)
{
    // Redimensionează vectorul de vecini pentru a adăuga noul nod
    graph->v[a]->adj = (Node**)realloc(graph->v[a]->adj, (graph->v[a]->adj_size + 1) * sizeof(Node*));

    //iar mai apoi facem legatura de "vecini" intre elementele a si b si crestem numarul de vecini al elemntului pe care il legam - a;
    graph->v[a]->adj[graph->v[a]->adj_size] = graph->v[b];
    graph->v[a]->adj_size++;
}

Node* createNode(int key)
{
    Node* node = (Node*)malloc(sizeof(Node));
    node->key = key;
    node->color = COLOR_WHITE;
    node->discoveryTime = 0;
    node->finishTime = 0;
    node->parent = NULL;
    node->adj = NULL;
    node->adj_size = 0;
    return node;
}
Graph* createGraph(int nrNodes)
{
    Graph* graph = (Graph*)malloc(sizeof(Graph));

    graph->nrNodes = nrNodes;
    graph->v = (Node**)malloc(nrNodes * sizeof(Node*));

    for (int i = 0; i < nrNodes; i++)
    {
        graph->v[i] = createNode(i);
    }

    return graph;
}
void demoDFS()
{
    Graph* graph = createGraph(6);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 3);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 1);
    addEdge(graph, 4, 3);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 5);

    printf("Graful initial:\n");
    for (int i = 0; i < 6; i++)
    {
        printf("Nodul %d are vecinii: ", i);
        for (int j = 0; j < graph->v[i]->adj_size; j++)
        {
            printf("%d ", graph->v[i]->adj[j]->key);
        }
        printf("\n");
    }

    DFS(graph, 6);

    printf("\nGraful dupa parcurgerea DFS:\n");
    for (int i = 0; i < 6; i++)
    {
        printf("Nodul %d are DiscoveryTime: %d si FinishTime: %d. \n", i, graph->v[i]->discoveryTime, graph->v[i]->finishTime);
    }

    printf("\nTimpul este %d.\n", timp);

}

//Sortarea topologica graf cu cicluri
void demoTopo1()
{
    Graph* graph = createGraph(6);
    NodeT** list = (NodeT**)malloc(graph->nrNodes * sizeof(NodeT*));
    for (int i = 0; i < graph->nrNodes; i++) {
        list[i] = NULL;
    }

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 3);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 1);
    addEdge(graph, 4, 3);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 5);

    if (DFS_Topo(graph, list))
    {
        printf("\nSortarea topologica este: \n");
        print_list(list[0]);
        printf("\n");
    }
    else
    {
        printf("\nGraful are cicluri, nu se poate realiza sortarea topologica.\n ");
        printf("\n");
    }

}
void demoTopo2()
{
    Graph* graph = createGraph(8);
    NodeT** list = (NodeT**)malloc(graph->nrNodes * sizeof(NodeT*));
    for (int i = 0; i < graph->nrNodes; i++) {
        list[i] = NULL;
    }

    addEdge(graph, 1, 0);
    addEdge(graph, 2, 1);
    addEdge(graph, 3, 1);
    addEdge(graph, 5, 2);
    addEdge(graph, 5, 4);
    addEdge(graph, 6, 3);
    addEdge(graph, 6, 4);
    addEdge(graph, 7, 5);
    addEdge(graph, 7, 6);

    printf("Graful initial:\n");
    for (int i = 0; i < 8; i++)
    {
        printf("Nodul %d are vecinii: ", i);
        for (int j = 0; j < graph->v[i]->adj_size; j++)
        {
            printf("%d ", graph->v[i]->adj[j]->key);
        }
        printf("\n");
    }

    if (DFS_Topo(graph, list))
    {
        printf("\nSortarea topologica este: ");
        print_list(list[0]);
        printf("\n");
    }
    else
    {
        printf("\nGraful are cicluri, nu se poate realiza sortarea topologica.\n ");
        printf("\n");
    }

}

//algoritmul lui Tarjan e bazat pe DFS + cativa pasi in plus;
//avem in plus index si lowLink; index ~ discoveryTime DFS; lowLink ~ id celui mai mic nod din arborede curent care poate fi atins de nodul curent(ne spune cand se inchide un ciclu)
//lowLink ne ajuta pentru a stii din ce componenta facem parte
NodeT** stack = NULL;
void push(NodeT** stack, int key)
{
    NodeT* p = (NodeT*)malloc(sizeof(NodeT));
    p->key = key;
    p->next = *stack;
    *stack = p;
}
int pop(NodeT** stack) {
    int x = -1;
    NodeT* p = *stack;
    if (p != NULL) {
        x = p->key;
        *stack = (*stack)->next;
        free(p);
    }
    return x;
}
int minimul(int a, int b)
{
    if (a < b)
    {
        return a;
    }
    return b;
}

void Strong_Connect(Graph* graph, Node* u)
{
    u->index = index;
    u->lowLink = index;
    index = index + 1;
    push(stack, u->key);
    u->onStack = true;

    for (int i = 0; i < u->adj_size; i++)
    {
        Node* v = u->adj[i];
        if (v->index == -1)
        {
            Strong_Connect(graph, v);
            u->lowLink = minimul(u->lowLink, v->lowLink);
        }
        else
        {
            if (v->onStack)
            {
                u->lowLink = minimul(u->lowLink, v->index);
            }
        }
    }

    if (u->lowLink == u->index)
    {
        nrComponents = nrComponents + 1;
        Node* v;
        do
        {
            v = graph->v[pop(stack)];
            v->onStack = false;
            v->comp = nrComponents;
        } while (v->key != u->key);
    }
}

void Tarjan(Graph* graph)
{

    for (int i = 0; i < graph->nrNodes; i++)
    {
        Node* u = graph->v[i];
        u->index = -1;
        u->lowLink = -1;
        u->onStack = FALSE;
        u->comp = 0;
    }

    for (int i = 0; i < graph->nrNodes; i++)
    {
        Node* u = graph->v[i];
        if (u->index == -1) {
            Strong_Connect(graph, u);
        }
    }
}
void demoTarjun()
{

    Graph* graph = createGraph(8);

    addEdge(graph, 0, 2);
    addEdge(graph, 1, 0);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 1);
    addEdge(graph, 2, 3);
    addEdge(graph, 2, 4);

    addEdge(graph, 3, 5);
    addEdge(graph, 4, 5);
    addEdge(graph, 4, 6);

    addEdge(graph, 5, 3);
    addEdge(graph, 5, 7);

    addEdge(graph, 6, 4);
    addEdge(graph, 6, 7);

    stack = (NodeT**)malloc(sizeof(NodeT*));

    printf("Graful initial:\n");
    for (int i = 0; i < 8; i++)
    {
        printf("Nodul %d are vecinii: ", i);
        for (int j = 0; j < graph->v[i]->adj_size; j++)
        {
            printf("%d ", graph->v[i]->adj[j]->key);
        }
        printf("\n");
    }

    Tarjan(graph);

    printf("\nGraful dupa parcurgerea Tarjan\n");
    for (int i = 0; i < 8; i++)
    {
        printf("Nodul %d are index: %d si loxLink: %d.\n", i, graph->v[i]->index, graph->v[i]->lowLink);
    }

    printf("\nComponentele tare conexe ale grafului sunt:\n");
    for (int i = 1; i <= nrComponents; i++)
    {
        printf("Componenta tare conexa formata din nodurile: ", i);
        for (int j = 0; j < graph->nrNodes; j++)
        {
            if (graph->v[j]->comp == i)
            {
                printf("%d ", graph->v[j]->key);
            }
        }
        printf("\n");
    }

    printf("\nNumarul componentelor conexe este: %d, iar indexul este: %d\n", nrComponents, index);

}

bool Duplicate(Graph* graph, int a, int b)
{
    //verificam daca exista deja muchie intre a si b
    for (int i = 0; i < graph->v[a]->adj_size; i++)
    {
        if (graph->v[a]->adj[i]->key == b)
        {
            return true;
        }
    }

    return false;
}
void perf1()
{
    int V = 100;
    int E;
    for (E = 1000; E <= 4500; E += 100)
    {
        Graph* graph = createGraph(V);
        for (int i = 0; i < E; i++)
        {
            int a = rand() % V;
            int b = rand() % V;

            int itsDuplicate = Duplicate(graph, a, b);

            while (itsDuplicate)
            {
                a = rand() % V + 1;
                b = rand() % V + 1;
                itsDuplicate = Duplicate(graph, a, b);
            }

            addEdge(graph, a, b);
        }

        DFS(graph, E);
    }
    p.showReport();
}

void perf2()
{
    int E = 4500;
    int V;
    for (V = 100; V <= 200; V += 10)
    {

        Graph* graph = createGraph(V);
        for (int j = 0; j < E; j++)
        {

            int a = rand() % V;
            int b = rand() % V;

            int itsDuplicate = Duplicate(graph, a, b);
            while (itsDuplicate)
            {
                a = rand() % V;
                b = rand() % V;
                itsDuplicate = Duplicate(graph, a, b);
            }

            addEdge(graph, a, b);
        }

        DFS(graph, V);
    }
    p.showReport();
}


int main() {

    // demoDFS();
     //demoTopo1();
    //demoTopo2();
     //demoTarjun();
     //perf1();
     //perf2();
    return 0;
}
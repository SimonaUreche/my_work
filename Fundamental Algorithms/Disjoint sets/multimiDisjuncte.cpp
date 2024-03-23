//Ureche Simona Elena - grupa 30224

//minimum spanning tree - determinam arborele minimal de acoperire cu algoritmul lui Kruskal: 
//determinam un subset de muchii, astfel incat mergand doar pe acele muchii sa ajungem de oriunde oriunde;
//avand cote pe muchii, luam arborele cu costul minim(suma cotelor muchiilor cea mai mica);

#include <stdlib.h>
#include "Profiler.h"
Profiler prof("lab9");

//multimile disjuncte se reprezinta ca un vector de parinti
void Make_Set(int* p, int* rank, int x, int dim)
{
    prof.countOperation("Kruskal", dim);
    p[x] = x;
    rank[x] = 0;
}

//rank reprezinta o aproximare a adancimii unui arbore; scopul este sa legam argorele mai mic de arborele mai mare;
void Unify(int* p, int* rank, int x, int y, int dim)
{
    prof.countOperation("Kruskal", dim);
    if (rank[x] > rank[y])
    {
        prof.countOperation("Kruskal", dim);
        p[y] = x;
    }
    else
    {
        prof.countOperation("Kruskal", dim);
        p[x] = y;
    }
    //singurul caz in care rank-ul creste este cand sunt ambele egale ca adancime
    prof.countOperation("Kruskal", dim);
    if (rank[x] == rank[y])
    {
        prof.countOperation("Kruskal", dim);
        rank[y] = rank[y] + 1;
    }   
}
//mergem din parinte in parinte, pana la radacina; cu find_set stim daca 2 elemente fac parte din aceeasi multime sau nu;
int Find_Set(int* p, int x, int dim)
{
    prof.countOperation("Kruskal", dim);
    if (x != p[x])
    {
        prof.countOperation("Kruskal", dim);
        p[x] = Find_Set(p, p[x], dim);
    }
    return p[x];
}

//uneste 2 multimi in una singura; practic unul din arbori il legam la celalalt 
void Reunion(int* p, int* rank, int x, int y, int dim)
{
    int reunion1 = Find_Set(p, x, dim);
    int reunion2 = Find_Set(p, y, dim);
    Unify(p, rank, reunion1, reunion2, dim);
}

void printSet(int* p, int* rank, int n, int dim)
{
    int* vizitat = (int*)calloc(n + 1, sizeof(int));

    for (int i = 1; i <= n; i++)
    {
        int root = Find_Set(p, i, dim);

        if (p[root] == root && vizitat[root] == 0)
        {
            printf("S%d = {(%d", root, i);

            for (int j = i + 1; j <= n; j++)
            {
                if (Find_Set(p, j, dim) == root)
                {
                    printf(", %d", j);
                }
            }
            printf(")} rank: %d\n", rank[root]);
            vizitat[root] = 1;
        }
    }
    printf("\n");
}

void demo_make_set()
{
    int n = 10;
    int* p = (int*)calloc(n , sizeof(int));
    int* rank = (int*)calloc(n, sizeof(int));

    printf("Cele 10 multimi create sunt:\n");
    for (int i = 1; i <= n; i++)
    {
        Make_Set(p, rank, i, n);
    }

    printSet(p, rank, n, n);
}

void demo_union_set()
{
    int n = 10;
    int* p = (int*)calloc(n, sizeof(int));
    int* rank = (int*)calloc(n, sizeof(int));

    for (int i = 1; i <= n;i++)
    {
        Make_Set(p, rank, i, n);
    }

    printf("Uniunea celor 10 multimi create anterior este:\n");
    for (int i = 1; i < n; i = i+2)
    {
        Reunion(p, rank, p[i], p[i+1], n);

    }
    printSet(p, rank, n, n);
}

typedef struct
{
    int a;
    int b;
    int w;
}Edge;
typedef struct 
{
    int nr_of_V, nr_of_E;
    Edge* edges;
} Graph;

//implementam quicksort hibridizat pt sortarea muchiilor
void swap(Edge x, Edge y)
{
    Edge aux = x;
    x = y;
    y = aux;
}
void InsertionSort(Edge v[], int n, int dim)
{
    for (int j = 1; j < n; j++)
    {
        prof.countOperation("Kruskal", dim, 2);
        int key = v[j].w;
        Edge k = v[j];
        int i = j - 1;

        while (i >= 0 && v[i].w > key)
        {
            prof.countOperation("Kruskal", dim);
            v[i + 1] = v[i];
            prof.countOperation("Kruskal", dim);
            i = i - 1;
        }
        prof.countOperation("Kruskal", dim);
        if (i != j - 1)
        {
            prof.countOperation("Kruskal", dim);
            v[i + 1] = k;
        }
    }
}
int partition(Edge v[], int low, int high, int dim)
{
    prof.countOperation("Kruskal", dim);
    int pivot = v[high].w;
    int i = low - 1;

    for (int j = low; j < high; j++)
    {
        prof.countOperation("Kruskal", dim);
        if (v[j].w < pivot)
        {
            i = i + 1;
            swap(v[i], v[j]);
            prof.countOperation("Kruskal", dim, 3);
        }
    }
    prof.countOperation("Kruskal", dim, 3);
    swap(v[i + 1], v[high]);
    return i + 1;
}
void quickSortHibridizat(Edge v[], int p, int r, int dim)
{
    if (p <= r)
    {
        //nr. de elemente din bucata curenta
        if (r - p + 1 <= 10)
        {
            InsertionSort(v + p, r - p + 1, dim);
        }
        else
        {
            int q = partition(v, p, r, dim);
            quickSortHibridizat(v, p, q - 1, dim);
            quickSortHibridizat(v, q + 1, r, dim);
        }
    }
}
//avem o structura care contine nr. de noduri, nr. de muchii si un vector de muchii pe care urmeaza sa le sortam crescator dupa 'pondere'(w);
//luam pe rand fiecare muchie si verificam daca inchide vreun ciclu cu muchiile deja luate; daca nu inchide niciun ciclu o luam in considerare, altfel nu o luam;
//ne oprim atunci cand avem n-1 muchii; un arbore de acoperire are tot timpul n-1 muchii pt. ca inafara de radacina, fiecare nod este conectat la un parinte;
//ne folosim de multimile disjuncte pt. a vedea daca o muchie inchide sau nu un ciclu;
//initial punem fiecare multime intr-un set separat; cand luam in considerare o muchie, verificam care este reprezentantul fiecarui varf, iar daca au reprezenanti diferiti inseamna ca fac parte din multimi diferite, deci muchia respectiva nu inchide un ciclu;
//atunci cand 2 noduri au acelasi reprezentant, nu luam muchia dintre ele in considerare deoarece ar inchide un ciclu;
Edge* Kruskal(Graph* G, int dim)
{
    Edge* A = (Edge*)calloc(G->nr_of_V - 1, sizeof(Edge));
    int index = 0;

    int* parent = (int*)calloc(G->nr_of_V, sizeof(int));
    int* rank = (int*)calloc(G->nr_of_V, sizeof(int));

    for (int i = 1; i <= G->nr_of_V; i++) {
        Make_Set(parent, rank, i, dim);
    }

    quickSortHibridizat(G->edges, 0, G->nr_of_E - 1, dim);

    for (int i = 0; i < G->nr_of_E; i++)
    {

        int u = G->edges[i].a;
        int v = G->edges[i].b;

         prof.countOperation("Kruskal", dim);
        if (Find_Set(parent, u, dim) != Find_Set(parent, v, dim)) 
        {
            prof.countOperation("Kruskal", dim);
            A[index] = G->edges[i];
            index++;
            Reunion(parent, rank, u, v, dim);
        }
    }
    return A;
}

Edge newEdge(int a, int b, int w) {
    Edge edge;
    edge.a = a;
    edge.b = b;
    edge.w = w;
    return edge;
}
void demo_Kruskal()
{
    Graph G;
    G.nr_of_V = 5;
    G.nr_of_E = 7;
    G.edges = (Edge*)calloc(G.nr_of_E, sizeof(Edge));

    //(a, b, w) - a - nodul din stanga, b - nodul din dreapta, w - ponderea
    G.edges[0] = newEdge(1, 2, 3);
    G.edges[1] = newEdge(1, 3, 7);
    G.edges[2] = newEdge(2, 3, 5);
    G.edges[3] = newEdge(2, 4, 4);
    G.edges[4] = newEdge(3, 4, 2);
    G.edges[5] = newEdge(3, 5, 9);
    G.edges[6] = newEdge(4, 5, 8);

    printf("Muchiile grafului sunt:\n");
    for (int i = 0; i < G.nr_of_E; i++)
    {
        printf("(%d, %d) %d\n", G.edges[i].a, G.edges[i].b, G.edges[i].w);
    }

    Edge* G_applyKruskal = Kruskal(&G, G.nr_of_E);

    printf("\nDrumul cel mai scurt de pondere minima aflat dupa aplicarea algoritmului Kruskal este:\n");
    for (int i = 0; i < G.nr_of_V - 1; i++) {
        printf("(%d, %d) %d\n", G_applyKruskal[i].a, G_applyKruskal[i].b, G_applyKruskal[i].w);
    }
}
void demo_Kruskal1()
{
    Graph G;
    G.nr_of_V = 6;
    G.nr_of_E = 10;
    G.edges = (Edge*)calloc(G.nr_of_E, sizeof(Edge));

    //{a, b, w} - a - nodul din stanga, b - nodul din dreapta, w - ponderea
    G.edges[0] = newEdge(1, 2, 7);
    G.edges[1] = newEdge(1, 3, 2);
    G.edges[2] = newEdge(1, 5, 5);
    G.edges[3] = newEdge(1, 6, 2);
    G.edges[4] = newEdge(2, 3, 9);
    G.edges[5] = newEdge(2, 4, 8);
    G.edges[6] = newEdge(3, 4, 1);
    G.edges[7] = newEdge(3, 5, 3);
    G.edges[8] = newEdge(4, 5, 4);
    G.edges[9] = newEdge(5, 6, 3);

    printf("Muchiile grafului sunt:\n");
    for (int i = 0; i < G.nr_of_E; i++)
    {
        printf("(%d, %d) %d\n", G.edges[i].a, G.edges[i].b, G.edges[i].w);
    }

    Edge* G_applyKruskal = Kruskal(&G, G.nr_of_E);

    printf("\nDrumul cel mai scurt de pondere minima aflat dupa aplicarea algoritmului Kruskal este:\n");
    for (int i = 0; i < G.nr_of_V - 1; i++) {
        printf("(%d, %d) %d\n", G_applyKruskal[i].a, G_applyKruskal[i].b, G_applyKruskal[i].w);
    }
}
void perf()
{
    for (int n = 100; n <= 10000; n += 100)
    {
      //  for (int test = 0; test < 15; test++)
       //{
            printf("%d ", n);
            Graph G;
            G.nr_of_V = n;
            G.nr_of_E = 4 * n;
            G.edges = (Edge*)calloc(G.nr_of_E, sizeof(Edge));

            G.edges[0] = newEdge(1, 2, rand() % G.nr_of_V);

            for (int i = 2; i < n; i++)
            {
                int random = rand() % i + 1;

                G.edges[i - 1] = newEdge(random, i + 1, rand() % G.nr_of_V);
            }

            for (int j = n; j < G.nr_of_E; j++) {

                int v1;
                int v2;
                FillRandomArray(&v1, 1, 1, G.nr_of_V - 1, false, 0);
                FillRandomArray(&v2, 1, 1, G.nr_of_V - 1, false, 0);
                Edge e = newEdge(v1, v2, rand() % G.nr_of_V);

                G.edges[j] = e;
            }
            Kruskal(&G, n);
            free(G.edges);
      //  }
    }
  //  prof.divideValues("Kruskal", 15);
    prof.showReport();
    
}

int main()
{
 // demo_make_set();
  //demo_union_set();
 demo_Kruskal();
  //demo_Kruskal1();
  //perf();
  return 0;
}
    
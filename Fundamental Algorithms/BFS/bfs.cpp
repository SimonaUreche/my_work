//Ureche Simona Elena - grupa 30224

#include <stdlib.h>
#include <string.h>
#include "bfs.h"
#define CAPACITY 100000
#define INFINITY 1000000

int get_neighbors(const Grid* grid, Point p, Point neighb[])
{
    int nOf_neighbors = 0;

    if (grid->mat[p.row][p.col] == 0)
    {
        //sus
        //verificam mai intai daca pozitia este in interiorul grilei, iar mai apoi daca aceasta este libera(valoarea din matrice la pozitia respectiva e 0).
        if (p.row - 1 < grid->rows && grid->mat[p.row - 1][p.col] == 0)
        {
            neighb[nOf_neighbors].row = p.row - 1;
            neighb[nOf_neighbors].col = p.col;
            nOf_neighbors++;
        }

        //jos
        if (p.row + 1 < grid->rows && grid->mat[p.row + 1][p.col] == 0)
        {
            neighb[nOf_neighbors].row = p.row + 1;
            neighb[nOf_neighbors].col = p.col;
            nOf_neighbors++;
        }

        //stanga
        if (p.col - 1 < grid->cols && grid->mat[p.row][p.col - 1] == 0)
        {
            neighb[nOf_neighbors].row = p.row;
            neighb[nOf_neighbors].col = p.col - 1;
            nOf_neighbors++;
        }

        //dreapta
        if (p.col + 1 < grid->cols && grid->mat[p.row][p.col + 1] == 0)
        {
            neighb[nOf_neighbors].row = p.row;
            neighb[nOf_neighbors].col = p.col + 1;
            nOf_neighbors++;
        }

    }
    //returnam numarul de vecini; maxim 4;
    return nOf_neighbors;
}

void grid_to_graph(const Grid* grid, Graph* graph)
{
    //we need to keep the nodes in a matrix, so we can easily refer to a position in the grid
    Node* nodes[MAX_ROWS][MAX_COLS];
    int i, j, k;
    Point neighb[4];

    //compute how many nodes we have and allocate each node
    graph->nrNodes = 0;
    for (i = 0; i < grid->rows; ++i) {
        for (j = 0; j < grid->cols; ++j) {
            if (grid->mat[i][j] == 0) {
                nodes[i][j] = (Node*)malloc(sizeof(Node));
                memset(nodes[i][j], 0, sizeof(Node)); //initialize all fields with 0/NULL
                nodes[i][j]->position.row = i;
                nodes[i][j]->position.col = j;
                ++graph->nrNodes;
            }
            else {
                nodes[i][j] = NULL;
            }
        }
    }
    graph->v = (Node**)malloc(graph->nrNodes * sizeof(Node*));
    k = 0;
    for (i = 0; i < grid->rows; ++i) {
        for (j = 0; j < grid->cols; ++j) {
            if (nodes[i][j] != NULL) {
                graph->v[k++] = nodes[i][j];
            }
        }
    }

    //compute the adjacency list for each node
    for (i = 0; i < graph->nrNodes; ++i) {
        graph->v[i]->adjSize = get_neighbors(grid, graph->v[i]->position, neighb);
        if (graph->v[i]->adjSize != 0) {
            graph->v[i]->adj = (Node**)malloc(graph->v[i]->adjSize * sizeof(Node*));
            k = 0;
            for (j = 0; j < graph->v[i]->adjSize; ++j) {
                if (neighb[j].row >= 0 && neighb[j].row < grid->rows &&
                    neighb[j].col >= 0 && neighb[j].col < grid->cols &&
                    grid->mat[neighb[j].row][neighb[j].col] == 0) {
                    graph->v[i]->adj[k++] = nodes[neighb[j].row][neighb[j].col];
                }
            }
            if (k < graph->v[i]->adjSize) {
                //get_neighbors returned some invalid neighbors
                graph->v[i]->adjSize = k;
                graph->v[i]->adj = (Node**)realloc(graph->v[i]->adj, k * sizeof(Node*));
            }
        }
    }
}

void free_graph(Graph* graph)
{
    if (graph->v != NULL) {
        for (int i = 0; i < graph->nrNodes; ++i) {
            if (graph->v[i] != NULL) {
                if (graph->v[i]->adj != NULL) {
                    free(graph->v[i]->adj);
                    graph->v[i]->adj = NULL;
                }
                graph->v[i]->adjSize = 0;
                free(graph->v[i]);
                graph->v[i] = NULL;
            }
        }
        free(graph->v);
        graph->v = NULL;
    }
    graph->nrNodes = 0;
}

//elementele corespunzatoare cozii sunt de tip Node*;
//fiecare element al cozii este un pointer care un Node*, care la randul lui pointeza la:
//Point position (celula din grila coresp. nodului)
//adjSize - nr. vecini nod respectiv(aflat cu functia get_neighb)
//adj[] - vectorul de vecini(neighb[])
//color - culoarea nodului, 0 la incepput pentru fiecare;
//dist - distanta de la nodul de start
//parent - pointer catre nodul parinte
typedef struct {
    Node* arrayNode[CAPACITY];
    int nr_elem;
    int head;
    int tail;
} Queue;
void new_queue(Queue* Q)
{
    Q->nr_elem = 0;
    Q->head = 0;
    Q->tail = 0;
}
void enqueue(Queue* Q, Node* key)
{
    if (Q->nr_elem == CAPACITY)
    {
        puts("Overflow");
        exit(1);
    }
    else
    {
        Q->arrayNode[Q->tail] = key;
        (Q->tail)++;
        (Q->nr_elem)++;
    }
}
Node* dequeue(Queue* Q)
{
    if (Q->nr_elem == 0)
    {
        puts("Underflow");
        exit(1);
    }
    else
    {
        Node* element = Q->arrayNode[Q->head];
        (Q->head)++;
        (Q->nr_elem)--;
        return element;
    }
}
//graph contine NrNodes si vectorul v[] care contine pointeri spre spre noduri 
void bfs(Graph* graph, Node* s, Operation* op)
{
    for (int i = 0; i < graph->nrNodes; i++)
    {
        if (op != NULL)
        {
            op->count(3);
        }
        graph->v[i]->color = COLOR_WHITE;
        graph->v[i]->dist = INFINITY;
        graph->v[i]->parent = NULL;
    }

    //nodul de start(radacina arborelui)
    if (op != NULL)
    {
        op->count(3);
    }
    s->color = COLOR_GRAY;
    s->dist = 0;
    s->parent = NULL;

    Queue Q;
    new_queue(&Q);
    enqueue(&Q, s);

    while (Q.nr_elem != 0)
    {
        if (op != NULL)
        {
            op->count();
        }
        Node* u = dequeue(&Q);
        if (op != NULL)
        {
            op->count();
        }
        for (int i = 0; i < u->adjSize; ++i)
        {
            if (op != NULL)
            {
                op->count();
            }
            Node* v = u->adj[i];


            if (op != NULL)
            {
                op->count();
            }
            if (v->color == COLOR_WHITE)
            {
                if (op != NULL)
                {
                    op->count(3);
                }
                v->color = COLOR_GRAY;
                v->dist = u->dist + 1;
                v->parent = u;
                enqueue(&Q, v);
            }
        }
        if (op != NULL)
        {
            op->count();
        }
        u->color = COLOR_BLACK;
    }
    // TOOD: implement the BFS algorithm on the graph, starting from the node s
   // at the end of the algorithm, every node reachable from s should have the color BLACK
   // for all the visited nodes, the minimum distance from s (dist) and the parent in the BFS tree should be set
   // for counting the number of operations, the optional op parameter is received
   // since op can be NULL (when we are calling the bfs for display purposes), you should check it before counting:
   // if(op != NULL) op->count();
};

//algoritmul bfs pe langa colorarea nodurilor, da nastere unui arbore, pentru ca fiecare nod are atributul de 'parent' care reprezinta parintele lui;(asemanator cu pprint pe un vector de parinti)
void pprint(int v[], int n, Point* p, int startIndex, int level = 0) {
  

    for (int i = 0; i < level; i++)
    {
        printf("        ");
    }

   printf("(%d, %d)\n", p[startIndex].row, p[startIndex].col);

    for (int i = 0; i < n; i++) {
        if (v[i] == startIndex) {
            pprint(v, n, p, i, level + 1);
        }
    }
}
void print_bfs_tree(Graph* graph)
{
    //first, we will represent the BFS tree as a parent array
    int n = 0; //the number of nodes
    int* p = NULL; //the parent array
    Point* repr = NULL; //the representation for each element in p

    //some of the nodes in graph->v may not have been reached by BFS
    //p and repr will contain only the reachable nodes
    int* transf = (int*)malloc(graph->nrNodes * sizeof(int));
    for (int i = 0; i < graph->nrNodes; ++i) {
        if (graph->v[i]->color == COLOR_BLACK) {
            transf[i] = n;
            ++n;
        }
        else {
            transf[i] = -1;
        }
    }
    if (n == 0) {
        //no BFS tree
        free(transf);
        return;
    }

    int err = 0;
    p = (int*)malloc(n * sizeof(int));
    repr = (Point*)malloc(n * sizeof(Node));
    for (int i = 0; i < graph->nrNodes && !err; ++i) {
        if (graph->v[i]->color == COLOR_BLACK) {
            if (transf[i] < 0 || transf[i] >= n) {
                err = 1;
            }
            else {
                repr[transf[i]] = graph->v[i]->position;
                if (graph->v[i]->parent == NULL) {
                    p[transf[i]] = -1;
                }
                else {
                    err = 1;
                    for (int j = 0; j < graph->nrNodes; ++j) {
                        if (graph->v[i]->parent == graph->v[j]) {
                            if (transf[j] >= 0 && transf[j] < n) {
                                p[transf[i]] = transf[j];
                                err = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    free(transf);
    transf = NULL;

    if (!err)
    {
        int startIndex = -1;
        //parcurgem vectorul de parinti pentru a gasi nodul radacina;
        //printam incepand cu nodul radacina(nodul de start)
        for (int i = 0; i < n; ++i) {
            if (p[i] == -1) {
                startIndex = i;
                break;
            }
        }
        pprint(p, n, repr, startIndex);
    
        // TODO: pretty print the BFS tree
        // the parrent array is p (p[k] is the parent for node k or -1 if k is the root)
        // when printing the node k, print repr[k] (it contains the row and column for that point)
        // you can adapt the code for transforming and printing multi-way trees from the previous labs
    }

    if (p != NULL) {
        free(p);
        p = NULL;
    }
    if (repr != NULL) {
        free(repr);
        repr = NULL;
    }
}

//avem punctul de start, punctul de final si trebuie sa completam vectorul de path[]
int shortest_path(Graph* graph, Node* start, Node* end, Node* path[])
{
    //apelam functia bfs, incepand cu nodul de start, rezultand un arbore care are distanta fiecarui nod fata de nodul de start setata, dar si legatura cu parintele facuta;
    bfs(graph, start, NULL);

    //nu exista drum
    if (start == NULL || end == NULL)
    {
        return -1;
    }

    //de la destinatie, parcurgem in oridine inversa arborele BFS pana ajungem la sursa
    Node* dest = end;
    for (int i = 0; i < end->dist; i++)
    {
        path[i] = dest;
        dest = dest->parent;
    }

    // Inversăm ordinea pentru a avea drumul de la start la end; practic inversam sagetile din graf
    for (int i = 0; i < end->dist / 2; i++) {
        Node* p = path[i];
        path[i] = path[end->dist - i - 1];
        path[end->dist - i - 1] = p;
    }
    return end->dist;

}
    // TODO: compute the shortest path between the nodes start and end in the given graph
    // the nodes from the path, should be filled, in order, in the array path
    // the number of nodes filled in the path array should be returned
    // if end is not reachable from start, return -1
    // note: the size of the array path is guaranteed to be at least 1000
void performance()
{
    int n, i;
    Profiler p("bfs");

    // vary the number of edges
    for (n = 1000; n <= 4500; n += 100) {
        Operation op = p.createOperation("bfs-edges", n);
        Graph graph;
        graph.nrNodes = 100;
        //initialize the nodes of the graph
        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
        // TODO: generate n random edges
        // make sure the generated graph is connected
        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);
    }

    // vary the number of vertices
    for (n = 100; n <= 200; n += 10) {
        Operation op = p.createOperation("bfs-vertices", n);
        Graph graph;
        graph.nrNodes = n;
        //initialize the nodes of the graph
        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
        // TODO: generate 4500 random edges
        // make sure the generated graph is connected
        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);
    }

    p.showReport();
}

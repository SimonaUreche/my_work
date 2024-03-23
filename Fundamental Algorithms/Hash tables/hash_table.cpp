//Ureche Simona Elena - grupa 30224

#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"
//#define N 10007
#define N 13

Profiler p("lab5");

typedef struct
{
    int id;
    char name[30];

} Htable;

int hash(int x, int i)
{
    int c1 = 1;
    int c2 = 1;
    return abs(x + c1 * i + c2 * i * i) % N;
}
void empty_array(Htable htable[N])
{
    for (int i = 0; i < N; i++)
    {
        htable[i].id = -1;
    }
}

int hash_insert(Htable htable[N], int x)
{
    int i = 0;
    do
    {
        int poz = hash(x, i);

        if (htable[poz].id == -1)
        {
            htable[poz].id = x;
            return poz;
        }
        else
        {
            i++;
        }
    } while (i != N);
}

int hash_search(Htable htable[N], int k, int* effort)
{
    int i = 0;
    do
    {
        *effort = i + 1;
        int poz = hash(k, i);

        if (htable[poz].id == k)
        {
            return poz;
        }
        else
            if (htable[poz].id == -1)
            {
                return -1;
            }
        i++;
    } while (i != N);
    *effort = i;
    return -1;
}
void hash_delete(Htable htable[N], int x)
{
    int eff;
    int pos = hash_search(htable, x, &eff);
    if (pos != -1)
    {
        htable[pos].id = -2;
        htable[pos].name[0] = 'D';
    }
}


void print_serch(Htable htable[N], int k, int* effort)
{
    int pozitieElementCautat = hash_search(htable, k, effort);
    if (pozitieElementCautat == -1)
    {
        printf("Elementul nu a fost inserat inca.");
    }
    else
    {
        printf("Elementul %d, se afla in hash table pe pozitia %d. ", htable[pozitieElementCautat].id, pozitieElementCautat);
    }
    printf("\n");
}
void print_array(Htable htable[N])
{
    for (int i = 0; i < N; i++)
    {
        printf("%d ", htable[i].id);
    }
    printf("\n");
}

//urmatoarea functie o sa fie testata pe N 13(date de intrare de dimensiuni mai mici)
void demo_insert(Htable htable[N])
{
    empty_array(htable);
    hash_insert(htable, 43);
    hash_insert(htable, 56);
    hash_insert(htable, 18);
    hash_insert(htable, 25);
    hash_insert(htable, 13);
    hash_insert(htable, 14);
    print_array(htable);
}
void demo_delete(Htable htable[N])
{
    hash_delete(htable, 43);
    hash_delete(htable, 56);
    printf("\nSirul dupa stergere este: ");
    print_array(htable);
}

void demo_search(Htable htable[N], int* effort)
{
    print_serch(htable, 43, effort);
    print_serch(htable, 56, effort);
    print_serch(htable, 18, effort);
    print_serch(htable, 25, effort);
    print_serch(htable, 13, effort);
    print_serch(htable, 14, effort);
    print_serch(htable, 0, effort);
}

void generate_table(double alpha)
{
    Htable htable[N];
    empty_array(htable);

    int n = (int)(alpha * N);
    int m = 3000;


    int* v = (int*)malloc((n + m / 2) * sizeof(int));
    int* indices = (int*)malloc((m / 2) * sizeof(int));


    int efort_maxim_gasite = -1;
    int efort_total_gasite = 0;
    int effort1 = 0;

    int efort_maxim_negasite = -1;
    int efort_total_negasite = 0;
    int effort2 = 0;


    FillRandomArray(v, n + m / 2, 1, 1000000, true, 0);
    FillRandomArray(indices, m / 2, 0, n - 1, true, 0);


    for (int i = 0; i < n; i++)
    {
        hash_insert(htable, v[i]);
    }

    for (int j = n; j < n + m / 2; j++)
    {
        int k = v[j];
        int position = hash_search(htable, k, &effort2);



        efort_total_negasite += effort2;
        if (effort2 > efort_maxim_negasite)
        {
            efort_maxim_negasite = effort2;
        }
    }

     for (int t = 0; t < m / 2; t++)
     {
            int k1 = v[indices[t]];
            int position = hash_search(htable, k1, &effort1);


            efort_total_gasite += effort1;
            if (effort1 > efort_maxim_gasite)
                efort_maxim_gasite = effort1;

       }


    
    // Calcul efort mediu
    double efort_mediu_gasit = efort_total_gasite / (double)1500;
    double efort_mediu_negasite = efort_total_negasite / (double)1500;

    printf("%.2lf\t\t\t\t\%.2lf\t\t\t\ %d\t\t    %.2lf\t\t\t\%d\n", alpha, efort_mediu_gasit , efort_maxim_gasite, efort_mediu_negasite, efort_maxim_negasite);

}
void perf()
{
    double alpha[] = { 0.8, 0.85, 0.9, 0.95, 0.99 };
    int n = sizeof(alpha) / sizeof(alpha[0]);

    printf("Factor de umplere\tEfort mediu gasite\tEfort maxim gasite\tEfort mediu ne-gasite\tEfort maxim ne-gasite\n");

    for (int i = 0; i < n; i++) {
        generate_table(alpha[i]);
    }
}

void demo()
{
    //N 13
    Htable htable[N];
    int eff;
    demo_insert(htable);
    printf("\n");
    demo_search(htable, &eff);
    demo_delete(htable);
}
void alpha_95()
{
    Htable htable[N];
    empty_array(htable);

    int v[] = { 43, 56, 18, 25, 13, 14, 7, 8, 9, 10, 11, 12, 13};
    int n = sizeof(v) / sizeof(v[0]);

    int alpha = int((95 / 100.0) * n);
    int effort = 0;
    int total_effort = 0;

    for (int i = 0; i < n; i++)
    {
        hash_insert(htable, v[i]);
    }
    printf("Htable dupa inserarea elementelor: ");
    print_array(htable);

    for (int i = 0; i < N; i++)
    {
       int poz = hash_search(htable, v[i], &effort);
        if (effort != 0 && v[i] == htable[poz].id)
        {
            printf("Efortul pentru a gasi elementul %d este de %d\n", htable[poz].id, effort);
            total_effort = total_effort + effort;
        }
        else
        {
            printf("Elementul nu a fost gasit.\n");
        }

    }

    double medie_efort = (double)total_effort / N;
    printf("\nEfortul mediu pentru un factor de umplere de 95 a unui Htable de 13 elemente este: %.2lf.", medie_efort);
}
int main()
{
   //N 13
   demo();
   //alpha_95();

    //N 10007
   //perf();

    return 0;
}


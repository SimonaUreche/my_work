//Ureche Simona - grupa 30224

//Bottom-Up(COMPLEXITATE ALGORITM)
//Complexitatea algoritmului Bottom-Up este O(nlgn) atat pentru cazul 'average', in care se aplica pe un sir ales aleator, cat si pe cazul 'worst', unde sirul este ordonat crescator.
//Pentru ca un sir ordonat descrescator este un max-heap, in cazul 'best' algoritmul o sa faca asignari si comparatii, insa intr-un numar considerabil mai mic.
//De asemenea, pentru cazul 'best' functia heapify nu o sa faca niciun apel recursiv.

//Top-Down(COMPLEXITATE ALGORITM)
//Complexitatea algoritmului Top-Down este O(n^2) atat atat pentru cazul 'average', in care sirul pe care se aplica este ales aleator, cat si pe cazul 'worst', unde sirul este ordonat crescator.
//In schimb, pentru cazul 'best'(un sir ordonat descrescator), desi numarul de asignari este mai mare pentru algoritmul Top-Down decat pentru algorimul Bottom-up, comparatii nu se realizeaza.(vezi algorim/grafice)

//Observam in cele din urma din grafice ca algoritmul Bottom-Up se dovedeste a fi mai eficint, avand un numar mai mic de operatii.

#include <stdio.h>
#include "Profiler.h"
#define NR_TESTS 5

Profiler p("lab2");

//cele 3 operatii pe care le utilizam pe 'arbore'
int left(int k)
{
	k = 2 * k + 1;
	return k;
}
int right(int k)
{
	k = 2 * k + 2;
	return k;
}
int parent(int k)
{
	k = (k - 1) / 2;
	return k;
}


//afisarea sub forma de 'arbore' - pretty-print
void pprint(int v[], int n, int k, int level = 0)
{
	for (int i = 0; i < level; i++)
	{
		printf("   ");
	}

	printf("%d\n", v[k]);
	int l = left(k);
	int r = right(k);
	if (l < n)
	{
		pprint(v, n, l, level + 1);
	}
	if (r < n)
	{
		pprint(v, n, r, level + 1);
	}
}



void heapify(int v[], int n, int i)
{
	int largest = i;
	int l = left(i);
	int r = right(i);

	p.countOperation("Heapify_Comp", n);
	if (l < n && v[l] > v[i])
	{
		largest = l;
	}
	else
	{
		largest = i;
	}

	
	p.countOperation("Heapify_Comp", n);
	if (r < n && v[r] > v[largest])
	{
		largest = r;
	}

	p.countOperation("Heapify_Comp", n);
	if (largest != i)
	{
		p.countOperation("Heapify_Assign", n, 3);
		int aux = v[i];
		v[i] = v[largest];
		v[largest] = aux;
		heapify(v, n, largest);
		
	}
}
void bottomUp(int v[], int n)
{
	for (int i = (n / 2) - 1; i >=0; i--)
	{
		heapify(v, n, i);
	}
}
void demoButtonUp()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	bottomUp(v, n);
	//in loc sa afisam sirul normal, apelam la functia pprint facuta anterior pentru a apela sub forma de 'arbore'
	pprint(v, n, 0);
}

void topDown(int v[], int n)
{
	for (int i = 1; i < n; i++)
	{
		p.countOperation("TopDown_Assign", n);
		int j = i;
		while (j > 0 && v[j] > v[parent(j)])
		{
			p.countOperation("TopDown_Comp", n, 2);
			int aux = v[j];
			v[j] = v[parent(j)];
			v[parent(j)] = aux;
			j = parent(j);
			p.countOperation("TopDown_Assign", n, 4);
		}
	}
}
void demoTopDown()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	topDown(v,n);
	//in loc sa afisam sirul normal, apelam la functia pprint facuta anterior pentru a apela sub forma de 'arbore'
	pprint(v, n, 0);
}



void heapsort(int v[], int n)
{
	bottomUp(v, n);
	for (int i = n-1 ; i > 0; i--)
	{
		int aux = v[0];
		v[0] = v[i];
		v[i] = aux;
		heapify(v, i, 0);
	}
}
void demoHeapsort()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	heapsort(v, n);
	for (int i = 0; i < n; i++)
	{
		printf("%d ", v[i]);
	}
	printf("\n");
}


void perf(int order)
{
	int v[10000];
	int u[10000];

	for (int n = 100; n <= 2000; n += 100)
	{
		for (int test = 0; test < NR_TESTS; test++)
		{
			printf("%d ", n);
			FillRandomArray(v, n, 10, 50000, false, order);
			memcpy(u, v, n * sizeof(int));
			bottomUp(v, n);
			topDown(u, n);
		}

	}
	printf("\n");

	p.divideValues("Heapify_Comp", NR_TESTS);
	p.divideValues("Heapify_Assign", NR_TESTS);
	p.divideValues("TopDown_Comp", NR_TESTS);
	p.divideValues("TopDown_Assign", NR_TESTS);

	p.addSeries("TotBU", "Heapify_Comp", "Heapify_Assign");
	p.addSeries("TotTD", "TopDown_Comp", "TopDown_Assign");

	p.createGroup("Total_Comp", "Heapify_Comp", "TopDown_Comp");
	p.createGroup("Total_Assign", "Heapify_Assign", "TopDown_Assign");

	p.createGroup("Total", "TotBU", "TotTD");

}
void perf_all() {
	perf(0);
	p.reset("best");
	perf(2);
	p.reset("worst");
	perf(1);
	p.showReport();
}

int main()
{
	demoButtonUp();
	demoTopDown();
	demoHeapsort();

	//perf_all();
	return 0;
}

//un vector sortat descrescator este un max-heap
//un max-heap nu e un vector sortat descrescator
//putem adauga un element in max-heap in timp logaritmic
//max-heapify - procedura recursiva in care primim un nod si ne ajuta sa il punem in heap si sa pastram proprietatea de max-heap in acelasi timp

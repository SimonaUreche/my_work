//Ureche Simona Elena - grupa 30224
//hibridizare: combinare a doi algoritmi astfel incat sa extragem ce e mai bun din fiecare
//Prin hibridizarem, in acest caz, intelegem ca atunci cand vectorul este de dimensiune mica folosim insertionSort, deoarece QuickSort nu e foarte eficient;
//varianta hibridizata e mai rapida deoerece pe bucati mai mici, scapa de atatea apeluri recursive;

#include <stdlib.h>
#include "Profiler.h"
Profiler p("lab8.1");
#define NR_TESTS 100
#define treshhold 10

//partea a doua;

void swap(int* x, int* y)
{
	int aux = *x;
	*x = *y;
	*y = aux;
}
void InsertionSort(int v[], int n, int dim, Operation op)
{
	for (int j = 1; j < n; j++)
	{
		int key = v[j];
		op.count();
		int i = j - 1;
		while (i >= 0 && v[i] > key)
		{
			op.count();
			v[i + 1] = v[i];
			op.count();
			i = i - 1;
		}
		op.count();
		 if (i != j - 1)
		{
			op.count();
			v[i + 1] = key;
		}

	}
}

int partition(int v[], int low, int high, int dim, Operation op)
{
	op.count();
	int pivot = v[high];
	int i = low - 1;

	for (int j = low; j < high; j++)
	{
		op.count();
		if (v[j] < pivot)
		{
			i = i + 1;
			swap(&v[i], &v[j]);
			op.count(3);
		}
	}
	op.count(3);
	swap(&v[i + 1], &v[high]);
	return i + 1;
}
void quickSort(int v[], int low, int high, int dim, Operation op)
{
	if (low < high)
	{
		int q = partition(v, low, high, dim, op);
		quickSort(v, low, q - 1, dim, op);
		quickSort(v, q + 1, high, dim, op);
	}
}

void quickSortHibridizat(int v[], int p, int r, int dim, Operation op)
{
	if (p <= r)
	{
		//nr. de elemente din bucata curenta
		if (r - p + 1 <= treshhold)
		{
			InsertionSort(v + p, r - p + 1, dim, op);
		}
		else
		{
			int q = partition(v, p, r, dim, op);
			quickSortHibridizat(v, p, q - 1, dim, op);
			quickSortHibridizat(v, q + 1, r, dim, op);

		}
	}
}

void demo()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 , 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);
	Operation op = p.createOperation("QuickSort", n);

	//testare algoritm
	quickSortHibridizat(v, 0, n - 1, n, op);
	printf("Quicksort hibridizat: ");
	for (int i = 0; i < n; i++)
	{
		printf("%d ", v[i]);
	}
	printf("\n");
}

void perf_timer()
{
	int* v = (int*)calloc(10000, sizeof(int));
	int* u = (int*)calloc(10000, sizeof(int));

	for (int n = 100; n < 2000; n += 100)
	{
		FillRandomArray(v, n, 10, 50000, false, 0);
		for (int TESTS = 0; TESTS < 100; TESTS++)
		{
			memcpy(u, v, n * sizeof(int));

			Operation op = p.createOperation("QuickSort", n);
			p.startTimer("QuickSort_Time", n);
			quickSort(v, 0, n - 1, n, op);
			p.stopTimer("QuickSort_Time", n);

			Operation op1 = p.createOperation("QuickSortHibridizat", n);
			p.startTimer("QuickSortHibridizat_Time", n);
			quickSortHibridizat(u, 0, n - 1, n, op1);
			p.stopTimer("QuickSortHibridizat_Time", n);
		}
	}

	p.createGroup("Time", "QuickSort_Time", "QuickSortHibridizat_Time");
	p.showReport();
}

void perf_op()
{
	int v[10000];
	int u[10000];


	for (int n = 100; n <= 10000; n += 100)
	{
		Operation op2 = p.createOperation("QuickSort", n);
		Operation op3 = p.createOperation("QuickSortHibridizat", n);

		for (int test = 0; test < NR_TESTS; test++)
		{
			FillRandomArray(v, n, 10, 50000, false, 0);
			memcpy(u, v, n * sizeof(int));

			quickSort(v, 0, n - 1, n, op2);
			quickSortHibridizat(u, 0, n - 1, n, op3);
		}
	}
	p.divideValues("QuickSort", NR_TESTS);
	p.divideValues("QuickSortHibridizat", NR_TESTS);

	p.createGroup("Operation", "QuickSort", "QuickSortHibridizat");
	p.showReport();
}
int main()
{
	demo();
	//perf_timer();
	//perf_op();
	return 0;
}

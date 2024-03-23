// Ureche Simona - CTI româna
// grupa 30224

// Heapsort - complexitatea algoritmului
// Indiferent de caz, heapsort isi pastreaza complexitatea de (O(nlogn)).
// Asadar, Heapsort este mai eficient decat Quicksort doar in cazul 'worst', unde avem un vector ordonat, iar pivotul este ales ca elementul de la final.

// Quicksort - pivot final
// Quicksort - este un algoritm mai rapid decat heapsort, fiind unul de tip recursiv; acesta este foarte eficinet pana dam de 'worst' case(atunci devine O(n^2)).
// Pentru cazul 'average' s-a dovedit a fi mai eficient decat Heapsort, dar in schimb pentru 'worst' cel din urma castiga, Quicksort indreptandu-se spre O(n^2).
// partea proasta la Quicksort este cand imparte in 2 bucati; una de 0 elemente si cealalta de n - 1('worst' case).

// Quicksort - pivot mijloc
// Daca alegem pivotul la mijloc si aplicam cei doi algoritmi pe un vector sortat crescator, Quicksort o sa fie mai eficient. (vezi grafice)

//* !alta strategie - hibridizare

#include <stdio.h>
#include "Profiler.h"
#define NR_TESTS 5

Profiler p("lab3");


void swap(int* x, int* y)
{
	int aux = *x;
	*x = *y;
	*y = aux;
}

// heapsort
int left(int k)
{
	return 2 * k + 1;
}
int right(int k)
{
	return 2 * k + 2;
}
void heapify(int v[], int n, int i, int dim)
{
	int largest = i;
	int l = left(i);
	int r = right(i);

	p.countOperation("Heapsort_Comp", dim);
	if (l < n && v[l] > v[i])
	{

		largest = l;
	}
	else
	{
		
		largest = i;
	}

	p.countOperation("Heapsort_Comp", dim);
	if (r < n && v[r] > v[largest])
	{

		largest = r;
	}

	p.countOperation("Heapsort_Comp", dim);
	if (largest != i)
	{
		p.countOperation("Heapsort_Assign", dim, 3);
		int aux = v[i];
		v[i] = v[largest];
		v[largest] = aux;
		heapify(v, n, largest, dim);

	}
}
void bottomUp(int v[], int n, int dim)
{
	for (int i = (n / 2) - 1; i >= 0; i--)
	{
		heapify(v, n, i, dim);
	}
}
void heapsort(int v[], int n, int dim)
{
	bottomUp(v, n, dim);
	for (int i = n - 1; i > 0; i--)
	{
		p.countOperation("Heapsort_Assign", dim, 3);
		int aux = v[0];
		v[0] = v[i];
		v[i] = aux;
		heapify(v, i, 0, dim);
	}
}
void demoHeapsort()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	heapsort(v, n, n);
	printf("Heapsort: ");
	for (int i = 0; i < n; i++)
	{
		printf("%d ", v[i]);
	}
	printf("\n");
}


//quiqSort cu pivot ales la final
int partitionFinalPivot(int v[], int low, int high, int dim)
{
	int pivot = v[high];
	int i = low - 1;

	for (int j = low; j <= high; j++)
	{
		p.countOperation("Quicksort_Comp", dim);
		if (v[j] < pivot)
		{
			i = i + 1;
			swap(&v[i], &v[j]);
			p.countOperation("Quicksort_Assign", dim, 3);
		}
	}
	p.countOperation("Quicksort_Assign", dim, 3);
	swap(&v[i+1], &v[high]);
	return i + 1;
}
void quickSortFinalPivot(int v[], int low, int high, int dim)
{
	p.countOperation("Quicksort_Comp", dim);
	if (low < high)
	{
		int q = partitionFinalPivot(v, low, high, dim);
		quickSortFinalPivot(v, low, q - 1, dim);
		quickSortFinalPivot(v, q + 1, high, dim);
	}
}
void demoQuickSortFinalPivot()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	quickSortFinalPivot(v, 0, n - 1, n);
	printf("Quicksort cu pivot la final: ");
	for (int i = 0; i < n; i++)
	{
		printf("%d ", v[i]);
	}
	printf("\n");
}


//quiqSort cu pivot ales la mijloc
int partitionMiddlePivot(int a[], int low, int high, int dim)
{
	int pivotIndex = low + (high - low) / 2;
	int pivot = a[pivotIndex];
	swap(&a[pivotIndex], &a[high]); 

	int i = low - 1;

	for (int j = low; j <= high - 1; j++)
	{
		p.countOperation("Quicksort_Comp2", dim);
		if (a[j] < pivot)
		{
			i = i + 1;
			swap(&a[i], &a[j]);
			p.countOperation("Quicksort_Assign2", dim, 3);
		}
	}
	p.countOperation("Quicksort_Assign2", dim, 3);
	swap(&a[i + 1], &a[high]);
	return i + 1;
}

void quickSortMiddlePivot(int v[], int low, int high, int dim)
{
	p.countOperation("Quicksort_Comp2", dim);
	if (low < high)
	{
		int q = partitionMiddlePivot(v, low, high, dim);
		quickSortMiddlePivot(v, low, q - 1, dim);
		quickSortMiddlePivot(v, q + 1, high, dim);
	}
}

void demoQuickSortMiddlePivot()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	quickSortMiddlePivot(v, 0, n - 1, n);
	printf("Quicksort cu pivot la mijloc: ");
	for (int i = 0; i < n; i++)
	{
		printf("%d ", v[i]);
	}
	printf("\n");
}


//cazul 'average' si 'worst' pentru Quicksort unde pivotul este ales ca ultim element
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
			heapsort(v, n, n);
			quickSortFinalPivot(u, 0, n-1, n);
		}

	}
	printf("\n");

	p.divideValues("Heapsort_Comp", NR_TESTS);
	p.divideValues("Heapsort_Assign", NR_TESTS);
	p.divideValues("Quicksort_Comp", NR_TESTS);
	p.divideValues("Quicksort_Assign", NR_TESTS);

	p.addSeries("TotHS", "Heapsort_Comp", "Heapsort_Assign");
	p.addSeries("TotQS", "Quicksort_Comp", "Quicksort_Assign");

	p.createGroup("Total_Comp", "Heapsort_Comp", "Quicksort_Comp");
	p.createGroup("Total_Assign", "Heapsort_Assign", "Quicksort_Assign");

	p.createGroup("Total", "TotHS", "TotQS");
}
void perf_average_worst() {
	perf(0);
	p.reset("worst");
	perf(2);
	p.showReport();
}


//cazul 'best' pentru Quicksort unde pivotul este ales ca elementul din mijloc
void perf_best()
{
	int v[10000];
	int u[10000];


	for (int n = 100; n <= 5000; n += 100)
	{
		for (int test = 0; test < NR_TESTS; test++)
		{
		printf("%d ", n);
		FillRandomArray(v, n, 10, 50000, false, 1);
		memcpy(u, v, n * sizeof(int));
		heapsort(v, n, n);
		quickSortMiddlePivot(u, 0, n - 1, n);
		}

	}
	printf("\n");

	p.divideValues("Heapsort_Comp", NR_TESTS);
	p.divideValues("Heapsort_Assign", NR_TESTS);
	p.divideValues("Quicksort_Comp2", NR_TESTS);
	p.divideValues("Quicksort_Assign2", NR_TESTS);

	p.addSeries("TotHS", "Heapsort_Comp", "Heapsort_Assign");
	p.addSeries("TotQS", "Quicksort_Comp2", "Quicksort_Assign2");

	p.createGroup("Total_Comp", "Heapsort_Comp", "Quicksort_Comp2");
	p.createGroup("Total_Assign", "Heapsort_Assign", "Quicksort_Assign2");

	p.createGroup("Total", "TotHS", "TotQS");
	p.showReport();
}

//Partea a doua a temei
//Implementare Quickselect
int quickSelect(int v[], int low, int high, int i);
void demoQuickSelect();

//Comparatie timp algoritm Insertion-sort
void InsertionSortIterative(int v[], int n);
void InsertionSortRecursive(int v[], int n);
void demo_Insertion();
void perf_time();
void perf_op();

int main()
{
	//HEAPSORT VS QUICKSORT
	//demoHeapsort();
	//demoQuickSortFinalPivot();
	//demoQuickSortMiddlePivot();
	//perf_average_worst();
	//perf_best();

	//QUICKSELECT
	demoQuickSelect();

	//ITERATIV VS RECURSIV
	//demo_Insertion();
	//perf_time();
	//perf_op();
	return 0;
}

//QUICKSELECT
int quickSelect(int v[], int low, int high, int i)
{
	if (low == high)
	{
		return v[low];
	}
	int pivotIndex = partitionFinalPivot(v, low, high, high);
	int k = pivotIndex - low + 1;
	if (i == k)
	{
		return v[pivotIndex];
	}
	else
	{
		if (i < k)
		{
			return quickSelect(v, low, pivotIndex - 1, i);
		}
		else
		{
			return quickSelect(v, pivotIndex +1, high, i-k);
		}
	}

}
void demoQuickSelect()
{
	int v[] = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
	int n = sizeof(v) / sizeof(v[0]);

	//testare algoritm
	int i = 5; //la i ii dam rank-ul elementului celui mai mic pe care il vrem din sir
	int minim = quickSelect(v, 0, n - 1, i);
	printf("Al %d-lea cel mai mic element din vector este: %d.", i, minim);

	printf("\n");
}


// Comparatie algoritm recursiv VS iterativ
//ITERATIV INSERTION-SORT
void InsertionSortIterative(int v[], int n)
{
	for (int j = 1; j < n; j++)
	{
		int key = v[j];
		int i = j - 1;
		p.countOperation("InsertionSort_Comp", n);
		while (i >= 0 && v[i] > key)
		{
			p.countOperation("InsertionSort_Assig", n);
			v[i + 1] = v[i];
			i = i - 1;
		}
		p.countOperation("InsertionSort_Comp", n);
		if (i != j - 1)
		{
			p.countOperation("InsertionSort_Assig", n);
			v[i + 1] = key;
		}

	}
}
//RECURSIV INSERTION-SORT
void InsertionSortRecursive(int v[], int n) 
{
	p.countOperation("InsertionSort_Comp1", n);
	if (n <= 1) {
		return;
	}

	InsertionSortRecursive(v, n - 1);


	int last = v[n - 1];
	int j = n - 2;
	p.countOperation("InsertionSort_Comp1", n);
	while (j >= 0 && v[j] > last) {

		p.countOperation("InsertionSort_Assig1", n);
		v[j + 1] = v[j];
		j--;
	}

	p.countOperation("InsertionSort_Assig1", n);
	v[j + 1] = last;
	
}

void demo_Insertion()
{
	int v[] = { 0, 11, 44, 2, 3, 1, 5, 8, 1 };
	int n = sizeof(v) / sizeof(v[0]);
	
	int u[] = { 0, 11, 44, 2, 3, 1, 5, 8, 1 };
	int m = sizeof(u) / sizeof(v[0]);

	InsertionSortIterative(v, n);
	InsertionSortRecursive(u, m);

	printf("Insertion-sort varianta iterativa: ");
	for (int i = 0; i < n; i++) {
		printf("%d ", v[i]);
	}
	printf("\n");

	printf("Insertion-sort varianta recursiva: ");
	for (int i = 0; i < m; i++) {
		printf("%d ", u[i]);
	}
	printf("\n");
}

void perf_time()
{
	int v[10000];
	int u[10000];
	int w[10000];

	p.disableCounters();
	for (int n = 100; n < 2000; n += 100)
	{
		FillRandomArray(v, n, 10, 50000, false, 0);
		p.startTimer("IterativeInsertionSort_Time", n);

		for (int TESTS = 0; TESTS <= 100; TESTS++)
		{
			memcpy(u, v, n * sizeof(int));
			InsertionSortIterative(u, n);
		}
		p.stopTimer("IterativeInsertionSort_Time", n);
			
		p.startTimer("RecursiveInsertionSort_Time", n);

		for (int TESTS = 0; TESTS <= 100; TESTS++)
		{
			memcpy(w, v, n * sizeof(int));
			InsertionSortRecursive(w, n);
		}
			p.stopTimer("RecursiveInsertionSort_Time", n);
		}


	p.divideValues("IterativeInsertionSort_Time", 100);
	p.divideValues("RecursiveInsertionSort_Time", 100);

	p.createGroup("Time", "IterativeInsertionSort_Time", "RecursiveInsertionSort_Time");
	p.showReport();
}

void perf_op()
{
	int v[10000], u[10000];

	for (int n = 100; n <= 2000; n += 100)
	{
		printf("%d ", n);
		FillRandomArray(v, n, 10, 50000);
		memcpy(u, v, n * sizeof(int));
		InsertionSortIterative(v, n);
		InsertionSortRecursive(u, n);
	}

	printf("\n");

	p.addSeries("Recursiv_op", "InsertionSort_Comp1", "InsertionSort_Assig1");
	p.addSeries("Iterativ_op", "InsertionSort_Comp", "InsertionSort_Assig");

	p.createGroup("Comparatii", "InsertionSort_Comp1", "InsertionSort_Comp");
	p.createGroup("Asignari", "InsertionSort_Assig1", "InsertionSort_Assig");
	p.createGroup("Total", "Recursiv_op", "Iterativ_op");

	p.showReport();
}
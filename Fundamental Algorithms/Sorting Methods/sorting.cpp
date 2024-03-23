//COMPLEXITATE ALGORITMI

//INSERTION - SORT (COMPLEXITATE ALGORITMI)
//In cazul algoritmului "INSERT SORT" complexitatea acestuluia atat din punct de vedere al comparatiilor, cat si din pct. de vedere al atribuirilor tinde la O(n), atat in cazul unui numar de operatii mai mic (de ex. cazul performantei 'best'), cat si in cazul unui numar de operatii mai mare ( de ex. in cazul performantei 'worst')'

//SELECTION - SORT 
//In cazul acestui algoritm, complexitatea din punct de vedere al numarlui de comparatii este aceeasi, adica O(n^2), indiferent de caz('avarage'/ 'best'/ 'worst' - vezi grafice)
//La atribuiri nu putem vorbi de aceeasi complexitate, aici fiind de O(1) in cazul unei performante mai ridicate ('best'), si O(n) in cazul unui numar mai mare de operatii;
//Obs!: ( in cazul performantei 'best', nu exista tabel pentru atribuiri la acest algoritm; sirul fiind deja ordonat nu se mai fac interschimbari de elemnte, deci nu exista atribuiri);

//BUBBLE - SORT
//In cazul acestui algoritm, complexitatea din punct de vedere al numarlui de comparatii este aceeasi, adica O(n^2), indiferent de caz('avarage'/ 'best'/ 'worst' - vezi grafice)
//In schimb, la atribuiri nu putem vorbi de aceeasi complexitate, aici fiind de O(1); 
//Obs! : ( in cazul performantei 'best', nu exista tabel pentru atribuiri la acest algoritm; sirul fiind deja ordonat nu se mai fac interschimbari de elemnte, deci nu exista atribuiri);


#include <stdio.h>
#include "Profiler.h"

Profiler p("lab1");

void BubbleSort(int v[], int n)
{
	for (int i = 0; i < n; i++)
	{
		for (int j =  n - 1; j >= i + 1; j--)
		{
			p.countOperation("BubbleSort_Comp", n);
			if (v[j] < v[j - 1])
			{
				int aux = v[j];
				v[j] = v[j-1];
				v[j-1] = aux;
				p.countOperation("BubbleSort_Assig", n, 3);
			}
		}
	}

}

void InsertionSort(int v[], int n)
{

	for (int j = 1; j < n ; j++)
	{
		int key = v[j];
		p.countOperation("InsertionSort_Assig", n);
		int i = j - 1;
		while (i >= 0 && v[i] > key)
		{
			p.countOperation("InsertionSort_Comp", n);
			v[i + 1] = v[i];
			p.countOperation("InsertionSort_Assig", n);
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

void SelectionSort(int v[], int n)
{
	for (int i = 0; i < n; i++)
	{
		int min = i;

		for (int j =  i + 1 ; j < n; j++)
		{
			p.countOperation("SelectionSort_Comp", n);
			if (v[j] < v[min])
			{
				min = j;
			}
		}
		if (i != min)
		{
			int aux = v[i];
			v[i] = v[min];
			v[min] = aux;
			p.countOperation("SelectionSort_Assig", n, 3);
		}
	}

}

// testarea algorimului bubble
void demo_Bubble()
{
	int v[] = { 0, 11, 44, 2, 3, 1, 5, 8, 1 };
	int n = sizeof(v) / sizeof(v[0]);

	BubbleSort(v, n);

	for (int i = 0; i < n; i++) {
		printf("%d ", v[i]);
	}
	printf("\n");
}

//testarea algoritmului insertion
void demo_Insertion()
{
	int v[] = {0, 11, 44, 2, 3, 1, 5, 8, 1};
	int n = sizeof(v) / sizeof(v[0]);

	InsertionSort(v, n);

	for (int i = 0; i < n; i++) {
		printf("%d ", v[i]);
	}
	printf("\n");
}

//testarea algoritmului selection
void demo_Selection()
{
	int v[] = { 0, 11, 44, 2, 3, 1, 5, 8, 1 };
	int n = sizeof(v) / sizeof(v[0]);

	SelectionSort(v, n);

	for (int i = 0; i < n; i++) {
		printf("%d ", v[i]);
	}
	printf("\n");
}

// functia urmatoare genereaza grafice pe baza unui sir generat random prin intermediul functiei FillRandomArray, acesta fiind un sir NEORDONAT(ordine aleatoare)
void perf_avarage()
{
	int v[10000];
	int u[10000];
	int w[10000];

	for (int n = 100; n <= 2000; n += 100)
	{
		printf("%d ", n);
		FillRandomArray(v, n);
		memcpy(u, v, n * sizeof(int));
		InsertionSort(u, n);
		memcpy(w, v, n * sizeof(int));
		BubbleSort(w, n);
		SelectionSort(v, n);
	}
	printf("\n");



	p.addSeries("totI", "InsertionSort_Assig", "InsertionSort_Comp");
	p.addSeries("totB", "BubbleSort_Assig", "BubbleSort_Comp");
	p.addSeries("totS", "SelectionSort_assig", "SelectionSort_Comp");



	p.createGroup("asignari", "InsertionSort_Assig", "BubbleSort_Assig", "SelectionSort_Assig");
	p.createGroup("comparatii", "InsertionSort_Comp", "BubbleSort_Comp", "SelectionSort_Comp");
	p.createGroup("total", "totI", "totS", "totB");

	p.showReport();
}


// functia urmatoare genereaza grafice pe baza unui sir generat random prin intermediul functiei FillRandomArray, acesta fiind un sir ORDONAT CRESCATOR
void perf_best()
{
	int v[10000];
	int u[10000];
	int w[10000];

	for (int n = 100; n <= 2000; n += 100)
	{
		printf("%d ", n);
		FillRandomArray(v, n, 10, 5000, false, 1);
		memcpy(u, v, n * sizeof(int));
		InsertionSort(u, n);
		memcpy(w, v, n * sizeof(int));
		BubbleSort(w, n);
		SelectionSort(v, n);
	}
	printf("\n");



	p.addSeries("totI", "InsertionSort_Assig", "InsertionSort_Comp");
	p.addSeries("totB", "BubbleSort_Assig", "BubbleSort_Comp");
	p.addSeries("totS", "SelectionSort_assig", "SelectionSort_Comp");



	p.createGroup("asignari", "InsertionSort_Assig", "BubbleSort_Assig", "SelectionSort_Assig");
	p.createGroup("comparatii", "InsertionSort_Comp", "BubbleSort_Comp", "SelectionSort_Comp");
	p.createGroup("total", "totI", "totS", "totB");

	p.showReport();
}

// functia urmatoare genereaza grafice pe baza unui sir generat random prin intermediul functiei FillRandomArray, acesta fiind un sir ORDONAT DESCRESCATOR
void perf_worst()
{
	int v[10000];
	int u[10000];
	int w[10000];

	for (int n = 100; n <= 2000; n += 100)
	{
		printf("%d ", n);
		FillRandomArray(v, n, 10, 5000, false, 2);
		memcpy(u, v, n * sizeof(int));
		InsertionSort(u, n);
		memcpy(w, v, n * sizeof(int));
		BubbleSort(w, n);
		SelectionSort(v, n);
	}
	printf("\n");


	// facem o serie cu numarul de asignari si cu numarul de comparatii de la BubbleSort si de la InsertionSort.
	p.addSeries("totI", "InsertionSort_Assig", "InsertionSort_Comp");
	p.addSeries("totB", "BubbleSort_Assig", "BubbleSort_Comp");
	p.addSeries("totS", "SelectionSort_assig", "SelectionSort_Comp");



	p.createGroup("asignari", "InsertionSort_Assig", "BubbleSort_Assig", "SelectionSort_Assig");
	p.createGroup("comparatii", "InsertionSort_Comp", "BubbleSort_Comp", "SelectionSort_Comp");
	p.createGroup("total", "totI", "totS", "totB");
	// facem o serie cu numarul de asignari si cu numarul de comparatii de la BubbleSort, InsertionSort si de la SelectionSort.

	p.showReport();
}

int main()
{
	//demo_Insertion();
	//demo_Bubble();
	//demo_Selection();
	
	//perf_avarage();
	//perf_best();
	perf_worst();

	return 0;
}
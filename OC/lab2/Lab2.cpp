#include <iostream>
#include <windows.h>
#include <thread>
#include <chrono>
#include <mutex>

using namespace std;

mutex mtx;

struct my_array {
	int size;
	int min_element = INT_MAX;
	int max_element = INT_MIN;
	int average = 0;
	int* arr;
	my_array(int size) {
		this->size = size;
		arr = new int[size];
	}

	~my_array() {
		delete[] arr;
	}
};

void func_min_max(my_array* a) {
	//cout << "Min_Max -- " << this_thread::get_id() << "\n";
	
	for (int i = 0; i < a->size; i++) {
		if (a->min_element > a->arr[i]) {
			a->min_element = a->arr[i];
		}
		this_thread::sleep_for(chrono::milliseconds(7));

		if (a->max_element < a->arr[i]) {
			a->max_element = a->arr[i];
		}
		this_thread::sleep_for(chrono::milliseconds(7));
	}

	mtx.lock();
	cout << a->max_element << " " << a->min_element << "\n";
	mtx.unlock();
}

void func_average(my_array* a) {
	//cout << "Average -- " << this_thread::get_id() << "\n";
	for (int i = 0; i < a->size; i++) {
		a->average += a->arr[i];
		this_thread::sleep_for(chrono::milliseconds(12));
	}
	a->average /= a->size;
	mtx.lock();
	cout << a->average << "\n";
	mtx.unlock();
}



int main() {
	setlocale(LC_ALL, "rus");
	int n;
	cout << "Size of array: ";
	cin >> n;
	my_array a(n);
	cout << "Enter array:\n";
	for (int i = 0; i < n; i++) {
		cin >> a.arr[i];
	}

	thread min_max(func_min_max, &a);
	thread average(func_average, &a);
	min_max.join();
	average.join();

	for (int i = 0; i < n; i++) {
		if (a.arr[i] == a.max_element || a.arr[i] == a.min_element) {
			a.arr[i] = a.average;
		}
	}

	for (int i = 0; i < n; i++) {
		cout << a.arr[i] << " ";
	}
	return 0;
}
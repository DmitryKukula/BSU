#include <iostream>
#include <vector>
#include <thread>
#include <condition_variable>
#include <mutex>
#include <algorithm>

using namespace std;

condition_variable cv;
mutex mtx;
vector<bool> thread_work;
vector<bool> crossed_out_threads;

void marker(int thread_ID, int a[], int size) {
	unique_lock<mutex> lock(mtx);
	srand(thread_ID);
	int marked_count = 0;

	while (true) {
		int random_number = rand();
		int index = random_number % size;

		cv.wait(lock, [&]() { return thread_work[thread_ID]; });

		if (a[index] == 0) {
			std::this_thread::sleep_for(std::chrono::milliseconds(5));
			a[index] = thread_ID + 1;
			std::this_thread::sleep_for(std::chrono::milliseconds(5));
			marked_count++;
		}
		else {
			cout << "Номер потока: " << thread_ID + 1 << "\n";
			cout << "Количество помеченных элементов: " << marked_count << "\n";
			cout << "Не смог пометить элемент номер: " << index << "\n";
			cout << "\t===============================\n";
			thread_work[thread_ID] = false;
			cv.notify_all();
		}
	}
	cv.notify_all();
}

int main() {
	setlocale(LC_ALL, "rus");
	int n, number_threads;
	cout << "Введите размер массива: ";
	cin >> n;
	cout << "Введите количество потоков: ";
	cin >> number_threads;
	cout << "\n\n";

	vector<int> a(n, 0);
	vector<thread> threads;
	thread_work.resize(number_threads, true);
	crossed_out_threads.resize(number_threads, true);
	vector<int> closed_threads(number_threads, 0);

	for (int i = 0; i < number_threads; i++) {
		threads.emplace_back(marker, i, a.data(), n);
	}

	cv.notify_all();

	while (true) {
		unique_lock<mutex> lock(mtx);
		cv.wait(lock, [&]() {
			return (std::find(thread_work.begin(), thread_work.end(), 1) == thread_work.end());
			});

		if (find(crossed_out_threads.begin(), crossed_out_threads.end(), 1) == crossed_out_threads.end()) {
			cout << "\n\n\t===============================\n";
			cout << "\tВы закрыли все потоки\n";
			cout << "\t===============================\n\n\n";
			break;
		}

		for (int i = 0; i < n; i++) {
			cout << a[i] << " ";
		}
		cout << "\n";

		cout << "\nВведите порядковый номер потока, который хотите закрыть (или 0 для выхода): ";
		int number_thread_to_close;
		cin >> number_thread_to_close;

		if (number_thread_to_close == 0) {
			break;
		}
		if (number_thread_to_close < 1 || number_thread_to_close > number_threads) {
			cerr << "ОШИБКА ВВОДА\n";
			continue;
		}
		else {
			for (auto t : thread_work)
			{
				t = true;
			}
			crossed_out_threads[number_thread_to_close - 1] = false;
		}

		for (int i = 0; i < n; i++) {
			if (a[i] == number_thread_to_close) {
				a[i] = 0;
			}
			cout << a[i] << " ";
		}
		cout << "\n";

		thread_work.resize(number_threads, true);
		for (int i = 0; i < number_threads; i++) {
			thread_work[i] = thread_work[i] && crossed_out_threads[i];
		}
		cv.notify_all();
	}

	for (int i = 0; i < number_threads; i++) {
		threads[i].detach();
	}
	return 0;
}
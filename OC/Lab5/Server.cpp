#include <iostream>
#include <fstream>
#include <vector>
#include <windows.h>
#include <conio.h>
#include <algorithm>
#include <string>

using namespace std;

HANDLE hStarted;

struct employee
{
	int num;
	char name[10];
	double hours;
};

HANDLE hPipe;
HANDLE hThreads;
HANDLE* hSemaphore;
vector <employee> emps;
int number_of_employees;
string file_name;

DWORD WINAPI operations(LPVOID pipe) {
	HANDLE hPipe = (HANDLE)pipe;
	DWORD dwBytesRead;
	DWORD dwBytesWrite;

	int message;
	while (true) {
		if (!ReadFile(
			hPipe, // дескриптор канала
			&message, // адрес буфера для ввода данных
			sizeof(message), // число читаемых байтов
			&dwBytesRead, // число прочитанных байтов
			NULL)) // передача данных синхронная
		{
			//cout << "Data reading from the named pipe failed.\n";
		}
		else {
			int ID = message / 10;
			int chosen_option = message % 10;

			if (chosen_option == 1) {
				WaitForSingleObject(hSemaphore[ID - 1], INFINITE);

				employee* emp_to_push = new employee();

				emp_to_push->num = emps[ID - 1].num;
				emp_to_push->hours = emps[ID - 1].hours;
				strcpy_s(emp_to_push->name, emps[ID - 1].name);

				bool checker = WriteFile(hPipe, emp_to_push, sizeof(employee), &dwBytesWrite, NULL);
				if (checker) {
					cout << "Data to modify was sent.\n";
				}
				else {
					cout << "Data to modify wasn't sent.\n";
				}

				ReadFile(hPipe, emp_to_push, sizeof(employee), &dwBytesWrite, NULL);

				emps[ID - 1].hours = emp_to_push->hours;
				strcpy_s(emps[ID - 1].name, emp_to_push->name);

				ofstream file;
				file.open(file_name);

				for (int i = 0; i < number_of_employees; i++)
					file << emps[i].num << " " << emps[i].name << " " << emps[i].hours << "\n";
				file.close();

				int msg;
				ReadFile(hPipe, &msg, sizeof(msg), &dwBytesWrite, NULL);
				if (msg == 1) {
					ReleaseSemaphore(hSemaphore[ID - 1], 1, NULL);
				}
			}
		}
	}
	DisconnectNamedPipe(hPipe);
	CloseHandle(hPipe);

}

int main() {
	cout << "Input file name: ";
	cin >> file_name;

	cout << "Input number of employees: ";
	cin >> number_of_employees;

	ofstream file;
	emps.resize(number_of_employees);

	for (int i = 0; i < number_of_employees; i++)
	{
		cout << "\nInput " << i + 1 << " employee id: ";
		cin >> emps[i].num;
		cout << "Input employee name: ";
		string tmp;
		cin >> emps[i].name;
		cout << "Input employee hours: ";
		cin >> emps[i].hours;
	}

	file.open(file_name);
	for (int i = 0; i < number_of_employees; i++)
		file << emps[i].num << " " << emps[i].name << " " << emps[i].hours << "\n";
	file.close();

	ifstream file_input;
	file_input.open(file_name);

	cout << "\n";
	for (int i = 0; i < number_of_employees; i++)
	{
		int id;
		char name[10];
		double hours;
		file_input >> id >> name >> hours;
		cout << "ID: " << id << "\tName: " << name << "\tHours: " << hours << "\n";

	}
	file_input.close();

	hPipe = CreateNamedPipe(L"\\\\.\\pipe\\pipe_name", PIPE_ACCESS_DUPLEX, PIPE_TYPE_MESSAGE | PIPE_READMODE_MESSAGE | PIPE_WAIT, PIPE_UNLIMITED_INSTANCES, 0, 0, INFINITE, NULL);
	hSemaphore = new HANDLE[number_of_employees];

	for (int i = 0; i < number_of_employees; i++)
	{
		hSemaphore[i] = CreateSemaphore(NULL, 1, 1, L"hSemahpore");
	}
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);

	wstring CommandLine = (L"Client.exe " + to_wstring(number_of_employees));
	LPWSTR lpszCommandLine = &CommandLine[0];

	if (!CreateProcess(NULL, lpszCommandLine, NULL, NULL, TRUE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))
	{
		cout << "Server. The process is not created.\n";
		cout << "Press any key to finish.\n";
		getchar();
		return GetLastError();
	}
	hStarted = CreateEvent(NULL, FALSE, FALSE, L"Process Started");
	CloseHandle(pi.hProcess);

	if (hPipe == INVALID_HANDLE_VALUE) {
		cout << "Server. Creation of the named pipe failed. The last error code: " << GetLastError() << "\nPress anything to finish server...";
		getchar();
		return GetLastError();
	}

	if (!ConnectNamedPipe(hPipe, (LPOVERLAPPED)NULL)) {
		cout << "Server. The connection failed. The last error code: " << GetLastError() << "\n";
		CloseHandle(hPipe);
		cout << "Press anything to finish the server: ";
		getchar();
		return GetLastError();
	}

	
	hThreads = CreateThread(NULL, 0, operations, static_cast<LPVOID>(hPipe), 0, NULL);

	WaitForSingleObject(hStarted, INFINITE);
	WaitForSingleObject(pi.hProcess, INFINITE);
	WaitForSingleObject(hThreads, INFINITE);

	cout << "\nThe client has finished the work.\n";

	file_input.open(file_name);
	for (int i = 0; i < number_of_employees; i++)
	{
		int id;
		char name[10];
		double hours;
		file_input >> id >> name >> hours;
		cout << "ID: " << id << "\tName: " << name << "\tHours: " << hours << "\n";

	}
	file_input.close();
	cout << "Press anything to exit...\n";
	getchar();
	return 0;
}
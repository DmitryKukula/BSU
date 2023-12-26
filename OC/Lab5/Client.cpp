#include <conio.h>
#include <windows.h>
#include <iostream>
#include <string>

using namespace std;

struct employee
{
	int num; // идентификационный номер сотрудника
	char name[10]; // имя сотрудника
	double hours; // количество отработанных часов
};

int main(int argc, char* argv[]) {
	int count_of_emp = stoi(argv[1]);

	HANDLE hStartEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"Process Started");
	if (hStartEvent == NULL)
	{
		cout << "Client. Open event failed. \nInput any char to exit.";
		cin.get();
		return GetLastError();
	}

	//SetEvent(hStartEvent);
	HANDLE hPipe = CreateFile(L"\\\\.\\pipe\\pipe_name", GENERIC_WRITE | GENERIC_READ, 0, NULL, OPEN_EXISTING, 0, NULL);
	if (hPipe == INVALID_HANDLE_VALUE)
	{
		cout << "Client. Creation of the named pipe failed. The last error code: " << GetLastError() << "\n";
		cout << "Press anything to finish server: ";
		getchar();
		return 1;
	}

    while (true) {
        int chosen_option = 0;
        cout << "Choose option:\n 1. Modify data\n 2. Exit\nChoose: ";
        cin >> chosen_option;

        if (chosen_option == 2) {
            break;
        }
        else if (chosen_option == 1) {
            DWORD dwBytesWritten;
            DWORD dwBytesReaden;

            int ID;
            cout << "\nInput an ID of employee: ";
            cin >> ID;

            if (ID > count_of_emp || ID < 1) {
                cout << "\nInvalid ID\n";
                continue;
            }

            int message_to_send = ID * 10 + chosen_option;
            bool checker = WriteFile(hPipe, &message_to_send, sizeof(message_to_send), &dwBytesWritten, NULL);

            if (!checker) {
                cout << "Message wasn't sent.\n";
                continue;
            }

            employee emp;
            if (ReadFile(
                hPipe, // дескриптор канала
                &emp, // адрес буфера для ввода данных
                sizeof(employee), // число читаемых байтов
                &dwBytesReaden, // число прочитанных байтов
                NULL)) { // передача данных синхронная

                cout << "ID: " << emp.num << "\tName: " << emp.name << "\tHours: " << emp.hours << "\n";
                
                cout << "Input new Name: ";
                cin >> emp.name;
                cout << "Input new Hours: ";
                cin >> emp.hours;

                checker = WriteFile(
                    hPipe, // дескриптор канала
                    &emp, // адрес буфера для вывода данных
                    sizeof(employee), // число записываемых байтов
                    &dwBytesWritten, // число записанных байтов
                    NULL); // передача данных синхронная

                if (checker) {
                    cout << "\nMessage was sent.\n";
                }
                else {
                    cout << "\nMessage wasn't sent.\n";
                }

                cout << "Press any key to confirm the option...\n";
                getchar();
                getchar();

                message_to_send = 1;
                WriteFile(hPipe, &message_to_send, sizeof(message_to_send), &dwBytesWritten, NULL);
            }
            else {
                cout << "Failed to read employee data.\n";
            }
        }
        else {
            cout << "Invalid option. Please choose 1 or 2.\n";
        }
    }
    SetEvent(hStartEvent);
	return 0;
}
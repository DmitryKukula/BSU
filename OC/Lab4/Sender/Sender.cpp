#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
#include "../Receiver/Message.h"

using namespace std;

int main(int argc, char* argv[]) {
    HANDLE readyEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, (to_wstring(atoi(argv[3])) + L"ready").c_str());
    HANDLE mutex = OpenMutex(MUTEX_ALL_ACCESS, FALSE, L"mutex");
    HANDLE writeSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"write_sem");
    HANDLE readSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"read_sem");

    if (!readyEvent || !readSemaphore || !writeSemaphore || !mutex) {
        cerr << "Error: Failed to open necessary resources.\n";
        return -1;
    }

    SetEvent(readyEvent);

    fstream outputFile;
    int userChoice;

    while (true) {
        cout << "1. Write a message\n";
        cout << "0. Exit\n";
        cin >> userChoice;

        if (userChoice != 0 && userChoice != 1) {
            cerr << "Error: Unknown command.\n";
            continue;
        }

        if (userChoice == 0) {
            break;
        }

        cin.ignore();

        string messageText;
        cout << "Enter the message text: ";
        getline(cin, messageText);

        WaitForSingleObject(writeSemaphore, INFINITE);
        WaitForSingleObject(mutex, INFINITE);

        message newMessage(messageText);
        outputFile.open(argv[1], ios::binary | ios::app);
        outputFile << newMessage;
        outputFile.close();

        ReleaseMutex(mutex);
        ReleaseSemaphore(readSemaphore, 1, NULL);

        cout << "\tSuccess: Message written to the file.\n";
    }

    CloseHandle(mutex);
    CloseHandle(readyEvent);
    CloseHandle(writeSemaphore);
    CloseHandle(readSemaphore);

    return 0;
}

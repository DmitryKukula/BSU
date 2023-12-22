#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
#include "Message.h"
#include <vector>

using namespace std;

HANDLE StartProcess(wstring commandLine) {
    STARTUPINFO startupInfo;
    PROCESS_INFORMATION processInfo;
    ZeroMemory(&startupInfo, sizeof(startupInfo));
    startupInfo.cb = sizeof(startupInfo);

    if (!CreateProcess(NULL, (LPWSTR)commandLine.c_str(), NULL, NULL,
        FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &startupInfo, &processInfo)) {
        return NULL;
    }

    CloseHandle(processInfo.hThread);
    return processInfo.hProcess;
}

int main() {
    wstring fileName;
    cout << "Enter file name: ";
    wcin >> fileName;

    ofstream fout(fileName, ios::binary);
    int numberOfRecords;
    cout << "Enter number of records: ";
    cin >> numberOfRecords;
    if (!fout.is_open()) {
        cerr << "Error creating file!";
        return 0;
    }
    fout.close();

    int numberOfSenders;
    cout << "Enter number of Senders: ";
    cin >> numberOfSenders;

    vector<HANDLE> senderProcesses(numberOfSenders);
    vector<HANDLE> events(numberOfSenders);

    HANDLE mutex = CreateMutex(NULL, FALSE, L"mutex");
    HANDLE writeSemaphore = CreateSemaphore(NULL, numberOfRecords, numberOfRecords, L"write_sem");
    HANDLE readSemaphore = CreateSemaphore(NULL, 0, numberOfRecords, L"read_sem");
    if (!mutex || !writeSemaphore || !readSemaphore) {
        cerr << "Error";
        return -1;
    }

    for (int i = 0; i < numberOfSenders; ++i) {
        wstring commandLine = L"Sender.exe " + fileName + L" " + to_wstring(numberOfRecords) + L" " + to_wstring(i);

        HANDLE event = CreateEvent(NULL, FALSE, FALSE, (to_wstring(i) + L"ready").c_str());
        events[i] = event;

        senderProcesses[i] = StartProcess(commandLine);
        if (senderProcesses[i] == NULL) {
            cerr << "Error while creating process";
            return -1;
        }
    }

    WaitForMultipleObjects(numberOfSenders, events.data(), TRUE, INFINITE);

    while (true) {
        int userChoice;
        cout << "1. Read message\n";
        cout << "0. Exit\n";
        cin >> userChoice;

        if (userChoice != 0 && userChoice != 1) {
            cerr << "Unknown command\n";
            continue;
        }

        if (userChoice == 0) {
            break;
        }

        WaitForSingleObject(readSemaphore, INFINITE);
        WaitForSingleObject(mutex, INFINITE);

        ifstream fin(fileName, ios::binary);
        message receivedMessage;
        fin >> receivedMessage;
        cout << "New message: " << receivedMessage.get_text() << "\n";

        vector<message> fileContents;
        while (fin >> receivedMessage) {
            fileContents.push_back(receivedMessage);
        }
        fin.close();

        ofstream outputFile(fileName, ios::binary);
        for (const auto& msg : fileContents) {
            outputFile << msg;
        }
        outputFile.close();

        ReleaseMutex(mutex);
        ReleaseSemaphore(writeSemaphore, 1, NULL);
    }

    for (int i = 0; i < numberOfSenders; ++i) {
        CloseHandle(events[i]);
        CloseHandle(senderProcesses[i]);
    }

    CloseHandle(mutex);
    CloseHandle(readSemaphore);
    CloseHandle(writeSemaphore);

    return 0;
}

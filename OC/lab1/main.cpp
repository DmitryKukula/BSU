#include <iostream>
#include <string>
#include <fstream>
#include <Windows.h>

using namespace std;

void start_creator(string input_file, int num_Records) {
	string creator_cmd = "Creator.exe " + input_file + " " + to_string(num_Records);
	wstring converting_creator_to_lpwstr = wstring(creator_cmd.begin(), creator_cmd.end());
	LPWSTR lpszCreatorProcessCommandLine = &converting_creator_to_lpwstr[0];

	STARTUPINFO si = { 0 };
	PROCESS_INFORMATION pi = { 0 };
	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);

	if (!CreateProcess(NULL, lpszCreatorProcessCommandLine, NULL, NULL, TRUE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi)) {
		cerr << "Process 1 is not created";
		system("pause");
		//return GetLastError();
	}
	WaitForSingleObject(pi.hProcess, INFINITE);
	CloseHandle(pi.hThread);
	CloseHandle(pi.hProcess);
}

void start_reporter(string input_file, string output_file, double salary_per_hour) {
	string reporter_cmd = "Reporter.exe " + input_file + " " + output_file + " " + to_string(salary_per_hour);
	wstring converting_reporter_to_lpwstr = wstring(reporter_cmd.begin(), reporter_cmd.end());
	LPWSTR lpszReporterProcessCommandLine = &converting_reporter_to_lpwstr[0];

	STARTUPINFO si = { 0 };
	PROCESS_INFORMATION pi = { 0 };
	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);

	if (!CreateProcess(NULL, lpszReporterProcessCommandLine, NULL, NULL, TRUE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi)) {
		cerr << "Process 2 is not created\n";
		system("pause");
		//return GetLastError();
	}
	WaitForSingleObject(pi.hProcess, INFINITE);
	CloseHandle(pi.hThread);
	CloseHandle(pi.hProcess);
}

int main() {
	setlocale(LC_ALL, "Rus");
	std::string input_file, output_file;
	int num_Records;
	double salary_per_hour;

	std::cout << "Write the name of input file: ";
	std::cin >> input_file;
	std::cout << "Write the number records in this file: ";
	std::cin >> num_Records;

	//std::cout << input_file << " " << num_Records << "\n";
	start_creator(input_file, num_Records);


	cout << "\n\tÈñõîäíûé ôàéë:\n";
	std::ifstream fin("D:\\ÁÃÓ\\2 êóðñ\\ÎÑ\\Lab1\\Lab1\\" + input_file, std::ios::binary);
	string temp;
	while (!fin.eof()) {
		getline(fin, temp);
		if (fin.eof()) {
			break;
		}
		cout << temp << "\n";
	}
	fin.close();
	cout << "\n";


	std::cout << "Write the name of output file: ";
	std::cin >> output_file;
	std::cout << "Write the salary per hour: ";
	std::cin >> salary_per_hour;

	start_reporter(input_file, output_file, salary_per_hour);

	std::ifstream fout("D:\\ÁÃÓ\\2 êóðñ\\ÎÑ\\Lab1\\Lab1\\" + output_file, std::ios::binary);
	cout << "\n";
	while (!fout.eof()) {
		getline(fout, temp);
		if (fout.eof()) {
			break;
		}
		cout << temp << "\n";
	}
	fout.close();
	return 0;
}
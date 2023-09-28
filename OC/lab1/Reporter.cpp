#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cmath>
#include "Employee.h"

double string_to_double(std::string arg) {
	int pos;
	for (int i = 0; i < arg.size(); i++) {
		if (arg[i] == '.' || arg[i] == ',') {
			pos = i;
		}
	}

	double result = 0;

	for (int i = 0; i < arg.size(); i++) {
		if (arg[i] == '.' || arg[i] == ',') {
			pos++;
			continue;
		}
		double temp = (arg[i] - '0') * pow(10, pos - 1 - i);
		result += temp;
	}
	return result;
}

int main(int argc, char* argv[]) {
	setlocale(LC_ALL, "rus");
	std::string output = argv[2];
	std::string input = argv[1];
	double salary = string_to_double(argv[3]);
		
	std::ofstream fout("D:\\БГУ\\2 курс\\ОС\\Lab1\\Lab1\\" + output, std::ios::binary);
	fout << "\tОтчет по файлу " << input << ":\n";
	
	std::vector <Employee> persons;
	std::ifstream fin("D:\\БГУ\\2 курс\\ОС\\Lab1\\Lab1\\" + input, std::ios::binary);
	while (!fin.eof()){
		Employee point;
		fin >> point;
		if (fin.eof()) {
			break;
		}
		persons.push_back(point);
	}
	fin.close();

	for (int i = 0; i < persons.size(); i++) {
		persons[i].set_salary(salary);
		std::cout << persons[i] << "\n";
	}

	for (int i = 0; i < persons.size(); i++) {
		persons[i].set_salary(salary);
		fout << persons[i] << " --- " << persons[i].salary << "\n";
	}
	fout.close();
	system("pause");
	return 0;
}
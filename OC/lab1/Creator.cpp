#include <iostream>
#include <fstream>
#include <string>
#include "Employee.h"

int main(int argc, char* argv[]) {
	std::string name_file = argv[1];
	int quantity = std::stoi(argv[2]);
	Employee* point = new Employee[quantity];

	std::cout << "Num of employee, name of employee, hours:\n";
	
	std::ofstream fout(name_file, std::ios::binary);
	for (int i = 0; i < quantity; i++) {
		std::cin >> point[i];
		fout << point[i] << "\n";
	}
	fout.close();
	system("pause");
	delete[] point;
	return 0;
}
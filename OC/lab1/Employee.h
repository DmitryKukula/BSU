#pragma once
#include <fstream>

struct Employee
{
	int num;
	char name[10];
	double hours;
	double salary;
	friend std::ostream& operator<< (std::ostream& out, const Employee& point);
	friend std::istream& operator>> (std::istream& in, Employee& point);

	void set_salary(double salary);
};
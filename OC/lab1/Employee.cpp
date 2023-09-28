#include "Employee.h"

std::ostream& operator<<(std::ostream& out, const Employee& point)
{
    out << point.num << " ";
    out << point.name << " ";
    out << point.hours;
    return out;
}

std::istream& operator>>(std::istream& in, Employee& point)
{
    in >> point.num >> point.name >> point.hours;
    return in;
}

void Employee::set_salary(double salary_per_hour)
{
    salary = double(hours) * salary_per_hour;
}

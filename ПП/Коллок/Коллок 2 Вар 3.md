## 0-я Группа вопросов:

2. Поток в ОС Windows:
   В операционной системе Windows поток представляет собой наименьшую единицу выполнения. Каждый процесс в Windows может содержать один или несколько потоков. Потоки внутри одного процесса могут выполняться параллельно, совместно используя ресурсы процесса. Они могут быть использованы для выполнения различных задач параллельно, улучшая отзывчивость программ и эффективность использования ресурсов.

3. Мьютекс:
   Мьютекс (Mutex) - это объект синхронизации, используемый для обеспечения взаимного исключения между потоками. Мьютекс предоставляет механизм блокировки, который гарантирует, что только один поток имеет доступ к общим ресурсам в определенный момент времени. Если поток заблокировал мьютекс, другие потоки должны ждать его освобождения.

4. Событие (объект синхронизации):
   Событие - это объект синхронизации, который может находиться в одном из двух состояний: сигнальном (событие произошло) или несигнальном (событие не произошло). События используются для уведомления одного или нескольких потоков о том, что произошло какое-то событие. Они предоставляют механизм асинхронного взаимодействия между потоками.

5. Сравнительный анализ стандарта C++ 98 и более свежего стандарта:
   - C++98 (или C++03): Это стандарт, который был принят в 1998 году и позже слегка модифицирован в 2003 году. В C++03 были внесены небольшие изменения и исправления ошибок по сравнению с C++98.
   - Более свежий стандарт (например, C++11, C++14, C++17, C++20): Следующие стандарты C++ внесли значительные изменения и добавили новые возможности в язык программирования. Это включает в себя автоматическое управление памятью с использованием умных указателей, лямбда-выражения, новые типы данных, обновленные стандартные библиотеки и многие другие улучшения.

   В контексте лабораторных работ, более свежие стандарты могут предоставлять более удобные и эффективные инструменты для работы с потоками, событиями и другими аспектами многозадачности и синхронизации. Улучшения в языке и стандартной библиотеке могут сделать код более читаемым, безопасным и производительным.





## 1-я Группа вопросов:
```cpp
UnitTest:
#include <gtest/gtest.h>

TEST(FactorialTest, NegativeInput) {
    EXPECT_THROW(generateFactorials(-1), std::invalid_argument);
}

TEST(FactorialTest, Factorials) {
    EXPECT_EQ(generateFactorials(0), std::vector<int>({1}));
    EXPECT_EQ(generateFactorials(5), std::vector<int>({1, 1, 2, 6, 24}));
}
```

1.
```cpp
#include <iostream>
#include <vector>
#include <stdexcept>

// Функция для вычисления факториала числа
int factorial(int n) {
    if (n == 0 || n == 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

// Функция для генерации первых n факториалов
std::vector<int> generateFactorials(int n) {
    if (n < 0) {
        throw std::invalid_argument("Input should be a non-negative integer.");
    }

    std::vector<int> result;
    for (int i = 0; i < n; ++i) {
        result.push_back(factorial(i));
    }

    return result;
}

int main() {
    try {
        int n;
        std::cout << "Enter a non-negative integer n: ";
        std::cin >> n;

        std::vector<int> factorials = generateFactorials(n);

        std::cout << "Factorials:";
        for (int factorial : factorials) {
            std::cout << " " << factorial;
        }
        std::cout << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}
```

2.
```cpp
#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>

// Функция для удаления дубликатов из контейнера
template <typename T>
std::vector<T> removeDuplicates(const std::vector<T>& input) {
    std::vector<T> result;
    std::set<T> uniqueElements;

    for (const T& element : input) {
        if (uniqueElements.insert(element).second) {
            result.push_back(element);
        }
    }

    return result;
}

int main() {
    std::vector<int> input = {1, 2, 3, 2, 4, 5, 6, 1};

    std::vector<int> result = removeDuplicates(input);

    std::cout << "Original: ";
    for (int element : input) {
        std::cout << element << " ";
    }
    std::cout << std::endl;

    std::cout << "Without duplicates: ";
    for (int element : result) {
        std::cout << element << " ";
    }
    std::cout << std::endl;

    return 0;
}
```

3. 
```cpp
#include <iostream>

struct Node {
    int data;
    Node* next;

    Node(int val) : data(val), next(nullptr) {}
};

// Функция для вывода связанного списка
void printList(Node* head) {
    while (head != nullptr) {
        std::cout << head->data << " ";
        head = head->next;
    }
    std::cout << std::endl;
}

// Функция для разворота связанного списка с использованием рекурсии
Node* reverseListRecursive(Node* head) {
    if (head == nullptr || head->next == nullptr) {
        return head;
    }

    Node* rest = reverseListRecursive(head->next);
    head->next->next = head;
    head->next = nullptr;

    return rest;
}

int main() {
    // Создание примера связанного списка: 1 -> 2 -> 3 -> 4 -> 5
    Node* head = new Node(1);
    head->next = new Node(2);
    head->next->next = new Node(3);
    head->next->next->next = new Node(4);
    head->next->next->next->next = new Node(5);

    std::cout << "Original list: ";
    printList(head);

    // Разворот связанного списка
    head = reverseListRecursive(head);

    std::cout << "Reversed list: ";
    printList(head);

    return 0;
}
```





## 2-я Группа вопросов:
1. ООП декомпозиция:
ООП декомпозиция (или декомпозиция в объектно-ориентированном программировании) - это процесс разделения сложной системы на более простые и легко управляемые части, которые называются объектами. В рамках объектно-ориентированного программирования (ООП), объекты представляют собой экземпляры классов, которые инкапсулируют данные и методы для их обработки. Каждый объект выполняет определенные функции и взаимодействует с другими объектами через определенные интерфейсы.

2. Статический полиморфизм:
Статический полиморфизм (или компиляционный полиморфизм) - это форма полиморфизма, при которой связывание (выбор, какой метод вызывать) происходит на этапе компиляции. Он достигается с использованием перегрузки функций или операторов, а также с использованием шаблонов (в случае языка программирования C++).

3. Инкапсуляция:
Инкапсуляция - это один из принципов объектно-ориентированного программирования, который представляет собой объединение данных (переменных) и методов, которые работают с этими данными внутри одного объекта. Основная идея инкапсуляции заключается в том, чтобы скрыть детали реализации и предоставить интерфейс, через который можно взаимодействовать с объектом. Это помогает обеспечить безопасность, упрощает изменение реализации без изменения интерфейса и позволяет скрыть сложность объекта.





## 3-я Группа вопросов:
1. Creation Method (Фабричный Метод) в C++:

   + Описание через призму инкапсуляции:

     Creation Method, также известный как Фабричный Метод, является порождающим паттерном проектирования, который инкапсулирует процесс создания объектов, делегируя его наследникам базового класса. Вместо того чтобы создавать экземпляр объекта напрямую, клиентский код вызывает виртуальный метод, предоставленный абстрактным базовым классом, и оставляет реализацию этого метода подклассам.

   + Пример использования:
   
     Рассмотрим простой пример с созданием различных форм (круг, прямоугольник, треугольник) с использованием Creation Method.

```cpp
#include <iostream>

// Абстрактный базовый класс
class Shape {
public:
    virtual void draw() = 0;
};

// Конкретные реализации
class Circle : public Shape {
public:
    void draw() override {
        std::cout << "Draw a circle." << std::endl;
    }
};

class Rectangle : public Shape {
public:
    void draw() override {
        std::cout << "Draw a rectangle." << std::endl;
    }
};

class Triangle : public Shape {
public:
    void draw() override {
        std::cout << "Draw a triangle." << std::endl;
    }
};

// Создание метода в абстрактном базовом классе
class ShapeCreator {
public:
    virtual Shape* createShape() = 0;

    void drawShape() {
        Shape* shape = createShape();
        shape->draw();
        delete shape;
    }
};

// Конкретные реализации создания форм
class CircleCreator : public ShapeCreator {
public:
    Shape* createShape() override {
        return new Circle();
    }
};

class RectangleCreator : public ShapeCreator {
public:
    Shape* createShape() override {
        return new Rectangle();
    }
};

class TriangleCreator : public ShapeCreator {
public:
    Shape* createShape() override {
        return new Triangle();
    }
};

int main() {
    // Клиентский код использует Creation Method
    ShapeCreator* creator = new CircleCreator();
    creator->drawShape();

    creator = new RectangleCreator();
    creator->drawShape();

    creator = new TriangleCreator();
    creator->drawShape();

    delete creator;

    return 0;
}
```

В этом примере Creation Method (`createShape`) инкапсулирует процесс создания форм, а клиентский код использует этот метод для создания и отрисовки различных форм. Это делает код более гибким, так как мы можем добавлять новые формы, не изменяя клиентский код.


2. Bridge + С++ идиома с объяснением причин использования:

   + Описание через призму инкапсуляции:
   
     Bridge - это структурный паттерн проектирования, который разделяет абстракцию и её реализацию так, чтобы они могли изменяться независимо друг от друга. Он использует композицию вместо наследования, чтобы инкапсулировать каждую из этих составляющих и предоставить возможность их изменения независимо друг от друга.

   + Пример использования:

     Представим, у нас есть иерархия классов для различных типов устройств (например, телевизор и радио) и для различных пультов управления (например, обычный пульт и умный пульт). Мы можем использовать мост для разделения абстракции устройства от её реализации.

   ```cpp
   // Абстракция - пульт
   class RemoteControl {
   protected:
       Device* device;

   public:
       RemoteControl(Device* device) : device(device) {}

       virtual void pressButton() {
           device->operate();
       }
   };

   // Реализация - устройство
   class Device {
   public:
       virtual void operate() = 0;
   };

   // Конкретная реализация - телевизор
   class TV : public Device {
   public:
       void operate() override {
           // Логика управления телевизором
       }
   };

   // Конкретная реализация - радио
   class Radio : public Device {
   public:
       void operate() override {
           // Логика управления радио
       }
   };

   // Клиентский код
   int main() {
       Device* tv = new TV();
       RemoteControl* simpleRemote = new RemoteControl(tv);
       simpleRemote->pressButton();

       Device* radio = new Radio();
       RemoteControl* smartRemote = new RemoteControl(radio);
       smartRemote->pressButton();

       return 0;
   }
   ```

   В данном примере Bridge позволяет изменять и расширять иерархии устройств и пультов независимо друг от друга, сохраняя при этом возможность свободного сочетания различных устройств и пультов управления.

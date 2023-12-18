// вариант 2


import java.util.*;

public class Main {             // класс Main
    public static void main(String[] args) {

        // инициализируем классы customer - клиент

        Customer eugene = new Customer(1, "Eugene", "Bakharev", "Vladimirovich", "133@mtuci.ru", 1234, 5678);
        Customer egor = new Customer(2, "Egor", "Zaycev", "meow@rostov-na-donu.com", 1111, 2222);

        // список клиентов

        CustomerList list = new CustomerList();

        // добавляем в список клиентов

        list.addCustomer(eugene);
        list.addCustomer(egor);

        System.out.println(list);       // вывод списка

        list.sort();                    // сортировка
        System.out.println(list);       // вывод отсортированного списка

        CustomerList arrayList = list.rangeOfCreditCard(1000, 1200);            // формируем список с новыми
        // клиентами, у которых номер карты задан на отрезке
        System.out.println(arrayList);      // вывод нового списка
    }
}





class Customer implements Comparable<Customer>{         // класс Customer. Наследуемся от интерфейса Comparable, что бы
    // добавить возможность сравнивать между собой объекты
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private String address;
    private Integer creditCardNumber;
    private Integer bankAccountNumber;

    // конструктор класса с отчеством

    Customer(Integer id, String name, String surname, String patronymic, String address, int creditCardNumber, int bankAccountNumber){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.bankAccountNumber = bankAccountNumber;
    }

    // конструктор класса без отчества

    Customer(Integer id, String name, String surname, String address, int creditCardNumber, int bankAccountNumber){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.bankAccountNumber = bankAccountNumber;
    }

    // методы, возвращающие переменные

    public Integer getId(){return this.id;}
    public Integer getBankAccountNumber(){return this.bankAccountNumber;}
    public String getName(){return this.name;}
    public String getSurname(){return this.surname;}
    public String getAddress(){return this.address;}
    public Integer getCreditCardNumber(){ return this.creditCardNumber;}

    // методы, устанавливающие переменные

    public void setId(Integer id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setSurname(String surname){this.surname = surname;}
    public void setAddress(String address){this.address = address;}
    public void setCreditCardNumber(Integer creditCardNumber){this.creditCardNumber = creditCardNumber;}
    public void setBankAccountNumber(Integer bankAccountNumber){this.bankAccountNumber = bankAccountNumber;}
    public boolean equals(Customer customer){
        if (this.compareTo(customer) == 0) return true;
        else return false;
    }

    @Override
    public int compareTo(Customer customer) {           // метод compareTo. сравнивает классы с заданным условием
        Integer result = this.name.compareTo(customer.name);        // сначала сравниваем по имени
        if (result == 0){       // если имена одинаковые
            result = this.surname.compareTo(customer.surname);      // сравниваем по фамилии
            if (result == 0){   // если фамилии одни и те же
                result = this.address.compareTo(customer.address);  // сравниваем по адресу
                if (result == 0) {  //если адрес одинаков
                    result = this.creditCardNumber.compareTo(customer.creditCardNumber);    // сравниваем по номеру кредитной карточки
                    if (result == 0) {  // если номера карт одинаковые
                        result = this.bankAccountNumber.compareTo(customer.bankAccountNumber);  // сравниваем по номеру банковского счёта
                        if (result == 0) {  // если номера счетов одни и те же
                            result = this.id.compareTo(customer.id);        // сравниваем по id. Примечание: гарантируется, что в наборах данных id у всех пользователей - разный
                        }
                    }
                }
            }
        }
        return result;      // возвращаем результат сравнения
    }

    public String toStringWithPoints(){     // вывод информации "по-особому"
        String help;
        if (this.patronymic != null) help = patronymic;     // если нет отчества
        else help = "Не указано";       // то "не указано"
        return "{\nid:                    " + this.id + "\nname:                  " + this.name +
                "\nsurname:               " + this.surname + "\npatronymic:            " +
                help + "\naddress:               " + this.address + "\ncredit card's number:  " +
                this.creditCardNumber + "\nbank account's number: " + this.bankAccountNumber + "\n}";
    }

    public String toString(){       // метод toString. Формирует строку из данных объекта по заданному условию
        String help;
        if (this.patronymic != null) help = patronymic;
        else help = "Не указано";
        return String.format("%-8sI ", this.id) +
                String.format("%-18sI ", this.name) +
                String.format("%-18sI ", this.surname) +
                String.format("%-18sI ", help) +
                String.format("%-28sI ", this.address) +
                String.format("%-18sI ", this.creditCardNumber) +
                String.format("%-18sI ", this.bankAccountNumber);
    }
}


class CustomerList{         // Класс список клиентов
    private ArrayList<Customer> customers = new ArrayList<>();      // единственный параметр - односвязный список


    CustomerList(Customer[] customers){
        Collections.addAll(this.customers, customers);
    }       // можем передать в конструктор список

    CustomerList(){}        // а можем ничего не передавать

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }       // добавить клиента
    public boolean isEmpty(){return this.customers.isEmpty();}      // проверка на пустоту

    public void sort(){
        Collections.sort(this.customers, new customerComparator());
    }       // сортировка
    public ArrayList<Customer> getCustomers(){return this.customers;}       // возврат списка
    public void setCustomers(ArrayList<Customer> list){this.customers = list;}      // передать список

    public CustomerList rangeOfCreditCard(int a, int b){        // формируем новый список клиентов, у которых номер кредитной карты задан на отрезке
        CustomerList arrayList = new CustomerList();            // создаём пустой список
        for (int i = 0; i < this.customers.size(); i++){        // итерируемся по исходному списку
            if (a <= this.customers.get(i).getCreditCardNumber() && this.customers.get(i).getCreditCardNumber() <= b){
                // если номер лежит в заданной диапазоне, то добавляем его в новый список
                arrayList.addCustomer(this.customers.get(i));
            }
        }
        return arrayList;       // возвращаем список
    }

    public String toString(){       // метод toString. Описан выше
        String result = String.format("%-8sI ", "id") +
                String.format("%-18sI ", "name") +
                String.format("%-18sI ", "surname") +
                String.format("%-18sI ", "patronymic") +
                String.format("%-28sI ", "address") +
                String.format("%-18sI ", "creditCardNumber") +
                String.format("%-18sI ", "bankAccountNumber") + "\n";
        for (int i = 0; i < this.customers.size(); i++){
            result = result.concat(this.customers.get(i).toString()).concat("\n");
        }
        return result;
    }
}




class customerComparator implements Comparator<Customer> {      // класс customerComparator, наследующийся от интерфейса Comparator
    // данный класс нужен для использования метода Collections.sort(). Мы этот класс передаём в качестве аргумента, что бы отсортировать список так, как нужно нам
    @Override
    public int compare(Customer customer, Customer t1) {
        return customer.compareTo(t1);
    }
}

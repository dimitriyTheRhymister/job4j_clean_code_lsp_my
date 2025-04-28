package ru.job4j.ood.isp.my;

import java.util.Scanner;

public class TodoApp {

    private final Menu menu;
    private final Scanner scanner;
    private final Printer printer = new Printer();

    public TodoApp(Menu menu) {
        this.menu = menu;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("1. Добавить элемент в корень меню");
            System.out.println("2. Добавить элемент к родительскому элементу");
            System.out.println("3. Вызвать действие, привязанное к пункту меню");
            System.out.println("4. Вывести меню в консоль");
            System.out.println("5. Выход");

            System.out.print("Выберите действие: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addRootElement();
                case 2 -> addChildElement();
                case 3 -> executeAction();
                case 4 -> printMenu();
                case 5 -> System.exit(0);
                default -> System.out.println("Недопустимый выбор");
            }
        }
    }

    private void addRootElement() {
        System.out.print("Введите имя элемента: ");
        String name = scanner.nextLine();
        menu.add(null, name, () -> System.out.println("Действие для " + name));
    }

    private void addChildElement() {
        System.out.print("Введите имя родительского элемента: ");
        String parentName = scanner.nextLine();
        System.out.print("Введите имя дочернего элемента: ");
        String childName = scanner.nextLine();
        menu.add(parentName, childName, () -> System.out.println("Действие для " + childName));
    }

    private void executeAction() {
        System.out.print("Введите имя элемента: ");
        String name = scanner.nextLine();
        menu.select(name).ifPresent(item -> item.actionDelegate().delegate());
    }

    private void printMenu() {
        printer.print(menu);
    }

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        TodoApp app = new TodoApp(menu);
        app.run();
    }
}

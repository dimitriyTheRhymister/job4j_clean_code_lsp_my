package ru.job4j.ood.isp.my;

public class Printer implements MenuPrinter {

    @Override
    public void print(Menu menu) {
        for (MenuItemInfo item : menu) {
            System.out.println("----".repeat(Math.max(0, item.getNumber().split("\\.").length - 1)) + item.getNumber() + " " + item.getName());
        }
    }

    public static void main(String[] args) {
        final ActionDelegate STUB_ACTION = System.out::println;
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача Общая.", STUB_ACTION);
        menu.add(Menu.ROOT, "Test2", STUB_ACTION);
        menu.add("Задача Общая.", "Задача Первая.", STUB_ACTION);
        menu.add("Задача Общая.", "Задача Вторая.", STUB_ACTION);
        menu.add("Задача Первая.", "Цель задачи.", STUB_ACTION);
        menu.add("Задача Первая.", "Ограничения и требования к решению.", STUB_ACTION);
        Printer printer = new Printer();
        printer.print(menu);
    }
}

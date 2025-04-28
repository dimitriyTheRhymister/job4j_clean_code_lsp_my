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
        menu.add(Menu.ROOT, "Test1", STUB_ACTION);
        menu.add(Menu.ROOT, "Test2", STUB_ACTION);
        menu.add("Test1", "Test1.1", STUB_ACTION);
        menu.add("Test1", "Test1.2", STUB_ACTION);
        Printer printer = new Printer();
        printer.print(menu);
    }
}
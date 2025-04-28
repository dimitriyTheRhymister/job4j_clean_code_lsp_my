package ru.job4j.ood.isp.my;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddRootElementThenReturnTrue() {
        Menu menu = new SimpleMenu();
        assertThat(menu.add(Menu.ROOT, "Test", STUB_ACTION)).isTrue();
    }

    @Test
    public void whenAddChildElementThenReturnTrue() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Parent", STUB_ACTION);
        assertThat(menu.add("Parent", "Child", STUB_ACTION)).isTrue();
    }

    @Test
    public void whenAddElementToNonExistentParentThenReturnFalse() {
        Menu menu = new SimpleMenu();
        assertThat(menu.add("NonExistentParent", "Child", STUB_ACTION)).isFalse();
    }

    @Test
    public void whenSelectRootElementThenReturnElement() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Test", STUB_ACTION);
        assertThat(menu.select("Test")).isPresent();
    }

    @Test
    public void whenSelectNonExistentElementThenReturnEmpty() {
        Menu menu = new SimpleMenu();
        assertThat(menu.select("NonExistent")).isEmpty();
    }

    @Test
    public void whenIterateOverMenuThenReturnAllElements() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Test1", STUB_ACTION);
        menu.add(Menu.ROOT, "Test2", STUB_ACTION);
        menu.add("Test1", "Test1.1", STUB_ACTION);
        menu.add("Test1", "Test1.2", STUB_ACTION);
        List<String> expected = List.of("1.Test1", "1.1.Test1.1", "1.2.Test1.2", "2.Test2");
        List<String> actual = new ArrayList<>();
        for (MenuItemInfo item : menu) {
            actual.add(item.number() + item.name());
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new MenuItemInfo(
                "Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").orElse(null));
        assertThat(new MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").orElse(null));
        assertThat(new MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").orElse(null));
        Printer printer = new Printer();
        printer.print(menu);
    }


    @Test
    public void whenPrintMenuThenOutputCorrectly() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Test1", STUB_ACTION);
        menu.add(Menu.ROOT, "Test2", STUB_ACTION);
        menu.add("Test1", "Test1.1", STUB_ACTION);
        menu.add("Test1", "Test1.2", STUB_ACTION);

        menu.forEach(i -> System.out.println(i.number() + i.name()));

        System.setOut(originalOut);

        String expected = "1.Test1\r\n1.1.Test1.1\r\n1.2.Test1.2\r\n2.Test2\r\n";
        assertThat(outputStream.toString()).isEqualTo(expected);
    }

    @Test
    public void whenPrintMenuWithPrinterThenOutputCorrectly() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача Общая.", STUB_ACTION);
        menu.add(Menu.ROOT, "Test2", STUB_ACTION);
        menu.add("Задача Общая.", "Задача Первая.", STUB_ACTION);
        menu.add("Задача Общая.", "Задача Вторая.", STUB_ACTION);
        menu.add("Задача Первая.", "Цель задачи.", STUB_ACTION);
        menu.add("Задача Первая.", "Ограничения и требования к решению.", STUB_ACTION);

        Printer printer = new Printer();
        printer.print(menu);

        System.setOut(originalOut);

        String expected = "1. Задача Общая.\r\n----1.1. Задача Первая.\r\n--------1.1.1. Цель задачи.\r\n--------1.1.2. Ограничения и требования к решению.\r\n----1.2. Задача Вторая.\r\n2. Test2\r\n";
        assertThat(outputStream.toString()).isEqualTo(expected);
        printer.print(menu);
    }
}
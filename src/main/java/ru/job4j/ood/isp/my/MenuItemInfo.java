package ru.job4j.ood.isp.my;

import java.util.List;
import java.util.Objects;

public record MenuItemInfo(String name, List<String> children, ActionDelegate actionDelegate, String number) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItemInfo that = (MenuItemInfo) o;
        return Objects.equals(name, that.name)
                && Objects.equals(children, that.children)
                && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, number);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
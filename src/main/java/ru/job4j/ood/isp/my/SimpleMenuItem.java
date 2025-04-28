package ru.job4j.ood.isp.my;

import java.util.ArrayList;
import java.util.List;

public class SimpleMenuItem implements MenuItem {

    private final String name;
    private final List<MenuItem> children = new ArrayList<>();
    private final ActionDelegate actionDelegate;

    public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
        this.name = name;
        this.actionDelegate = actionDelegate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<MenuItem> getChildren() {
        return children;
    }

    @Override
    public ActionDelegate getActionDelegate() {
        return actionDelegate;
    }

    public static void main(String[] args) {
        ActionDelegate actionDelegate = () -> System.out.println("Action executed!");
        SimpleMenuItem menuItem = new SimpleMenuItem("Menu Item 1", actionDelegate);

        System.out.println("Name: " + menuItem.getName());
        System.out.println("Children: " + menuItem.getChildren());
        menuItem.getActionDelegate().delegate();

        SimpleMenuItem childItem = new SimpleMenuItem("Child Item", actionDelegate);
        menuItem.getChildren().add(childItem);

        System.out.println("Children after addition: " + menuItem.getChildren().stream().map(MenuItem::getName).toList());
    }
}
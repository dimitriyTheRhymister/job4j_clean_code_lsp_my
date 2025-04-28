package ru.job4j.ood.isp.my;

public record ItemInfo(MenuItem menuItem, String number) {

    public static ItemInfo of(MenuItem menuItem, String number) {
        return new ItemInfo(menuItem, number);
    }

    public static void main(String[] args) {
        ActionDelegate actionDelegate = () -> System.out.println("Action executed!");
        MenuItem menuItem = new SimpleMenuItem("Menu Item 1", actionDelegate);
        ItemInfo itemInfo = of(menuItem, String.valueOf(1));
        System.out.println(itemInfo.menuItem.getName());
        System.out.println(itemInfo.menuItem.getChildren());
        System.out.println(itemInfo.number);
        itemInfo.menuItem.getActionDelegate().delegate();
    }
}
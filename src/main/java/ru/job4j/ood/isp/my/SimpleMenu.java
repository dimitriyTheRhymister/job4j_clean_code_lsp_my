package ru.job4j.ood.isp.my;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        return (parentName == null
                || parentName.isEmpty())
                ? addRootElement(childName, actionDelegate)
                : addChildElement(parentName, childName, actionDelegate);
    }

    private boolean addRootElement(String childName, ActionDelegate actionDelegate) {
        rootElements.add(new SimpleMenuItem(childName, actionDelegate));
        return true;
    }

    private boolean addChildElement(String parentName, String childName, ActionDelegate actionDelegate) {
        Optional<ItemInfo> parent = findItem(parentName);
        if (parent.isPresent()) {
            parent.get().menuItem().getChildren().add(new SimpleMenuItem(childName, actionDelegate));
            return true;
        }
        return false;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<ItemInfo> item = findItem(itemName);
        return item.map(this::convertToMenuItemInfo);
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        return new Iterator<>() {
            private final DFSIterator dfsIterator = new DFSIterator(rootElements);

            @Override
            public boolean hasNext() {
                return dfsIterator.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                ItemInfo itemInfo = dfsIterator.next();
                return convertToMenuItemInfo(itemInfo);
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        DFSIterator iterator = new DFSIterator(rootElements);

        while (iterator.hasNext()) {
            ItemInfo currentItem = iterator.next();
            if (currentItem.menuItem().getName().equals(name)) {
                return Optional.of(currentItem);
            }
        }

        return Optional.empty();
    }

    private MenuItemInfo convertToMenuItemInfo(ItemInfo itemInfo) {
        return new MenuItemInfo(
                itemInfo.menuItem().getName(),
                itemInfo.menuItem().getChildren().stream()
                        .map(MenuItem::getName)
                        .toList(),
                itemInfo.menuItem().getActionDelegate(),
                itemInfo.number()
        );
    }

    public static void main(String[] args) {
        ActionDelegate actionDelegate = () -> System.out.println("Action executed!");
        SimpleMenu menu = new SimpleMenu();
        menu.add(null, "Root 1", actionDelegate);
        menu.add("", "Root 2", actionDelegate);
        menu.add("Root 1", "1 1", actionDelegate);
        menu.add("Root 2", "2 1", actionDelegate);

        System.out.println(Objects.requireNonNull(menu.select("1 1").orElse(null)).getName());
        System.out.println(Objects.requireNonNull(Objects.requireNonNull(menu.findItem("2 1").orElse(null)).number()));

        for (MenuItemInfo itemInfo : menu) {
            System.out.println(itemInfo.number() + " " + itemInfo.getName());
        }
    }
}

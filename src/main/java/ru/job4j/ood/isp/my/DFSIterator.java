package ru.job4j.ood.isp.my;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class DFSIterator implements Iterator<ItemInfo> {

    private final Deque<MenuItem> stack = new LinkedList<>();
    private final Deque<String> numbers = new LinkedList<>();

    public DFSIterator(List<MenuItem> rootElements) {
        int number = 1;
        for (MenuItem item : rootElements) {
            stack.addLast(item);
            numbers.addLast(String.valueOf(number++).concat("."));
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public ItemInfo next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        MenuItem current = stack.removeFirst();
        String lastNumber = numbers.removeFirst();
        List<MenuItem> children = current.getChildren();
        int currentNumber = children.size();
        for (var i = children.listIterator(children.size()); i.hasPrevious();) {
            MenuItem child = i.previous();
            stack.addFirst(child);
            numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
        }
        return ItemInfo.of(current, lastNumber);
    }

    public static void main(String[] args) {
        ActionDelegate actionDelegate = () -> System.out.println("Action executed!");
        SimpleMenuItem root1 = new SimpleMenuItem("Root 1", actionDelegate);
        SimpleMenuItem root2 = new SimpleMenuItem("Root 2", actionDelegate);

        SimpleMenuItem child11 = new SimpleMenuItem("Child 1.1", actionDelegate);
        SimpleMenuItem child12 = new SimpleMenuItem("Child 1.2", actionDelegate);
        root1.getChildren().add(child11);
        root1.getChildren().add(child12);

        SimpleMenuItem child21 = new SimpleMenuItem("Child 2.1", actionDelegate);
        SimpleMenuItem child22 = new SimpleMenuItem("Child 2.2", actionDelegate);
        root2.getChildren().add(child21);
        root2.getChildren().add(child22);

        List<MenuItem> rootElements = List.of(root1, root2);
        DFSIterator dfsIterator = new DFSIterator(rootElements);

        while (dfsIterator.hasNext()) {
            ItemInfo itemInfo = dfsIterator.next();
            System.out.println(itemInfo.number() + " " + itemInfo.menuItem().getName());
        }
    }
}

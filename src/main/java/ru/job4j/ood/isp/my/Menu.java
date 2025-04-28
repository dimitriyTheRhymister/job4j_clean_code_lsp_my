package ru.job4j.ood.isp.my;

import java.util.Optional;

public interface Menu extends Iterable<MenuItemInfo> {

    String ROOT = null;

    boolean add(String parentName, String childName, ActionDelegate actionDelegate);

    Optional<MenuItemInfo> select(String itemName);
}
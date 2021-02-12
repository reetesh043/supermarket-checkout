package com.reet.domain;

import java.util.Objects;
import java.util.StringJoiner;
/*
 *  Item class. Contains info of checkout items.
 */
public class Item {

    private String itemId;

    private String groupId;

    private String quantity;

    private String price;

    public Item() {
        // default constructor
    }

    public Item(String itemId, String groupId, String quantity, String price) {
        this.itemId = itemId;
        this.groupId = groupId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }


    public String getGroupId() {
        return groupId;
    }


    public String getQuantity() {
        return quantity;
    }


    public String getPrice() {
        return price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && quantity == item.quantity && Objects.equals(groupId, item.groupId) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, groupId, quantity, price);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
                .add("itemId='" + itemId + "'")
                .add("groupId='" + groupId + "'")
                .add("quantity='" + quantity + "'")
                .add("unitPrice='" + price + "'")
                .toString();
    }
}

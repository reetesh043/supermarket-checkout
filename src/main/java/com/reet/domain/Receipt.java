package com.reet.domain;

import java.util.Objects;
import java.util.StringJoiner;

/*
 *  Final receipt after discount.
 */
public final class Receipt {

    private final Double originalPrice;
    private final Double finalPrice;
    private final Double discount;
    private final String itemId;
    private final String quantity;

    public Receipt(Double originalPrice, Double finalPrice, Double discount, String itemId, String quantity) {
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.discount = discount;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(originalPrice, receipt.originalPrice) && Objects.equals(finalPrice, receipt.finalPrice) && Objects.equals(discount, receipt.discount) && Objects.equals(itemId, receipt.itemId) && Objects.equals(quantity, receipt.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalPrice, finalPrice, discount, itemId, quantity);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Receipt.class.getSimpleName() + "[", "]")
                .add("originalPrice=" + originalPrice)
                .add("finalPrice=" + finalPrice)
                .add("discount=" + discount)
                .add("itemId='" + itemId + "'")
                .add("quantity='" + quantity + "'")
                .toString();
    }

}

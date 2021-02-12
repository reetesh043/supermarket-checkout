package com.reet.domain;

/*
* Rule enum.
 */
public enum RuleEnum {

    BUY_THREE_EQUAL_PRICE_PAY_FOR_TWO("1"),
    BUY_TWO_EQUAL_PRICE_FOR_SPECIAL_PRICE("2"),
    BUY_THREE_EQUAL_PRICE_IN_GROUP_GET_CHEAPEST_FREE("3"),
    BUY_N_ITEM_X_GET_M_ITEMS_Y("4");

    private String discount;

    RuleEnum(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }

}

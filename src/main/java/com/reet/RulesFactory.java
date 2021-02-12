package com.reet;

import com.reet.rules.BuyThreeItemsPayForTwoItems;
import com.reet.rules.BuyTwoItemsAndGetForSpecialPrice;
import com.reet.rules.BuyThreeItemsInGroupAndGetCheapestFree;
import com.reet.rules.BuyNItemsXGetMItemsY;
import com.reet.rules.IDiscountRule;

/*
 * Factory class to create object of discount rule interface implementation classes.
 */
public class RulesFactory {

    public static IDiscountRule createDiscountRule(final String ruleType) {
        switch (ruleType) {
            case "1":
                return new BuyThreeItemsPayForTwoItems();
            case "2":
                return new BuyTwoItemsAndGetForSpecialPrice();
            case "3":
                return new BuyThreeItemsInGroupAndGetCheapestFree();
            case "4":
                return new BuyNItemsXGetMItemsY();
            default:
                throw new IllegalArgumentException("Invalid rule type");
        }
    }
}

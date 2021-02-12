package com.reet.rules;

import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.Item;
import com.reet.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.List;

/*
 * Base interface for all the business discount rules. Any class implementing this interface will provide the logic
 * of applying the rule and generate the receipt for items.
 */
public interface IDiscountRule {


    /**
     * apply all the discounts on all the item and generate receipt.
     *
     * @param items - list of items
     * @Param rules - list of items with discounts
     * @return list of receipt
     */

    List<Receipt> applyDiscountRule(final List<Item> items, final List<Rule> rules);

    /**
     * Filter all items for which a discount rule is applied.
     *
     * @param items - list of items
     * @Param rules - list of items with discounts
     * @Param ruleName - name of the rule to be filtered
     * @return itemList - filtered Items
     */
    default List<Item> filterByCriteria(final List<Item> items, final List<Rule> rules, final String ruleName) {

        List<Item> itemList = new ArrayList<>();
        rules.stream().filter(rule -> rule.getRuleName().equals(ruleName)).map(rule -> rule.getItemId())
                .forEach(allowedItemId -> items.stream().filter(item -> item.getItemId().equals(allowedItemId)).forEach(itemList::add));
        return itemList;

    }

    /**
     * method to calculate total price.
     *
     * @param price    - price of item
     * @param quantity - item quantity
     * @return totalPrice
     */

    static Double calculateTotalPrice(final String price, final String quantity) {
        Double itemPrice = !price.isEmpty() ? Double.parseDouble(price) : 0;
        Double itemQuantity = !quantity.isEmpty() ? Double.parseDouble(quantity) : 0;
        return 0 == itemPrice && 0 == itemQuantity ? 0 : itemQuantity / (1 / itemPrice);
    }

    /**
     * validate if items and rules are empty
     *
     * @param items    - price of item
     * @param rules - item quantity
     *
     */

    static void validate(final List<Item> items, final List<Rule> rules) {
        if (items == null || items.isEmpty() || rules == null || rules.isEmpty()) {
            throw new InvalidRequestException("number of items and rules applied to them must not be null");
        }
    }


}

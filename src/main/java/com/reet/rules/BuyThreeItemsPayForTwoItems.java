package com.reet.rules;

import com.reet.domain.Item;
import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.RuleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * Implementation of rule buy three and pay for two items
 */
public class BuyThreeItemsPayForTwoItems implements IDiscountRule {


    private static Logger logger = Logger.getLogger("BuyThreeItemsPayForTwoItems");

    public static final int ITEMS_WITH_EQUAL_PRICE_SIZE = 3;
    public static final double DISCOUNT_PERCENTAGE = 0.33;


    /**
     * Buy set of three items and get one item for free.
     *
     * @param items - items to be used for calculating the discount
     * @param rules - contains the rules which to be applied on items
     * @return responseList - list of generated response
     */

    @Override
    public List<Receipt> applyDiscountRule(final List<Item> items, final List<Rule> rules) {
        logger.info("processing the items");
        IDiscountRule.validate(items, rules);

        // retrieve the items which belong to this rule
        List<Item> itemsAppliedToRule = filterByCriteria(items, rules, RuleEnum.BUY_THREE_EQUAL_PRICE_PAY_FOR_TWO.name());

        List<Receipt> receiptList = new ArrayList<>();
        if (0 != itemsAppliedToRule.size()) {
            // check if all the items has same price
            boolean priceCheck = itemsAppliedToRule.isEmpty() || itemsAppliedToRule.stream()
                    .allMatch(item -> item.getPrice().equals(itemsAppliedToRule.get(0).getPrice()));

            // Check if no of items are more than 3 including all items
            int quantityCount = itemsAppliedToRule.parallelStream().mapToInt(item -> Integer.parseInt(item.getQuantity())).sum();

            // apply discount and generate the response
            if (priceCheck && quantityCount >= ITEMS_WITH_EQUAL_PRICE_SIZE) {
                itemsAppliedToRule.forEach(item -> {
                    final Double totalPrice = IDiscountRule.calculateTotalPrice(item.getPrice(), item.getQuantity());
                    final Receipt receipt = new Receipt(totalPrice, totalPrice * DISCOUNT_PERCENTAGE, totalPrice - totalPrice * DISCOUNT_PERCENTAGE, item.getItemId(), item.getQuantity());
                    receiptList.add(receipt);
                });
            }
        }
        logger.info("items processed successfully");

        return receiptList;
    }
}

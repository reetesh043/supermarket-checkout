package com.reet.rules;

import com.reet.domain.Item;
import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.RuleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 *  Implementation of rule to buy two equal price item on a special price
 */
public class BuyTwoItemsAndGetForSpecialPrice implements IDiscountRule {

    private static Logger logger = Logger.getLogger("BuyTwoItemsAndGetForSpecialPrice");

    public static final int ITEMS_WITH_EQUAL_PRICE_SIZE = 2;

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
        List<Item> itemsAppliedToRule = filterByCriteria(items, rules, RuleEnum.BUY_TWO_EQUAL_PRICE_FOR_SPECIAL_PRICE.name());

        List<Receipt> receiptList = new ArrayList<>();
        if (0 != itemsAppliedToRule.size()) {
            // list of item with same price
            List<List<Item>> itemsWithSamePrice = itemsAppliedToRule.stream().collect(Collectors.groupingBy(Item::getPrice)).values().stream()
                    .filter(itemWithSamePrice -> itemWithSamePrice.size() >= ITEMS_WITH_EQUAL_PRICE_SIZE)
                    .collect(Collectors.toList());

            // apply discount and generate the response
            itemsWithSamePrice.stream().forEach(itemList -> {
                itemList.stream().forEach(item -> {
                    final Double totalPrice = IDiscountRule.calculateTotalPrice(item.getPrice(), item.getQuantity());
                    final Optional<String> price = rules.stream().filter(rule -> rule.getItemId().equals(item.getItemId())).map(Rule::getFinalPrice).findAny();
                    final Double finalPrice = IDiscountRule.calculateTotalPrice(price.get(), item.getQuantity());
                    final Receipt receipt = new Receipt(totalPrice, finalPrice, totalPrice - finalPrice, item.getItemId(), item.getQuantity());
                    receiptList.add(receipt);
                });
            });
        }
        logger.info("items processed successfully");
        return receiptList;
    }
}

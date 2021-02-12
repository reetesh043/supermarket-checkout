package com.reet.rules;

import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.RuleEnum;
import com.reet.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 *  Implementation of rule to buy three in same group and get cheapest item free
 */
public class BuyThreeItemsInGroupAndGetCheapestFree implements IDiscountRule {


    private static Logger logger = Logger.getLogger("BuyThreeItemsInGroupAndGetCheapestFree");


    public static final int ITEMS_IN_SAME_GROUP_SIZE = 3;

    /**
     * Buy set of three items and get the cheapest one for free.
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
        List<Item> itemsAppliedToRule = filterByCriteria(items, rules, RuleEnum.BUY_THREE_EQUAL_PRICE_IN_GROUP_GET_CHEAPEST_FREE.name());

        // get the count of each item in a group
        Map<String, List<Item>> itemsPerGroup = itemsAppliedToRule.stream().collect(Collectors.groupingBy(Item::getGroupId));

        // apply discount and generate the response
        List<Receipt> receiptList = new ArrayList<>();
        if (0 != itemsAppliedToRule.size()) {
            itemsPerGroup.forEach((groupId, itemList) -> {
                if (itemList.size() >= ITEMS_IN_SAME_GROUP_SIZE) {
                    final Double minPrice = IDiscountRule.calculateTotalPrice(itemList.get(0).getPrice(), itemList.get(0).getQuantity());
                    itemList.forEach(selectedItem -> {
                        final Double totalPrice = IDiscountRule.calculateTotalPrice(selectedItem.getPrice(), selectedItem.getQuantity());
                        final Double finalPrice = 0 == totalPrice.compareTo(minPrice) ? 0 : totalPrice;
                        final Receipt receipt = new Receipt(totalPrice, finalPrice, totalPrice - finalPrice, selectedItem.getItemId(), selectedItem.getQuantity());
                        receiptList.add(receipt);
                    });
                }
            });
        }
        logger.info("items processed successfully");

        return receiptList;
    }
}

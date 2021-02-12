package com.reet.rules;

import com.reet.domain.Item;
import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.RuleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/*
 *  Implementation of rule to buy Buy N Items X Get M Items Y free.
 */
public class BuyNItemsXGetMItemsY implements IDiscountRule {

    private static Logger logger = Logger.getLogger("BuyNItemsXGetMItemsY");

    /**
     * Buy Buy N Items X Get M Items Y free.
     *
     * @param items - items to be used for calculating the discount
     * @param rules - contains the rules which to be applied on items
     * @return responseList - list of generated response
     */
    @Override
    public List<Receipt> applyDiscountRule(final List<Item> items, final List<Rule> rules) {

        logger.info("processing the items");
        IDiscountRule.validate(items, rules);
        // retrieve the items to which this rule will apply
        List<Item> itemsAppliedToRule = filterByCriteria(items, rules, RuleEnum.BUY_N_ITEM_X_GET_M_ITEMS_Y.name());

        //apply discount rule
        List<Receipt> receiptList = new ArrayList<>();

        if (0 != itemsAppliedToRule.size()) {
            itemsAppliedToRule.stream().forEach(item -> {
                String itemIdToBuy;
                String noOfItemsToBuy;
                String freeItemId;
                String noOfItemsFree;
                Optional<String> discountRule = rules.stream().filter(rule -> rule.getItemId().equals(item.getItemId())).map(Rule::getRuleCondition).findAny();

                // get the rule config from the rules object
                String[] configs = discountRule.isPresent() ? discountRule.get().split(" ") : null;
                if (configs.length == 4) {
                    noOfItemsToBuy = configs[0];
                    itemIdToBuy = configs[1];
                    noOfItemsFree = configs[2];
                    freeItemId = configs[3];
                } else {
                    throw new IllegalArgumentException("rule config must contain the correct config and pass N, X, M, Y");
                }
                String finalFreeItemId = freeItemId;
                Item freeItemObj = items.stream().filter(id -> id.getItemId().equals(finalFreeItemId)).findAny().orElse(null);
                if (Integer.parseInt(item.getQuantity()) >= Integer.parseInt(noOfItemsToBuy)
                        && (Objects.nonNull(freeItemObj) && Integer.parseInt(freeItemObj.getQuantity()) >= Integer.parseInt(noOfItemsFree))) {
                    //set the receipt of item to be bought
                    final Double totalPriceOfItemToBuy = IDiscountRule.calculateTotalPrice(item.getPrice(), noOfItemsToBuy);
                    final Receipt receiptOfItemToBuy = new Receipt(totalPriceOfItemToBuy, totalPriceOfItemToBuy, 0.0, itemIdToBuy, noOfItemsToBuy);
                    //set the receipt of item to be given free with item to be bought
                    final Double totalPriceOfFreeItem = IDiscountRule.calculateTotalPrice(freeItemObj.getPrice(), noOfItemsFree);
                    final Receipt receiptOfFreeItem = new Receipt(totalPriceOfFreeItem, 0.0, totalPriceOfFreeItem, freeItemId, noOfItemsFree);
                    receiptList.add(receiptOfItemToBuy);
                    receiptList.add(receiptOfFreeItem);
                }
            });
        }
        logger.info("items processed successfully");
        return receiptList;
    }


}


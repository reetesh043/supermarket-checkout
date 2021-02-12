package com.reet;

import com.opencsv.bean.CsvToBeanBuilder;
import com.reet.domain.Item;
import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.domain.RuleEnum;
import com.reet.exception.InvalidRequestException;
import com.reet.exception.ResourceAccessException;
import com.reet.rules.IDiscountRule;
import com.reet.utils.CheckoutUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/*
 * Entry class for the checkout system.
 */
public class CheckoutApp {

    private static Logger logger = Logger.getLogger("CheckoutApp");

    /**
     * Apply all price rules to the list of items
     */
    public static void applyDiscountRules(final String itemsFilePath, final String rulesFilePath) {
        logger.info("process the items and rules csv files");
        if (null == itemsFilePath || itemsFilePath.isEmpty() || null == rulesFilePath || rulesFilePath.isEmpty()) {
            throw new ResourceAccessException("items and rules csv file path must not be empty");
        }
        try {
            final List<Item> items = new CsvToBeanBuilder(new FileReader(itemsFilePath))
                    .withType(Item.class)
                    .build()
                    .parse();
            logger.info("items file size" + ":" + items.size());
            List<Rule> rules = new CsvToBeanBuilder(new FileReader(rulesFilePath))
                    .withType(Rule.class)
                    .build()
                    .parse();
            logger.info("rules file size" + ":" + rules.size());
            if (null == items || items.isEmpty() || null == rules || rules.isEmpty()) {
                throw new InvalidRequestException("number of items and rules applied to them must not be null");
            }
            List<Receipt> processedItemsWithoutDiscount = processItemsWithoutDiscount(items, rules);
            List<List<Receipt>> listsOfReceipt = processItemsWithDiscount(items, rules);
            listsOfReceipt.add(processedItemsWithoutDiscount);
            generateFinalReceipt(listsOfReceipt);

        } catch (Exception e) {
            logger.severe("Error occurred" + ":" + e.getMessage());
            throw new RuntimeException("error occurred while processing items and rules files");
        }

    }

    private static void generateFinalReceipt(final List<List<Receipt>> listsOfReceipt) {
        List<Receipt> listOfReceipt = Optional.ofNullable(listsOfReceipt).orElseGet(Collections::emptyList).stream().flatMap(List::stream).collect(Collectors.toList());
        //total amount of all the items
        final Double[] totalAmount = {0.0};
        //total discounted amount on all the items
        final Double[] totalDiscountedAmount = {0.0};
        //Apply the best offer based on final price after applying discount, if an item is used in multiple offers and create final receipt.
        List<Receipt> finalReceipt = new ArrayList<>();
        if (0 != listOfReceipt.size()) {
            new ArrayList<>(listOfReceipt.stream().collect(
                    Collectors.groupingBy(r1 -> Arrays.asList(r1.getItemId(), r1.getItemId()),
                            Collectors.collectingAndThen(
                                    Collectors.minBy(Comparator.comparing(Receipt::getFinalPrice)),
                                    Optional::get))).values()).stream().forEach(receipt -> {
                totalAmount[0] = totalAmount[0] + receipt.getFinalPrice();
                totalDiscountedAmount[0] = totalDiscountedAmount[0] + receipt.getDiscount();
                finalReceipt.add(receipt);
            });
        }

        //Print final receipt to console. Same output can be written in either csv or .txt file.
        printReceipt(finalReceipt, totalAmount[0], totalDiscountedAmount[0]);
    }

    /*
     *  process and return all the items with discount
     */
    private static List<List<Receipt>> processItemsWithDiscount(final List<Item> finalItems,
                                                                final List<Rule> finalItemsWithRules) {
        List<CompletableFuture<List<Receipt>>> listCompletableFuture =
                Arrays.asList(RuleEnum.values()).stream()
                        .map(discountId -> CompletableFuture.supplyAsync(() ->
                                RulesFactory.createDiscountRule(discountId.getDiscount()).applyDiscountRule(finalItems, finalItemsWithRules)))
                        .collect(Collectors.toList());
        return listCompletableFuture.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /*
     *  process and return all the items without discount
     */
    private static List<Receipt> processItemsWithoutDiscount(final List<Item> items, final List<Rule> rules) {

        //get the item id without rule
        List<Item> itemWithoutRules = new ArrayList<>();
        rules.stream().filter(rule -> rule.getRuleName().isEmpty()).map(rule -> rule.getItemId())
                .forEach(allowedItemId -> items.stream().filter(item -> item.getItemId().equals(allowedItemId)).forEach(itemWithoutRules::add));
        List<Receipt> receiptList = new ArrayList<>();
        //process the items and generate response
        if (0 != itemWithoutRules.size()) {
            itemWithoutRules.stream().forEach(item -> {
                final Double finalPrice = IDiscountRule.calculateTotalPrice(item.getPrice(), item.getQuantity());
                final Receipt receipt = new Receipt(finalPrice, finalPrice, 0.0, item.getItemId(), item.getQuantity());
                receiptList.add(receipt);
            });
        }
        return receiptList;
    }


    /*
     *  print the final receipt to console and write the receipt to txt file inside project folder.
     */
    private static void printReceipt(List<Receipt> finalReceipt, Double totalAmount, Double totalDiscountedAmount) {
        AtomicInteger line = new AtomicInteger(1);
        StringBuilder builder = new StringBuilder();
        CheckoutUtil.printHeader(builder);
        Optional.ofNullable(finalReceipt).orElse(new ArrayList<>()).stream().sorted(comparing(Receipt::getItemId))
                .forEach(item -> {
                    CheckoutUtil.printLine(item, builder, line.incrementAndGet());
                });
        CheckoutUtil.printFooter(builder, BigDecimal.valueOf(totalAmount), BigDecimal.valueOf(totalDiscountedAmount));

        logger.info(builder.toString());
        //write the final receipt to a text file
        try {
            File f = new File("receipt.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(builder.toString());
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            logger.severe("error occurred while writing data to file");
        }
    }

    /*
     *  Entry method of checkout application
     */
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            logger.severe("You suppose to enter two files, first for items information and second for rules information");
            throw new IllegalArgumentException("Invalid argument provided");
        }
        new CheckoutApp().applyDiscountRules(args[0], args[1]);
    }
}

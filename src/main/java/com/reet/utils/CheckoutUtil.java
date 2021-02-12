package com.reet.utils;


import com.reet.domain.Receipt;

import java.math.BigDecimal;
import java.text.NumberFormat;

/*
* Utility class to print the receipt.
*/
public class CheckoutUtil {

    private CheckoutUtil() {
        //default  private constructor;
    }

    private static NumberFormat format = NumberFormat.getCurrencyInstance();
    private static String spaces100 = "                                                                                                    ";
    private static String bottom100 = "---------------------------------------------------------------------------------------------------";
    private static String itemName = "ItemID";
    private static String normalPrice = "UnitPrice";
    private static String quantity = "Quantity";
    private static String discount = "Discount";
    private static String afterDiscount = "After Discount";

    public static void printLine(Receipt item, StringBuilder builder, int lineNumber) {
        int start = lineNumber * 100;
        builder.append(spaces100);

        String id = "id=" + item.getItemId();

        builder.replace(start, start + id.length(), id);
        start += 20;
        builder.replace(start, start + getFormattedLength(new BigDecimal(item.getOriginalPrice())), getFormatted(new BigDecimal(item.getOriginalPrice())));
        start += 20;
        builder.replace(start, start + getFormattedLength(new BigDecimal(item.getQuantity())), getFormatted(new BigDecimal(item.getQuantity())));
        start += 20;
        builder.replace(start, start + getFormattedLength(new BigDecimal(item.getDiscount())), getFormatted(new BigDecimal(item.getDiscount())));
        start += 20;
        builder.replace(start, start + getFormattedLength(new BigDecimal(item.getFinalPrice())), getFormatted(new BigDecimal(item.getFinalPrice())));
        start += 19;
        builder.replace(start, start + System.lineSeparator().length(), System.lineSeparator());
    }

    private static int getFormattedLength(BigDecimal decimal) {
        return format.format(decimal).length();
    }

    private static String getFormatted(BigDecimal decimal) {
        return format.format(decimal);
    }

    public static void printHeader(StringBuilder builder) {
        int start = 0;
        builder.append(spaces100);
        builder.replace(start, start + itemName.length(), itemName);
        start += 20;
        builder.replace(start, start + normalPrice.length(), normalPrice);
        start += 20;
        builder.replace(start, start + quantity.length(), quantity);
        start += 20;
        builder.replace(start, start + discount.length(), discount);
        start += 20;
        builder.replace(start, start + afterDiscount.length(), afterDiscount);
        start += 19;
        builder.replace(start, start + System.lineSeparator().length(), System.lineSeparator());
        builder.append(bottom100).append(System.lineSeparator());

    }

    public static void printFooter(StringBuilder builder, BigDecimal total, BigDecimal totalDiscount) {
        builder.append(bottom100).append(System.lineSeparator());
        builder.append("Total discount:   ").append(format.format(totalDiscount)).append(System.lineSeparator());
        builder.append(bottom100).append(System.lineSeparator());
        builder.append("Total:   ").append(format.format(total)).append(System.lineSeparator());
    }
}


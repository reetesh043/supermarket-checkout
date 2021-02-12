package com.reet.rules;

import com.reet.domain.Item;
import com.reet.domain.Receipt;
import com.reet.domain.Rule;
import com.reet.exception.InvalidRequestException;
import com.reet.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Buy Three Items In Group And Get Cheapest Free Test")
public class BuyThreeItemsInGroupAndGetCheapestFreeTest {

    private BuyThreeItemsInGroupAndGetCheapestFree buyThreeItemsInGroupAndGetCheapestFree;
    private List<Item> items;
    private List<Rule> rules;

    @BeforeEach
    private void setUp() throws FileNotFoundException {
        buyThreeItemsInGroupAndGetCheapestFree = new BuyThreeItemsInGroupAndGetCheapestFree();
        items = TestUtil.getItems("src/test/resources/items.csv");
        rules = TestUtil.getRules("src/test/resources/rules.csv");
    }

    /**
     * @param
     */
    @DisplayName("Test success response for Buy Three Items In Group And Get Cheapest Free")
    @Test
    public void success_BuyThreeItemsInGroupAndGetCheapestFree() {
        List<Receipt> receipt = buyThreeItemsInGroupAndGetCheapestFree.applyDiscountRule(items, rules);
        Assertions.assertNotNull(receipt);
        Assertions.assertEquals(6, receipt.size());
    }

    /**
     * @param
     */
    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfItemsAreEmpty() {
        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyThreeItemsInGroupAndGetCheapestFree.applyDiscountRule(null, rules);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfRulesAreEmpty() {
        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyThreeItemsInGroupAndGetCheapestFree.applyDiscountRule(items, null);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

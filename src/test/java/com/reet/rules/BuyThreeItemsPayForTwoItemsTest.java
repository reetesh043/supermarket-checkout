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
@DisplayName("Buy Three Items Pay For Two Items Test")
public class BuyThreeItemsPayForTwoItemsTest {

    private BuyThreeItemsPayForTwoItems buyThreeItemsPayForTwoItems;
    private List<Item> items;
    private List<Rule> rules;

    @BeforeEach
    private void setUp() throws FileNotFoundException {
        buyThreeItemsPayForTwoItems = new BuyThreeItemsPayForTwoItems();
        items = TestUtil.getItems("src/test/resources/items.csv");
        rules = TestUtil.getRules("src/test/resources/rules.csv");
    }

    /**
     * @param
     */
    @DisplayName("Test Buy Three Items Pay For Two Items")
    @Test
    public void success_BuyThreeItemsInGroupAndGetCheapestFree() {
        List<Receipt> receipt = buyThreeItemsPayForTwoItems.applyDiscountRule(items, rules);
        Assertions.assertNotNull(receipt);
        Assertions.assertEquals(3, receipt.size());
    }

    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfItemsAreEmpty() {

        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyThreeItemsPayForTwoItems.applyDiscountRule(null, rules);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfRulesAreEmpty() {

        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyThreeItemsPayForTwoItems.applyDiscountRule(items, null);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

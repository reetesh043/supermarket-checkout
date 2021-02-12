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
@DisplayName("Buy N Items X Get M Items Y Test")
public class BuyNItemsXGetMItemsYTest {

    private BuyNItemsXGetMItemsY buyNItemsXGetMItemsY;

    private List<Item> items;
    private List<Rule> rules;

    @BeforeEach
    private void setUp() {
        buyNItemsXGetMItemsY = new BuyNItemsXGetMItemsY();
    }

    /**
     * @param
     */
    @DisplayName("Test success response for Buy N Items X Get M Items Y ")
    @Test
    public void success_BuyNItemsXGetMItemsYTest() throws FileNotFoundException {
        rules = TestUtil.getRules("src/test/resources/rules.csv");
        items = TestUtil.getItems("src/test/resources/items.csv");
        List<Receipt> receipt = buyNItemsXGetMItemsY.applyDiscountRule(items, rules);
        Assertions.assertNotNull(receipt);
        Assertions.assertEquals(2, receipt.size());
    }

    @DisplayName("Throw exception if N X M K Values are not provided correctly")
    @Test
    public void shouldThrowException() throws FileNotFoundException {
        rules = TestUtil.getRules("src/test/resources/invalid_rules_config.csv");
        items = TestUtil.getItems("src/test/resources/items.csv");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            buyNItemsXGetMItemsY.applyDiscountRule(items, rules);
        });
        String expectedMessage = "rule config must contain the correct config and pass N, X, M, Y";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfItemsAreEmpty() {

        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyNItemsXGetMItemsY.applyDiscountRule(null, rules);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should throw exception if items is null")
    @Test
    public void shouldThrowExceptionIfRulesAreEmpty() {

        Exception exception = Assertions.assertThrows(InvalidRequestException.class, () -> {
            buyNItemsXGetMItemsY.applyDiscountRule(items, null);
        });
        String expectedMessage = "number of items and rules applied to them must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


}

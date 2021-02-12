package com.reet;

import com.reet.domain.Item;
import com.reet.domain.Rule;
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
@DisplayName("Checkout App Main Class Test")
public class CheckoutAppTest {

    private CheckoutApp checkoutApp;
    private List<Item> items;
    private List<Rule> rules;

    @BeforeEach
    private void setUp() throws FileNotFoundException {
        checkoutApp = new CheckoutApp();
    }

    @DisplayName("Should throw exception if one argument is passed")
    @Test
    public void shouldThrowExceptionIfOneArgumentIsPassed() {
        String[] args = {"./items.csv"};

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutApp.main(args);
        });
        String expectedMessage = "Invalid argument provided";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should throw exception if null argument is passed")
    @Test
    public void shouldThrowExceptionIfArgumentIsNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutApp.main(null);
        });
        String expectedMessage = "Invalid argument provided";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should throw exception if file path is invalid")
    @Test
    public void shouldThrowExceptionIfArgumentIsInvalid() {
        String[] args = {"1", "2"};

        Assertions.assertThrows(RuntimeException.class, () -> {
            checkoutApp.main(args);
        });
    }

    @DisplayName("Process successfully")
    @Test
    public void processSuccessfullyIfCorrectArgsArePassed() {
        String[] args = {"./items.csv", "./rules.csv"};
        checkoutApp.main(args);
    }

    @DisplayName("Throw exception if file content is empty")
    @Test
    public void throwExceptionIfFileContentIsEmpty() {
        String[] args = {"src/test/resources/invalid_items.csv", "./rules.csv"};
        Assertions.assertThrows(RuntimeException.class, () -> {
            checkoutApp.main(args);
        });
    }
}

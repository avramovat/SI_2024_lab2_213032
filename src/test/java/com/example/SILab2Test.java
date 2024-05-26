package com.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    public void testAllItemsNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, 100);
        });
        assertEquals("allItems list can't be null!", exception.getMessage());
    }

    @Test
    public void testNameIsNull() {
        List<Item> items = List.of(new Item(null, "123456", 100, 0.1f));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    public void testNameIsEmpty() {
        List<Item> items = List.of(new Item("", "123456", 100, 0.1f));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    public void testBarcodeIsNull() {
        List<Item> items = List.of(new Item("Item1", null, 100, 0.1f));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items, 100);
        });
        assertEquals("No barcode!", exception.getMessage());
    }

    @Test
    public void testInvalidCharacterInBarcode() {
        List<Item> items = List.of(new Item("Item1", "12345a", 100, 0.1f));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items, 100);
        });
        assertEquals("Invalid character in item barcode!", exception.getMessage());
    }

    @Test
    public void testDiscountGreaterThanZero() {
        List<Item> items = List.of(new Item("Item1", "123456", 100, 0.1f));
        assertTrue(SILab2.checkCart(items, 110));
    }

    @Test
    public void testDiscountIsZero() {
        List<Item> items = List.of(new Item("Item1", "123456", 100, 0.0f));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    public void testPriceGreaterThan300AndDiscountGreaterThanZeroAndBarcodeStartsWithZero() {
        List<Item> items = List.of(new Item("Item1", "012345", 400, 0.1f));
        assertTrue(SILab2.checkCart(items, 330));
    }

    @Test
    public void testSumGreaterThanPayment() {
        List<Item> items = List.of(new Item("Item1", "123456", 100, 0.1f));
        assertFalse(SILab2.checkCart(items, 5));
    }

    @Test
    public void testMultipleItems() {
        List<Item> items = List.of(
                new Item("Item1", "123456", 100, 0.1f),
                new Item("Item2", "234567", 200, 0.0f),
                new Item("Item3", "345678", 300, 0.15f)
        );
        assertTrue(SILab2.checkCart(items, 550));
    }

    @Test
    public void testMultipleItemsWithDiscountsAndHighPrice() {
        List<Item> items = List.of(
                new Item("Item1", "012345", 350, 0.2f),
                new Item("Item2", "123456", 400, 0.1f)
        );
        assertTrue(SILab2.checkCart(items, 620)); // 350*0.2 + 400*0.1 - 30 = 620
    }
}

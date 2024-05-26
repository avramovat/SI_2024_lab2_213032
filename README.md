SI_2024_lab2_213032
Тијана Аврамова 213032

**Control Flow Graph**
![Control Flow Graph]("C:\Users\Lenovo\Downloads\cfg.drawio.png")

**Цикломатска комплексност**
Цикломатската комплексност на дадениот код е 10. Оваа пресметка е направена користејќи ја формулата V(G) = P + 1, каде што:
P(предикатни јазли) = 9

 V(G) = 9 + 1 = 10

Ова значи дека постојат 10 независни патеки низ програмскиот код, што укажува на потребата од најмалку 10 тест случаи за целосно покривање на сите можни патеки.

**Тест случаи според критериумот Every statement**

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

1.testAllItemsNull: Проверува дали се фрла исклучок кога листата е null.
2.testNameIsNull: Проверува дали името се поставува на "unknown" кога е null.
3.testNameIsEmpty: Проверува дали името се поставува на "unknown" кога е празно.
4.testBarcodeIsNull: Проверува дали се фрла исклучок кога баркодот е null.
5.testInvalidCharacterInBarcode: Проверува дали се фрла исклучок кога баркодот содржи невалиден карактер.
6.testDiscountGreaterThanZero: Проверува дали се применува попустот кога е поголем од нула.
7.testDiscountIsZero: Проверува дали не се применува попуст кога е нула.
8.testPriceGreaterThan300AndDiscountGreaterThanZeroAndBarcodeStartsWithZero: Проверува дали се намалува 30 од вкупната цена кога цената е поголема од 300, попустот е поголем од нула и баркодот почнува со 0.
9.testSumGreaterThanPayment: Проверува дали се враќа false кога сумата е поголема од уплатата.
10.testMultipleItems: Проверува дали функцијата работи правилно со повеќе предмети со различни попусти.
11.testMultipleItemsWithDiscountsAndHighPrice: Проверува комбинација на предмети со попусти и висока цена, вклучувајќи ја и логиката за намалување од 30.

**тест случаи според Multiple Condition критериумот**
**Тест случај 1**
Сценарио: item.getPrice() > 300 е вистинито, item.getDiscount() > 0 е вистинито, item.getBarcode().charAt(0) == '0' е вистинито.

@Test
public void testPriceGreaterThan300DiscountGreaterThanZeroBarcodeStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "012345", 400, 0.1f));
    assertTrue(SILab2.checkCart(items, 330)); // Вкупната цена е 400*0.1 = 40 - 30 = 10, 10 <= 330 -> true
}

**Тест случај 2**
Сценарио: item.getPrice() > 300 е вистинито, item.getDiscount() > 0 е вистинито, item.getBarcode().charAt(0) == '0' е невистинито.

@Test
public void testPriceGreaterThan300DiscountGreaterThanZeroBarcodeNotStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "112345", 400, 0.1f));
    assertTrue(SILab2.checkCart(items, 370)); // Вкупната цена е 400*0.1 = 40, 40 <= 370 -> true
}

**Тест случај 3**
Сценарио: item.getPrice() > 300 е вистинито, item.getDiscount() > 0 е невистинито, item.getBarcode().charAt(0) == '0' е вистинито.

@Test
public void testPriceGreaterThan300DiscountIsZeroBarcodeStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "012345", 400, 0.0f));
    assertTrue(SILab2.checkCart(items, 400)); // Вкупната цена е 400, 400 <= 400 -> true
}

**Тест случај 4**
Сценарио: item.getPrice() > 300 е вистинито, item.getDiscount() > 0 е невистинито, item.getBarcode().charAt(0) == '0' е невистинито.

@Test
public void testPriceGreaterThan300DiscountIsZeroBarcodeNotStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "112345", 400, 0.0f));
    assertTrue(SILab2.checkCart(items, 400)); // Вкупната цена е 400, 400 <= 400 -> true
}

**Тест случај 5**
Сценарио: item.getPrice() > 300 е невистинито, item.getDiscount() > 0 е вистинито, item.getBarcode().charAt(0) == '0' е вистинито.

@Test
public void testPriceIsNotGreaterThan300DiscountGreaterThanZeroBarcodeStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "012345", 200, 0.1f));
    assertTrue(SILab2.checkCart(items, 200)); // Вкупната цена е 200*0.1 = 20, 20 <= 200 -> true
}

**Тест случај 6**
Сценарио: item.getPrice() > 300 е невистинито, item.getDiscount() > 0 е вистинито, item.getBarcode().charAt(0) == '0' е невистинито.

@Test
public void testPriceIsNotGreaterThan300DiscountGreaterThanZeroBarcodeNotStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "112345", 200, 0.1f));
    assertTrue(SILab2.checkCart(items, 200)); // Вкупната цена е 200*0.1 = 20, 20 <= 200 -> true
}

**Тест случај 7**
Сценарио: item.getPrice() > 300 е невистинито, item.getDiscount() > 0 е невистинито, item.getBarcode().charAt(0) == '0' е вистинито.

@Test
public void testPriceIsNotGreaterThan300DiscountIsZeroBarcodeStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "012345", 200, 0.0f));
    assertTrue(SILab2.checkCart(items, 200)); // Вкупната цена е 200, 200 <= 200 -> true
}

**Тест случај 8**
Сценарио: item.getPrice() > 300 е невистинито, item.getDiscount() > 0 е невистинито, item.getBarcode().charAt(0) == '0' е невистинито.

@Test
public void testPriceIsNotGreaterThan300DiscountIsZeroBarcodeNotStartsWithZero() {
    List<Item> items = List.of(new Item("Item1", "112345", 200, 0.0f));
    assertTrue(SILab2.checkCart(items, 200)); // Вкупната цена е 200, 200 <= 200 -> true
}



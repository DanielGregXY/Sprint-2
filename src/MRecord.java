public class MRecord {

    String itemName; // название товара;

boolean isExpense; // одно из двух значений: TRUE или FALSE. Обозначает, является ли запись тратой (TRUE) или доходом (FALSE);
int quantity; // количество закупленного или проданного товара;
int sumOfOne; //  стоимость одной единицы товара. Целое число.
// aa
    public MRecord(String itemName, boolean isExpense, int quantity, int sumOfOne) {

        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }



}



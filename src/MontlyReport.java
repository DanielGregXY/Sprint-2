import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class MontlyReport {

    File folder = new File("C:\\Users\\Daniel Greg\\dev\\Sprint-two\\yapraI\\resources");

    File[] listOfFiles = folder.listFiles(); // Считываем все файлы

    ArrayList<ArrayList<MRecord>> mreport = new ArrayList<ArrayList<MRecord>>(); // список "отчет за месяц"

    ArrayList<MonthlyExpenses> monthlyExpenses = new ArrayList<>(); // Список доходов и расходов по месяцам

    YearlyReport yearlyReport = new YearlyReport(2021, "resources/y.2021.csv"); // считаем данные года

    MonthHelper monthHelper = new MonthHelper(); // считаем данные года

    public MontlyReport() { // получаем и дробим данные из файлов

        for (File file : listOfFiles) { // заводим в цикл файлы месяцев начинающиеся с "m".
            if (file.isFile()) {
                if (file.getName().charAt(0) == 'm') {

                    String content = readFileContentsOrNull("resources/" + file.getName());
                    String[] lines = content.split("\r?\n");
                    ArrayList<MRecord> month = new ArrayList<>();

                    for (int i = 1; i < lines.length; i++) {

                        String line = lines[i];
                        String[] parts = line.split(","); // ["01", "350000", "true"]
                        String itemName = parts[0];
                        boolean isExpense = Boolean.parseBoolean(parts[1]);
                        int quantity = Integer.parseInt(parts[2]);
                        int sumOfOne = Integer.parseInt(parts[3]);

                        month.add(new MRecord(itemName, isExpense, quantity, sumOfOne));
                    }
                    mreport.add(month);
                }
            }
        }
    }

    private String readFileContentsOrNull(String path) {

        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public String getMonthlyRecord() { // Месячный отчет

        String result = "";

        for (ArrayList<MRecord> month : mreport) { //Вытаскиваем список из списка.
            for (MRecord item : month) {
                result += String.format(item.itemName + " " + item.isExpense + " " + item.quantity + " " + item.sumOfOne + "\n");
            }
            result += "\n";
        }

        return result;
    }

    public ArrayList<MonthlyExpenses> getMonthlyExpenses() { //возвращает расходы за определенный месяц


        for (int i = 0; i < mreport.size(); i++) {
            ArrayList<MRecord> month = mreport.get(i);
            int sumExpenses = 0;
            int sumIncomes = 0;
            for(MRecord item : month)// обращаемся к месяцу
                if (item.isExpense){
                    sumExpenses += item.sumOfOne * item.quantity;
                } else {
                    sumIncomes += item.sumOfOne * item.quantity;
                }
                monthlyExpenses.add(new MonthlyExpenses(sumIncomes, sumExpenses, i + 1));
        }
        return monthlyExpenses;
    }

    public Integer isExpenseFalse() { //возвращает доходы за определенный месяц

        ArrayList<MRecord> month = mreport.get(0);

        int monthExpense = 0;

        for (MRecord item : month) { // обращаемся к месяцу
                if (!item.isExpense){
                    monthExpense = item.sumOfOne * item.quantity;
                }

        }
        return monthExpense;
    }

    public void compareExpense() { // сравнивает расходы по месяцам

        ArrayList<MonthlyExpenses> monthlyExpenses = getMonthlyExpenses();

        ArrayList<YearlyExpenses> yearlyExpenses = yearlyReport.getYearlyExpenses();

        for (int i = 0; i < monthlyExpenses.size(); i++) {
            if ((monthlyExpenses.get(i).income == yearlyExpenses.get(i).income) && (monthlyExpenses.get(i).expense == yearlyExpenses.get(i).expense)){
                System.out.println("По месяцу: " + (i + 1) + " ошибок не было выявлено.");
            }else {
                System.out.println("В месяце: " + (i + 1) + " выявлена ошибка.");
            }
        }
    }


    public String maxExpenseMonthly() { //Максимальная трата

        int index = 0;

        StringBuilder result = new StringBuilder();

        for (ArrayList<MRecord> month : mreport) {
            index++;
            int max = 0;
            MRecord item = null;
            MRecord itemExpense = null;
            int maxExpense = 0;
            for(MRecord row : month){
               if (!row.isExpense){
                   if (max < (row.quantity * row.sumOfOne)){
                       max = row.quantity * row.sumOfOne;
                       item = row;
                   }
               } else {
                   if (maxExpense < row.sumOfOne * row.quantity) {
                       maxExpense = row.sumOfOne * row.quantity;
                       itemExpense = row;
                   }
               }
            }
            result.append(monthHelper.getMonthName(index))
                    .append(": Самый прибыльный товар на сумму: ")
                    .append(max)
                    .append(" - ")
                    .append(item.itemName)
                    .append(".")
                    .append(" Максимальная трата на сумму: ")
                    .append(itemExpense.sumOfOne * itemExpense.quantity)
                    .append(" - ")
                    .append(itemExpense.itemName)
                    .append(".")
                    .append('\n');
        }

        return result.toString();
    }
}
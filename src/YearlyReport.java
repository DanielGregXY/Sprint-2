import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
// aa
public class YearlyReport {
    int year;

    ArrayList<YRecord> yearRows = new ArrayList<>();

    ArrayList<YearlyExpenses> yearlyExpenses = new ArrayList<>(); // Список доходов и расходов по месяцам

    MonthHelper monthHelper = new MonthHelper(); // считаем данные года

    public YearlyReport(int year, String path) { // получаем и дробим данные из файлов

        this.year = year;

        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(","); // ["01", "350000", "true"]
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);
            yearRows.add(new YRecord(month, amount, isExpense));
        }
    }

    public String getYearlyRecord() {

        String result = "";

        for (YRecord item : yearRows) {
            result += String.format(item.month + " " + item.amount + " " + item.isExpense + "\n");

        }
        return result;
    }

    public ArrayList<YearlyExpenses> getYearlyExpenses() { //возвращает расходы за определенный месяц

        for (int i = 0; i < yearRows.size(); i += 2) {

                int sumExpenses = 0;
                int sumIncomes = 0;
                int sumExpenses1 = 0;
                int sumIncomes1 = 0;

                if (yearRows.get(i).isExpense) {
                    sumExpenses = yearRows.get(i).amount;
                } else {
                    sumIncomes = yearRows.get(i).amount;
                }
                if (yearRows.get(i + 1).isExpense) {
                    sumExpenses1 = yearRows.get(i + 1).amount;
                } else {
                    sumIncomes1 = yearRows.get(i + 1).amount;
                }

                yearlyExpenses.add((new YearlyExpenses(sumIncomes + sumIncomes1, sumExpenses + sumExpenses1, yearRows.get(i).month)));
            }

        return yearlyExpenses;
    }

    public StringBuilder getYearlyReport() {

        getYearlyExpenses();

        StringBuilder result = new StringBuilder();
        int income = 0;
        int index = 0;
        int averageExpense = 0;
        int averageIncome = 0;


        for (YearlyExpenses month: yearlyExpenses) {
            averageExpense += month.expense;
        }


        for (YearlyExpenses month: yearlyExpenses) {
            averageIncome += month.income;
        }


        for (YearlyExpenses month: yearlyExpenses) {
            index += 1;
            result.append(monthHelper.getMonthName(index))
                    .append(": Прибыль: ")
                    .append(month.income - month.expense)
                    .append(".")
                    .append("\n");
        }

        result.append("Средние расходы за все месяцы: ")
                .append(averageExpense / yearlyExpenses.size())
                .append(".")
                .append(" Средние доходы за все месяцы: ")
                .append(averageIncome / yearlyExpenses.size())
                .append(".");

        return result;
    }


    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

}

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MontlyReport montlyReport = new MontlyReport();
        YearlyReport yearlyReport = new YearlyReport(2021, "resources/y.2021.csv"); // считаем данные года

        System.out.println("Здравствуйте! Что вы хотите сделать?");
        System.out.println("Доступные функции программы:");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Завершить работу программы. ");
        Scanner scanner = new Scanner(System.in);

        while (true) { // Свич кейс

            int command = scanner.nextInt();

            switch (command) {
                case 1 -> System.out.println("Ваш месячный отчёт: \n" + montlyReport.getMonthlyRecord());
                case 2 -> System.out.println("Ваш годовой отчёт: \n" + yearlyReport.getYearlyRecord());
                case 3 -> {
                    System.out.println("Ваш отчёт сверки: \n");
                    montlyReport.compareExpense();
                }
                case 4 -> System.out.println("Информация о всех месячных отчётах: \n" + montlyReport.maxExpenseMonthly());
                case 5 -> System.out.println("Информация о годовом отчёте: \n" + yearlyReport.getYearlyReport());
                case 0 -> System.out.println("Завершение работы... ");
                default -> System.out.println("Извините, такой команды нет.");
            }
        }

    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}






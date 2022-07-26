import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MonthHelper { // .

    public String getMonthName(int index){

        String result = "";

        switch (index){
            case 1:
                result = "Январь";
                break;
            case 2:
                result = "Февраль";
                break;
            case 3:
                result = "Март";
                break;
            case 4:
                result = "Апрель";
                break;
            case 5:
                result = "Май";
                break;
            case 6:
                result = "Июнь";
                break;
            case 7:
                result = "Июль";
                break;
            case 8:
                result = "Август";
                break;
            case 9:
                result = "Сентябрь";
                break;
            case 10:
                result = "Октябрь";
                break;
            case 11:
                result = "Ноябрь";
                break;
            case 12:
                result = "Декабрь";
                break;
            default: result = "Ошибка.";

        } return  result;
    }
}






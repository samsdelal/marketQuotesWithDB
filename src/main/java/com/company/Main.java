package com.company;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        while (true){
            System.out.println("Введите даты в формате dd/MM/YY.");
            Scanner scanner = new Scanner(System.in);
            String date = scanner.nextLine();
            System.out.println("Введите индификатор валюты например 'USD'");
            Scanner CharCode = new Scanner(System.in);
            String charcode = CharCode.nextLine();
            GetCurrency currency = new GetCurrency();
            System.out.println(currency.getCurrency(date, charcode));
            System.out.println("ЧТОБЫ ВЫЙТИ ИЗ ПРОГРАММЫ ВВЕДИТЕ 'exit', для продолжения нажмите 'ENTER'");
            Scanner cmd = new Scanner(System.in);
            String command = cmd.nextLine().toLowerCase();
            if (command.equals("exit")){
                break;
            }
            System.out.println("===============================");
        }


    }
}

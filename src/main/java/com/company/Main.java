package com.company;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Objects;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String date = args[0];
        String charcode = args[1];
        if (date.equals("updateDB")){
            GetCurrency action = new GetCurrency();
            action.saveAllInfo(charcode);
        }
        else if (!date.equals("updateDB")){
            GetCurrency currency = new GetCurrency();
            System.out.println(currency.getCurrency(date, charcode));
        } else{
            System.out.println("== КРИТИЧЕСКАЯ ОШИБКА! ==");
        }
    }
}

package com.company;

import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

public class GetCurrency {
    public static String getCurrency(String date, String CharCode) throws ParserConfigurationException, IOException, SAXException {
        String value = null;
        var allInfo = getAllInfo(date);
        var isValidChar = checkCurrency(CharCode, allInfo);
        var isValidDate = checkDate(date);
        if (isValidChar & date != "" & CharCode != "") {
            for (HashMap<String, String> i : allInfo) {
                if (i.get("CharCode").toLowerCase().equals(CharCode.toLowerCase())) {
                    Formatter message = new Formatter();
                    message.format("Название валюты - %s\nИдентификатор валюты - %s\nКурс - %s", i.get("Name"), i.get("CharCode"), i.get("Value"));
                    value = message.toString();
                }
            }
        }else if(!isValidDate){value = "Неправильно введена дата, пример правилььного ввода - '11/12/2001' (dd/MM/YYYY)";}
        else if(date == ""){value = "Вы не ввели дату!";}
        else if(CharCode == ""){value = "Вы не ввели индификатор валюты";}
        else {value =  "Неверно введен индификатор валюты, правильный пример ввода - 'USD'";}
        return value;
    }

    private static ArrayList<HashMap<String, String>> getAllInfo(String date) throws ParserConfigurationException, IOException, SAXException {

        Formatter request = new Formatter();
        request.format("https://www.cbr.ru/scripts/XML_daily.asp?date_req=%s", date);
        String currency = Unirest.get(request.toString()).asString().getBody();
        HashMap<String, String> valCode = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(currency)));
        document.getDocumentElement().normalize();
        NodeList valInfo = document.getElementsByTagName("Valute");
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for (int i = 0; i < valInfo.getLength(); i++) {
            Node nNode = valInfo.item(i);
            Element valElement = (Element) nNode;
            String charCode = valElement.getElementsByTagName("CharCode").item(0).getTextContent();
            String value = valElement.getElementsByTagName("Value").item(0).getTextContent();
            String name = valElement.getElementsByTagName("Name").item(0).getTextContent();
            HashMap<String, String> map = new HashMap<>();
            map.put("CharCode", charCode);
            map.put("Value", value);
            map.put("Name", name);
            data.add(map);

        }
        return data;

    }


    private static boolean checkCurrency(String valueIndification, ArrayList<HashMap<String, String>> data) {
        ArrayList<String> charcode = new ArrayList<>();
        for (HashMap<String, String> i : data) {
            charcode.add(i.get("CharCode").toLowerCase());
        }

        if (charcode.contains(valueIndification.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
    private static boolean checkDate(String date){
        {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.setLenient(false);
                df.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}

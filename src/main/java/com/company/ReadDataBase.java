package com.company;

import java.sql.*;
import java.util.Formatter;
import java.util.HashMap;

public class ReadDataBase {
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    //    public void connectionToDatabase() throws SQLException {
//        String query = "select * from towers where cellid = 2241 and lac = 45005";
//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://0.tcp.ngrok.io:15791/cell", "ro1", "a2188e43a3688dffcba3c958b0bcc416");
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery(query);
//            while (rs.next()){
//                System.out.printf("lat - %s, lon - %s\n", rs.getString(8), rs.getString(9));
//                //System.out.println(rs.getString(8));
//
//            }
//
//        }catch (SQLException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
//
//    }
    public HashMap<String, String> connectionToDatabase(String date, String charcode) throws SQLException {
        Formatter query = new Formatter();
        query.format("select * from valute where charcode = %s and date = %s", charcode, date);
        HashMap<String, String> value = new HashMap<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/value", "root", "root");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(String.valueOf(query));
            while (rs.next()){
                value.put("lat", String.valueOf(Double.valueOf(rs.getString(3))));
                return value;
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return value;
    }
    public void insertData(String date, String CharCode, String value){
        Formatter query = new Formatter();
        String sql = "INSERT INTO valute (dat, charcode, value) VALUES (?, ?,?)";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/value", "root", "root");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, CharCode);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();


        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    public String returnValue(String date, String CharCode){
        String sql = "SELECT * FROM valute WHERE charcode = ? AND dat = ?";
        String value = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/value", "root", "root");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, CharCode);
            preparedStatement.setString(2, date);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                value = rs.getString("value");
            }
            conn.close();



        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return value;
    }

}
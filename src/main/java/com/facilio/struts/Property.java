package com.facilio.struts;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Property {
    public static void main(String args[]) throws IOException {
        Properties p = new Properties();
        OutputStream os = new FileOutputStream("db.properties");
        p.setProperty("url", "jdbc:mysql://localhost:3306/user");
        p.setProperty("uname", "root");
        p.setProperty("password", "Jadseb@1329");
        p.store(os, null);
    }
}

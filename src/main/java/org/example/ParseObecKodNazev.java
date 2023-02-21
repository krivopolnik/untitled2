package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ParseObecKodNazev {

    ParseObecKodNazev() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, SQLException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("20210331_OB_573060_UZSZ.xml"));

        // Vytvoreni objektu XPath a XPathExpression pro vyhledávání knih
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expressionObecKod = xpath.compile("//Obec/Kod");
        XPathExpression expressionObecNazev = xpath.compile("//Obec/Nazev");

        // Provedeni dotazu XPath a získani seznamu prvků
        NodeList nodeListKod = (NodeList) expressionObecKod.evaluate(document, XPathConstants.NODESET);
        NodeList nodeListNazev = (NodeList) expressionObecNazev.evaluate(document, XPathConstants.NODESET);

        // Přidání do pole, kontrola, zda taková hodnota neexistuje
        ArrayList<Integer> arrayObecKod = new ArrayList<>();
        for (int i = 0; i < nodeListKod.getLength(); i++) {
            Node node = nodeListKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayObecKod.contains(value)) {
                arrayObecKod.add(value);
            }
        }
        for (Integer num : arrayObecKod) {
            System.out.println(num);
        }

        ArrayList<String> arrayObecNazev = new ArrayList<>();
        for (int i = 0; i < nodeListNazev.getLength(); i++) {
            Node node = nodeListNazev.item(i);
            String value = node.getTextContent();
            if (!arrayObecNazev.contains(value)) {
                arrayObecNazev.add(value);
            }
        }
        for (String num : arrayObecNazev) {
            System.out.println(num);
        }

        ConnectionSQL connection = new ConnectionSQL();
        Connection con = connection.getConnection();

        // Přidání do DB z array
        for (int i = 0; i < arrayObecKod.size(); i++) {
            int kod = arrayObecKod.get(i);
            String nazev = arrayObecNazev.get(i);
            String command = "INSERT INTO obec (kod, nazev) VALUES ('" + kod + "', '" + nazev + "');";
            System.out.println(command);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(command);
            stmt.close();
        }
        con.close();
    }
}

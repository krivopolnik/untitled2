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

public class ParseCastObceKodNazev {

    ParseCastObceKodNazev() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("20210331_OB_573060_UZSZ.xml"));

        // Vytvoreni objektu XPath a XPathExpression pro vyhledávání knih
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expressionCastObceObecKod = xpath.compile("//CastObce/Obec/Kod");
        XPathExpression expressionCastObceKod = xpath.compile("//CastObce/Kod");
        XPathExpression expressionCastObceNazev = xpath.compile("//CastObce/Nazev");

        // Provedeni dotazu XPath a získani seznamu prvků
        NodeList nodeListCastObceKod = (NodeList) expressionCastObceKod.evaluate(document, XPathConstants.NODESET);
        NodeList nodeListCastObceNazev = (NodeList) expressionCastObceNazev.evaluate(document, XPathConstants.NODESET);
        NodeList nodeListObecKod = (NodeList) expressionCastObceObecKod.evaluate(document, XPathConstants.NODESET);

        ArrayList<Integer> arrayCastObceObecKod = new ArrayList<>();
        for (int i = 0; i < nodeListCastObceKod.getLength(); i++) {
            Node node = nodeListCastObceKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayCastObceObecKod.contains(value)) {
                arrayCastObceObecKod.add(value);
            }
        }
        for (Integer num : arrayCastObceObecKod) {
            System.out.println(num);
        }

        ArrayList<Integer> arrayObecKod = new ArrayList<>();
        for (int i = 0; i < nodeListObecKod.getLength(); i++) {
            Node node = nodeListObecKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayObecKod.contains(value)) {
                arrayObecKod.add(value);
            }
        }
        for (Integer num : arrayObecKod) {
            System.out.println(num);
        }

        ArrayList<String> arrayObecNazev = new ArrayList<>();
        for (int i = 0; i < nodeListCastObceNazev.getLength(); i++) {
            Node node = nodeListCastObceNazev.item(i);
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

        for (int i = 0; i < arrayCastObceObecKod.size(); i++) {
            int KodObceKeKteremu = arrayObecKod.get(0);
            int kod = arrayCastObceObecKod.get(i);
            String nazev = arrayObecNazev.get(i);
            String command = "INSERT INTO castobce (kod, nazev, KodObceKeKteremu) VALUES ('" + kod + "', '" + nazev + "', '" + KodObceKeKteremu + "');";
            System.out.println(command);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(command);
            stmt.close();
        }
        con.close();
    }
}

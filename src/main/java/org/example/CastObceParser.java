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

public class CastObceParser {

    public void extractDataCastbce () throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("20210331_OB_573060_UZSZ.xml"));

        // Vytvoreni objektu XPath a XPathExpression pro vyhledávání knih
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        XPathExpression obecKod = xpath.compile("//CastObce/Obec/Kod");
        XPathExpression castObceKod = xpath.compile("//CastObce/Kod");
        XPathExpression castObceNazev = xpath.compile("//CastObce/Nazev");

        // Provedeni dotazu XPath a získani seznamu prvků
        NodeList nodeCastObceKod = (NodeList) castObceKod.evaluate(document, XPathConstants.NODESET);
        NodeList nodeCastObceNazev = (NodeList) castObceNazev.evaluate(document, XPathConstants.NODESET);
        NodeList nodeObecKod = (NodeList) obecKod.evaluate(document, XPathConstants.NODESET);

        ArrayList<Integer> arrayCastObceKod = new ArrayList<>();
        for (int i = 0; i < nodeCastObceKod.getLength(); i++) {
            Node node = nodeCastObceKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayCastObceKod.contains(value)) {
                arrayCastObceKod.add(value);
            }
        }
        for (Integer num : arrayCastObceKod) {
            System.out.println(num);
        }

        ArrayList<Integer> arrayObecKod = new ArrayList<>();
        for (int i = 0; i < nodeObecKod.getLength(); i++) {
            Node node = nodeObecKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayObecKod.contains(value)) {
                arrayObecKod.add(value);
            }
        }
        for (Integer num : arrayObecKod) {
            System.out.println(num);
        }

        ArrayList<String> arrayCastObceNazev = new ArrayList<>();
        for (int i = 0; i < nodeCastObceNazev.getLength(); i++) {
            Node node = nodeCastObceNazev.item(i);
            String value = node.getTextContent();
            if (!arrayCastObceNazev.contains(value)) {
                arrayCastObceNazev.add(value);
            }
        }
        for (String num : arrayCastObceNazev) {
            System.out.println(num);
        }

        ConnectionSQL connection = new ConnectionSQL();
        Connection con = connection.getConnection();

        for (int i = 0; i < arrayCastObceKod.size(); i++) {
            int KodObceKeKteremu = arrayObecKod.get(0);
            int kod = arrayCastObceKod.get(i);
            String nazev = arrayCastObceNazev.get(i);
            String command = "INSERT INTO castobce (kod, nazev, KodObceKeKteremu) VALUES ('" + kod + "', '" + nazev + "', '" + KodObceKeKteremu + "');";
            System.out.println(command);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(command);
            stmt.close();
        }
        con.close();
    }
}

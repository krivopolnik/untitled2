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
import java.util.ArrayList;

public class ParseCastObceKodNazev {

    ParseCastObceKodNazev() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("20210331_OB_573060_UZSZ.xml"));

        // Создание объектов XPath и XPathExpression для поиска книг
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expressionCastObceObecKod = xpath.compile("//CastObce/Obec/Kod");
        XPathExpression expressionCastObceKod = xpath.compile("//CastObce/Kod");
        XPathExpression expressionCastObceNazev = xpath.compile("//CastObce/Nazev");

        // Выполнение запроса XPath и получение списка элементов
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

        ArrayList<Integer> arrayCastObceKod = new ArrayList<>();
        for (int i = 0; i < nodeListCastObceKod.getLength(); i++) {
            Node node = nodeListCastObceKod.item(i);
            int value = Integer.parseInt(node.getTextContent());
            if (!arrayCastObceKod.contains(value)) {
                arrayCastObceKod.add(value);
            }
        }
        for (Integer num : arrayCastObceKod) {
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

//        for (int i = 0; i < arrayCastObceKod.size(); i++) {
//            int kod = arrayCastObceKod.get(i);
//            int kodObce = arrayCastObceObecKod.get(i);
//            String nazev = arrayObecNazev.get(i);
//            String command = "INSERT INTO castobce (kod, nazev) VALUES ('" + kod + "', '" + nazev + "', '" + kodObce + "');";
//            System.out.println(command);
//            Statement stmt = con.createStatement();
//            stmt.executeUpdate(command);
//            stmt.close();
//        }
//        con.close();
    }
}

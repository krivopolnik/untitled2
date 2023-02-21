package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws IOException, SQLException, SAXException, ParserConfigurationException, XPathExpressionException {

        FileDownloader fileDownloader = new FileDownloader();
        fileDownloader.downloadFile();

        ObecParser obecParser = new ObecParser();
        obecParser.extractDataObec();

        CastObceParser castObceParser = new CastObceParser();
        castObceParser.extractDataCastbce();
    }
}



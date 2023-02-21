package org.example;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileViewer {

    public FileViewer() {
        try {
            // Otevření souboru pro čtení
            FileReader fileReader = new FileReader("20210331_OB_573060_UZSZ.xml");

            // Vytvořte BufferedReader pro snadnější čtení souboru
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            // Přečtěte si soubor řádek po řádku a zobrazte obsah na obrazovce
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // Zavřete BufferedReader a FileReader
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

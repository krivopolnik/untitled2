package org.example;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileViewer {

    public FileViewer() {
        try {
            // Открываем файл для чтения
            FileReader fileReader = new FileReader("20210331_OB_573060_UZSZ.xml");

            // Создаем BufferedReader для более удобного чтения файла
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            // Читаем файл построчно и выводим содержимое на экран
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // Закрываем BufferedReader и FileReader
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

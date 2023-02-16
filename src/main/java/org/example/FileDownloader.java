package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileDownloader {

     FileDownloader() throws IOException {
         String fileUrl = "https://www.smartform.cz/download/kopidlno.xml.zip";
         String localFileName = "kopidlno.xml.zip";

         try {
             URL url = new URL(fileUrl);
             URLConnection conn = url.openConnection();
             InputStream inputStream = conn.getInputStream();

             FileOutputStream outputStream = new FileOutputStream(localFileName);

             byte[] buffer = new byte[4096];
             int len;
             while ((len = inputStream.read(buffer)) != -1) {
                 outputStream.write(buffer, 0, len);
             }

             outputStream.close();
             inputStream.close();

         } catch (Exception e) {
             e.printStackTrace();
         }

         ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(localFileName)));
         ZipEntry entry = zipInputStream.getNextEntry();
         while (entry != null) {
             String fileName = entry.getName();
             File file = new File(fileName);
             FileOutputStream out = new FileOutputStream(file);

             byte[] zipBuffer = new byte[4096];
             int zipLen;
             while ((zipLen = zipInputStream.read(zipBuffer)) > 0) {
                 out.write(zipBuffer, 0, zipLen);
             }

             out.close();
             zipInputStream.closeEntry();
             entry = zipInputStream.getNextEntry();
         }
         zipInputStream.close();
         System.out.println("File downloaded and extracted successfully!");
     }
}


package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Reading_file {
    public static byte[] readFileTXTToBytes(String nameFile, String pathToFile) throws Exception {
        String filePath = pathToFile + nameFile;
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static byte[] readFileXMLToBytes(String fileName) throws IOException, SAXException, ParserConfigurationException {
        File xmlFile = new File(ConstantVariable.PATH_IN + fileName);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        // Получение списка узлов с выражениями
        NodeList expressionList = doc.getElementsByTagName("expression");

        // Преобразование выражений в строку и запись в ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int i = 0; i < expressionList.getLength(); i++) {
            Element expressionElement = (Element) expressionList.item(i);
            String expression = expressionElement.getTextContent().replaceAll("\\s", ""); // Удаление пробелов
            outputStream.write(expression.getBytes());
            if (i < expressionList.getLength() - 1) {
                outputStream.write("\n".getBytes()); // Добавление символа новой строки, если не последний элемент
            }
        }

        // Получение массива байтов из ByteArrayOutputStream
        return outputStream.toByteArray();
    }
}

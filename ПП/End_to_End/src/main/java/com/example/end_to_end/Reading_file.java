package com.example.end_to_end;

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
    public static byte[] readFileToBytes(String nameFile, String pathToFile) throws Exception {
        String filePath = pathToFile + nameFile;
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
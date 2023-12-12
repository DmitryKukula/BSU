package com.example.end_to_end;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Output {
    public static void writeBytesToFile(String fileName, String filePath, byte[] byteArray) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath + fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(byteArray);
        }
    }

    public static byte[] convertBytesForJSON(byte[] byteArray) {
        String answersString = new String(byteArray);
        String[] answersArray = answersString.split("\\n");

        JsonObject resultJson = new JsonObject();
        JsonArray answersJsonArray = new JsonArray();

        for (String answer : answersArray) {
            answersJsonArray.add(answer);
        }

        resultJson.add("answers", answersJsonArray);

        return convertJsonToBytes(resultJson);
    }

    private static byte[] convertJsonToBytes(JsonObject json) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (Writer writer = new OutputStreamWriter(outputStream)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

    public static byte[] convertBytesForXML(byte[] byteArray) {
        String answersString = new String(byteArray);
        String[] answersArray = answersString.split("\\n");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        byte[] bytesXML = new byte[0];

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element answersElement = doc.createElement("Answers");
            doc.appendChild(answersElement);

            for (String answer : answersArray) {
                addAnswer(doc, answersElement, answer);
            }

            bytesXML = convertXmlToBytes(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytesXML;
    }

    private static void addAnswer(Document doc, Element parentElement, String answer) {
        Element answerElement = doc.createElement("Answer");
        Text answerText = doc.createTextNode(answer);
        answerElement.appendChild(answerText);
        parentElement.appendChild(answerElement);
    }

    private static byte[] convertXmlToBytes(Document doc) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (outputStream) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}

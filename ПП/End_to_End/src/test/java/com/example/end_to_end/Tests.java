package com.example.end_to_end;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import javax.script.ScriptException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Nested
    class FilterBytesTests {
        @Test
        void testFilterBytes() {
            byte[] originalBytes = "12 + 34 * (56 - 78)".getBytes();
            byte[] expectedFilteredBytes = "12+34*(56-78)".getBytes();

            byte[] actualFilteredBytes = FilterBytes.filterBytes(originalBytes);

            assertArrayEquals(expectedFilteredBytes, actualFilteredBytes);
        }

        @Test
        void testFilterBytesWithSpaces() {
            byte[] originalBytes = "1 2 + 3 4 * ( 5 6 - 7 8 )".getBytes();
            byte[] expectedFilteredBytes = "12+34*(56-78)".getBytes();

            byte[] actualFilteredBytes = FilterBytes.filterBytes(originalBytes);

            assertArrayEquals(expectedFilteredBytes, actualFilteredBytes);
        }

        @Test
        void testFilterBytesWithTwoConsecutiveArithmeticOperationSymbols() {
            byte[] originalBytes = "12 + * (56 - 78)".getBytes();
            byte[] expectedFilteredBytes = "12*(56-78)".getBytes();

            byte[] actualFilteredBytes = FilterBytes.filterBytes(originalBytes);

            assertArrayEquals(expectedFilteredBytes, actualFilteredBytes);
        }

        @Test
        void testFilterBytesEmptyInput() {
            byte[] originalBytes = new byte[0];
            byte[] expectedFilteredBytes = new byte[0];

            byte[] actualFilteredBytes = FilterBytes.filterBytes(originalBytes);

            assertArrayEquals(expectedFilteredBytes, actualFilteredBytes);
        }
    }

    @Nested
    class Reading_fileTest{
        @Test
        void readFileToBytesTXT() {
            String testFileName = "input.txt";
            try {
                byte[] result = Reading_file.readFileToBytes(testFileName, ConstantVariable.PATH_IN);
                assertNotNull(result);
                assertTrue(result.length > 0);
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        }

        @Test
        void readFileToBytesJSON() {
            String testFileName = "input.json";
            try {
                byte[] result = Reading_file.readFileToBytes(testFileName, ConstantVariable.PATH_IN);
                assertNotNull(result);
                assertTrue(result.length > 0);
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        }
        @Test
        void readFileToBytesXML() {
            String testFileName = "input.xml";
            try {
                byte[] result = Reading_file.readFileToBytes(testFileName, ConstantVariable.PATH_IN);
                assertNotNull(result);
                assertTrue(result.length > 0);
            } catch (Exception e) {
                fail("Exception thrown: " + e.getMessage());
            }
        }
    }

    @Nested
    class ArchiveTest {
        @Test
        void testRecordAndReadArchive() {
            byte[] originalBytes = "Hello, Archive!".getBytes();
            String zipFilePath = "testArchive.zip";
            String entryName = "testEntry";
            String path = "src/test/resources/";

            Archive.recordArchive(originalBytes, path + zipFilePath, entryName);

            byte[] readBytes = Archive.readArchive(entryName, zipFilePath, path);

            assertArrayEquals(originalBytes, readBytes);
        }
    }

    @Nested
    class OutputTest {

        @Test
        void testWriteBytesToFile() throws Exception {
            String fileName = "testOutput.txt";
            String filePath = "src/test/resources/";
            byte[] byteArray = "Hello, Output!".getBytes();
            Output.writeBytesToFile(fileName, filePath, byteArray);
            assertTrue(new File(filePath + fileName).exists());
        }

        @Test
        void testConvertBytesForJSON() {
            byte[] byteArray = "Hello\nWorld\nOutput".getBytes();
            String expectedJsonString = "{\n  \"answers\": [\n    \"Hello\",\n    \"World\",\n    \"Output\"\n  ]\n}";
            byte[] jsonBytes = Output.convertBytesForJSON(byteArray);
            String actualJsonString = new String(jsonBytes);
            assertEquals(expectedJsonString, actualJsonString);
        }

        @Test
        void testConvertBytesForXML() {
            byte[] byteArray = "Answer 1\nAnswer 2\nAnswer 3".getBytes();
            byte[] expectedXmlBytes = "<Answers>\n    <Answer>Answer 1</Answer>\n    <Answer>Answer 2</Answer>\n    <Answer>Answer 3</Answer>\n</Answers>".getBytes();
            byte[] actualXmlBytes = Output.convertBytesForXML(byteArray);
            assertXmlEquals(new String(expectedXmlBytes), new String(actualXmlBytes));
        }
        private void assertXmlEquals(String expectedXml, String actualXml) {
             org.xmlunit.assertj.XmlAssert.assertThat(actualXml).and(expectedXml).areSimilar();
        }
    }

    @Nested
    class MathExpressionEvaluatorTest {

        @Test
        void testLibSolution() throws ScriptException {
            String content = "2 + 3\n5 * 4 - 10";
            byte[] result = MathExpressionEvaluator.calculate(content, true);
            String expectedResult = "5.0\n10.0\n";
            assertEquals(expectedResult, new String(result));
        }

        @Test
        void testStackSolution() throws ScriptException {
            String content = "2 + 3\n5 * 4 - 10";
            byte[] result = MathExpressionEvaluator.calculate(content, false);
            String expectedResult = "5.0\n10.0\n";
            assertEquals(expectedResult, new String(result));
        }

        @Test
        void testLibSolutionWithInvalidExpression() {
            String content = "2 + 3\n5 * 4 - 10 / 0";
            assertThrows(NumberFormatException.class, () -> {
                byte[] result = MathExpressionEvaluator.calculate(content, true);
                // Пытаемся преобразовать результат в число, чтобы выявить деление на ноль
                Double.parseDouble(new String(result).trim());
            });
        }

        @Test
        void testStackSolutionWithInvalidExpression() {
            String content = "2 + 3\n5 * 4 - 10 / 0";
            assertThrows(ArithmeticException.class, () -> MathExpressionEvaluator.calculate(content, false));
        }

        @Test
        void testLib_evaluateMathExpression() throws ScriptException, ScriptException {
            String expression = "2 + 3 * 4";
            String result = MathExpressionEvaluator.lib_evaluateMathExpression(expression);
            assertEquals("14.0", result);
        }

        @Test
        void testStack_evaluateMathExpression() {
            String expression = "2 + 3 * 4";
            String result = MathExpressionEvaluator.stack_evaluateMathExpression(expression);
            assertEquals("14.0", result);
        }
    }

    @Nested
    class Encrypt_file_AESTest {

        @Test
        void testEncryptAndDecrypt() throws Exception {
            String originalText = "Hello, Encryption!";
            byte[] originalBytes = originalText.getBytes();
            byte[] encryptedBytes = Encrypt_file_AES.encrypt(originalBytes);
            byte[] decryptedBytes = Encrypt_file_AES.decrypt(encryptedBytes);

            assertArrayEquals(originalBytes, decryptedBytes);
        }
    }
}
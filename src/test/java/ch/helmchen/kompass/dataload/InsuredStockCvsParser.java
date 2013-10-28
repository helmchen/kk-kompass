/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.dataload;

import ch.helmchen.kompass.geo.entities.Canton;
import ch.helmchen.kompass.geo.entities.Country;
import ch.helmchen.kompass.geo.entities.StructureNumber;
import ch.helmchen.kompass.insurance.entities.FophNumber;
import ch.helmchen.kompass.insurance.entities.InsuredStock;
import ch.helmchen.kompass.primes.entities.OhiPrime;
import ch.helmchen.kompass.primes.entities.OhiType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author helmut
 */
public class InsuredStockCvsParser {

    private Properties config;
    private Map<String, Integer> fieldMapping;
    BufferedReader br = null;
    FileWriter writer = null;
    final String CSV_DELIMITER = ";";
    private int inputRecords = 0;
    private int outputRecords = 0;

    public InsuredStockCvsParser(Properties config, File inputFile, File outputFile) throws IOException {
        this.config = config;
        fieldMapping = new HashMap<String, Integer>();
        br = new BufferedReader(new FileReader(inputFile));
        writer = new FileWriter(outputFile);
    }

    private void processHeaders() throws IOException {
        int pos = 0;
        for (String field : getLine()) {
            String mapping = config.getProperty("Mapping." + field);
            if (mapping != null) {
                fieldMapping.put(mapping, pos);
            }
            pos++;
        }
        List<String> outputFields = new ArrayList<>();
        outputFields.add("fophNumber");
        outputFields.add("version");
        outputFields.add("ohiZone");
        outputFields.add("insuredStock");
        writeLine(outputFields);

    }

    private void processContent() throws IOException {
        List<String> contentLine;
        while ((contentLine = getLine()) != null) {
            try {
                FophNumber fophNumber = FophNumber.valueOf(contentLine.get(fieldIndex("fophNumber")));
                InsuredStock stock = new InsuredStock();
                Country country = Country.SWITZERLAND;
                String cantonCode = contentLine.get(fieldIndex("ohiZoneCanton"));
                if (country.asIso3166().equals(cantonCode)) {
                    // Platzhalter Summe.
                    stock.setStructureNumber(StructureNumber.valueOf(country.asDbKey()));
                } else {
                    Canton canton = Canton.valueOf(cantonCode);
                    stock.setStructureNumber(StructureNumber.valueOf(country, canton));
                }
                stock.setFophNumber(FophNumber.valueOf(contentLine.get(fieldIndex("fophNumber"))));
                stock.setVersion(Integer.parseInt(config.getProperty(
                        "Convert.version." + contentLine.get(fieldIndex("version")))));
                stock.setInsuredStock((int) Math.round(Double.parseDouble(contentLine.get(fieldIndex("insuredStock")))));

                List<String> outputFields = new ArrayList<>();
                outputFields.add(stock.getFophNumber().getValue());
                outputFields.add(String.valueOf(stock.getVersion()));
                outputFields.add(stock.getStructureNumber().getValue());
                outputFields.add(String.valueOf(stock.getInsuredStock()));
                writeLine(outputFields);
            } catch (IllegalArgumentException invalidValue) {
                System.err.println("Nothing creted for " + contentLine + " due to " + invalidValue);
            }

        }
    }

    private int fieldIndex(String fieldName) {
        if (fieldMapping.containsKey(fieldName)) {
            return fieldMapping.get(fieldName);
        }
        throw new IllegalArgumentException("Field with name " + fieldName + " not found in data.");
    }

    private List<String> getLine() throws IOException {
        final String line = br.readLine();
        if (line == null) {
            return null;
        }
        // use comma as separator
        String[] fields = line.split(CSV_DELIMITER);
        // Remove delimiters
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].startsWith("\"")) {
                fields[i] = fields[i].substring(1);
            }
            if (fields[i].endsWith("\"")) {
                fields[i] = fields[i].substring(0, fields[i].length() - 1);
            }
        }
        inputRecords++;
        return Arrays.asList(fields);
    }

    private void writeLine(List<String> fields) throws IOException {
        boolean isFirst = true;

        for (String field : fields) {
            if (isFirst) {
                isFirst = false;
            } else {
                writer.append("\t");
            }
            writer.append("\"");
            writer.append(field);
            writer.append("\"");
        }
        writer.append("\n");
        outputRecords++;
        writer.flush();
    }

    private void cleanup() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished. Read " + inputRecords + " records and wrote " + outputRecords + " records.");
    }

    public static void main(String[] args) throws Exception {
        File inputFile;
        File outputFile;
        Properties conf = new Properties();
        InputStream in = InsuranceProviderPdfParser.class.getResourceAsStream("InsuredStockCvsParserConfig.properties");
        conf.load(in);
        in.close();

        if (args.length >= 2) {
            String strInputFile = args[0];
            inputFile = new File(strInputFile);
            String strOutputFile = args[1];
            outputFile = new File(strOutputFile);
        } else {
            // Dialog zum Oeffnen von Dateien anzeigen
            JFileChooser inputChooser = new JFileChooser();
            FileFilter pdfFiles = new FileNameExtensionFilter("CSV-Dateien", "csv");
            inputChooser.setFileFilter(pdfFiles);
            File defaultInputPath = new File(conf.getProperty("DefaultInputPath")).getAbsoluteFile();
            if (defaultInputPath.exists()) {
                inputChooser.setCurrentDirectory(defaultInputPath);
            }
            final int selectInputFile = inputChooser.showOpenDialog(null);
            if (JFileChooser.APPROVE_OPTION == selectInputFile) {
                inputFile = inputChooser.getSelectedFile();
            } else {
                System.err.println("Cancel required.");
                return;
            }
            // Zu speichernde Datei aus Input ableiten
            String outputFileName = inputFile.getAbsolutePath().replace(".csv", ".tsv");
            File defaultOutputPath = new File(conf.getProperty("DefaultOutputPath")).getAbsoluteFile();
            if (defaultOutputPath.exists()) {
                outputFileName = outputFileName.replace(defaultInputPath.getAbsolutePath(), defaultOutputPath.getAbsolutePath());
            }
            outputFile = new File(outputFileName);
        }
        /*     if (outputFile.exists()) {
         System.err.print("Outputfile already exists.");
         return;
         } */
        InsuredStockCvsParser parser = new InsuredStockCvsParser(conf, inputFile, outputFile);
        parser.processHeaders();
        parser.processContent();
        parser.cleanup();
    }

}

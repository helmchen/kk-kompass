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
public class OhiPrimesCsvParser {

    private Properties config;
    private Map<String, Integer> fieldMapping;
    BufferedReader br = null;
    FileWriter writer = null;
    final String CSV_DELIMITER = ";";
    private int inputRecords = 0;
    private int outputRecords = 0;

    public OhiPrimesCsvParser(Properties config, File inputFile, File outputFile) throws IOException {
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
        outputFields.add("type");
        outputFields.add("tariffName");
        outputFields.add("tariffOrder");
        outputFields.add("ohiZone");
        outputFields.add("ageFrom");
        outputFields.add("ageUntil");
        outputFields.add("withAccident");
        outputFields.add("franchise");
        outputFields.add("prime");
        writeLine(outputFields);

    }

    private void processContent() throws IOException {
        List<String> contentLine;
        while ((contentLine = getLine()) != null) {
            try {
                FophNumber fophNumber = FophNumber.valueOf(contentLine.get(fieldIndex("fophNumber")));
                Country country = Country.fromIso3166(contentLine.get(fieldIndex("ohiZoneCountry")));
                Canton canton = Canton.valueOf(contentLine.get(fieldIndex("ohiZoneCanton")));
                String zone = contentLine.get(fieldIndex("ohiZoneRegion"));
                OhiPrime prime = new OhiPrime();
                prime.setFophNumber(FophNumber.valueOf(contentLine.get(fieldIndex("fophNumber"))));
                prime.setOhiZone(StructureNumber.valueOf(country, canton, zone).getValue());
                String ageFrom = contentLine.get(fieldIndex("ageFrom"));
                prime.setAgeFrom(Integer.parseInt(ageFrom));
                prime.setAgeUntil(Integer.parseInt(config.getProperty("Convert.ageUnil." + ageFrom)));
                prime.setVersion(Integer.parseInt(config.getProperty(
                        "Convert.version." + contentLine.get(fieldIndex("version")))));
                prime.setType(OhiType.fromFophName(contentLine.get(fieldIndex("type"))));
                prime.setTariffName(contentLine.get(fieldIndex("tariffName")));
                prime.setTariffOrder(Integer.parseInt(contentLine.get(fieldIndex("tariffOrder"))));
                prime.setWithAccident(Boolean.parseBoolean(config.getProperty(
                        "Convert.withAccident." + contentLine.get(fieldIndex("withAccident")))));
                prime.setLimited(Boolean.parseBoolean(config.getProperty(
                        "Convert.limited." + contentLine.get(fieldIndex("limited")))));
                prime.setFranchise(Double.parseDouble(contentLine.get(fieldIndex("franchise"))));
                prime.setPrime(Double.parseDouble(contentLine.get(fieldIndex("prime"))));
                List<String> outputFields = new ArrayList<>();
                outputFields.add(prime.getFophNumber().getValue());
                outputFields.add(String.valueOf(prime.getVersion()));
                outputFields.add(prime.getType().name());
                outputFields.add(prime.getTariffName());
                outputFields.add(String.valueOf(prime.getTariffOrder()));
                outputFields.add(prime.getOhiZone());
                outputFields.add(String.valueOf(prime.getAgeFrom()));
                outputFields.add(String.valueOf(prime.getAgeUntil()));
                outputFields.add(String.valueOf(prime.isWithAccident()));
                outputFields.add(String.valueOf(prime.getFranchise()));
                outputFields.add(String.valueOf(prime.getPrime()));
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
        InputStream in = InsuranceProviderPdfParser.class.getResourceAsStream("OhiPrimesCsvParser.properties");
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
        OhiPrimesCsvParser parser = new OhiPrimesCsvParser(conf, inputFile, outputFile);
        parser.processHeaders();
        parser.processContent();
        parser.cleanup();
    }

}

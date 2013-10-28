/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.dataload;

import ch.helmchen.kompass.insurance.entities.ContactUsage;
import ch.helmchen.kompass.insurance.entities.FophNumber;
import ch.helmchen.kompass.insurance.entities.InsuranceProvider;
import ch.helmchen.kompass.insurance.entities.LegalForm;
import ch.helmchen.kompass.insurance.entities.TelecomContact;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

/**
 *
 * @author helmut
 */
public class InsuranceProviderPdfParser {

    private static final String PHONE_MARKER = "Tel.";
    private static final String FAX_MARKER = "Fax";
    private static final String URL_MARKER = "www.";
    private File inputFile;
    private File outputFile;
    private Properties config;

    public InsuranceProviderPdfParser(Properties config, File inputFile, File outputFile) {
        this.config = config;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    private void parsePdf() throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(inputFile);
        PdfReader reader = new PdfReader(is);
        String content = getRelevantPdfContent(reader);
        InsuranceProviders insurances = parseContent(content);
    }

    private String getRelevantPdfContent(final PdfReader reader) throws IOException {
        final StringBuilder result = new StringBuilder();
        final int pages = reader.getNumberOfPages();
        final String notParseBefore = config.getProperty("PageHeaderEnd");
        final String notParseAfter = config.getProperty("PageFooterStart");
        for (int i = 1; i <= pages; i++) {
            TextExtractionStrategy strats = new InsuranceProviderPdfTextStrategy(notParseBefore, notParseAfter);
            result.append(PdfTextExtractor.getTextFromPage(reader, i, strats));
            result.append("\n");
        }
        return result.toString();
    }

    private InsuranceProviders parseContent(String content) {
        InsuranceProviders result = new InsuranceProviders();
        StringTokenizer lineTokenizer = new StringTokenizer(content, "\n");
        while (lineTokenizer.hasMoreTokens()) {
            String insuranceLine = lineTokenizer.nextToken();
            System.out.println("Begin next insurance company: " + insuranceLine);
            StringTokenizer insuranceTokenizer = new StringTokenizer(insuranceLine, "\t");
            if (insuranceTokenizer.countTokens() >= 5) {
                final String officialId = insuranceTokenizer.nextToken().trim();
                if (!FophNumber.isValid(officialId)) {
                    System.out.println("Overreading insurance company " + officialId + ", because it has no valid foph number. ");
                    continue;
                }
                final String onlyTaggeld = insuranceTokenizer.nextToken().trim();
                if ("x".equals(onlyTaggeld)) {
                    System.out.println("Overreading insurance company " + officialId + ", because it has only daily allowance. ");
                    continue;
                }
                InsuranceProvider insuranceProvider = new InsuranceProvider();
                insuranceProvider.setFophNumber(FophNumber.valueOf(officialId));
                final String insuranceName = insuranceTokenizer.nextToken().trim();
                insuranceProvider.setName(fixName(insuranceName));
                final String holding = config.getProperty(insuranceProvider.getFophNumber().getValue() + ".holding");
                if (holding == null) {
                    insuranceProvider.setHoldingId(insuranceProvider.getFophNumber().getValue());
                } else {
                    insuranceProvider.setHoldingId(holding);
                }
                final String contactInformation = insuranceTokenizer.nextToken().trim();
                int phoneNumberPos = contactInformation.indexOf(PHONE_MARKER);
                int faxNumberPos = contactInformation.indexOf(FAX_MARKER);
                int urlPos = contactInformation.indexOf(URL_MARKER);
                if (urlPos < 0) {
                    urlPos = contactInformation.length();
                }
                if (faxNumberPos < 0) {
                    faxNumberPos = urlPos;
                }
                if (phoneNumberPos < 0) {
                    phoneNumberPos = faxNumberPos;
                }
                final String address = contactInformation.substring(0, phoneNumberPos - 1);


                if (faxNumberPos > phoneNumberPos) {
                    final String phone = findPhoneNumber(contactInformation.substring(phoneNumberPos + PHONE_MARKER.length(), faxNumberPos - 1));
                    TelecomContact phoneNumber = new TelecomContact();
                    phoneNumber.setUsage(ContactUsage.WORK);
                    phoneNumber.setCharacteristics("PREF,VOICE");
                    phoneNumber.setValue(phone);
                    insuranceProvider.add(phoneNumber);

                }
                if (urlPos > faxNumberPos) {
                    final String fax = findPhoneNumber(contactInformation.substring(faxNumberPos + FAX_MARKER.length(), urlPos - 1));
                    TelecomContact faxNumber = new TelecomContact();
                    faxNumber.setUsage(ContactUsage.WORK);
                    faxNumber.setCharacteristics("PREF,FAX");
                    faxNumber.setValue(fax);
                    insuranceProvider.add(faxNumber);
                }
                final String url = contactInformation.substring(urlPos, contactInformation.length());
                try {
                    insuranceProvider.setHomePage(new URL("http://" + url));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(InsuranceProviderPdfParser.class.getName()).log(Level.SEVERE, null, ex);
                }
                final String legalStatus = insuranceTokenizer.nextToken();
                final String legalStatusDe = new StringTokenizer(legalStatus).nextToken();
                switch (legalStatusDe) {
                    case "AG":
                        insuranceProvider.setLegalForm(LegalForm.CORPORATION);
                        break;
                    case "Stiftung":
                        insuranceProvider.setLegalForm(LegalForm.FOUNDATION);
                        break;
                    case "Genossenschaft":
                        insuranceProvider.setLegalForm(LegalForm.COOPERATIVE);
                        break;
                    case "Genossen-schaft":
                        insuranceProvider.setLegalForm(LegalForm.COOPERATIVE);
                        break;
                    case "Verein":
                        insuranceProvider.setLegalForm(LegalForm.SOCIETY);
                        break;
                    default:
                        System.out.println("Nicht untersützte Rechtsform: " + legalStatusDe);
                }
                insuranceProvider.setCdColor(config.getProperty(insuranceProvider.getFophNumber().getValue() + ".color"));
                insuranceProvider.setImagePath(config.getProperty(insuranceProvider.getFophNumber().getValue() + ".logo"));

                System.out.println(insuranceProvider);
                result.addInsuranceProvider(insuranceProvider);
            }
        }
        try {
            writeFile(result);
        } catch (JAXBException ex) {
            Logger.getLogger(InsuranceProviderPdfParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private void writeFile(InsuranceProviders insuranceProviders) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(InsuranceProviders.class);
        Configuration config = new Configuration();
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);
        Writer writer = new OutputStreamWriter(System.out);
        XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(insuranceProviders, xmlStreamWriter);
    }

    private String findPhoneNumber(String aString) {
        StringBuilder result = new StringBuilder();
        for (char c : aString.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c);
            } else if (c == ' ') {
                // continue
            } else {
                break;
            }
        }
        return result.toString();
    }

    private String fixName(String aName) {
        // Eliminieren von Dubletten (z.B. bei Mehrsprachigen Firmenbezeichnungen).
        final String firstToken = new StringTokenizer(aName, " ").nextToken();
        int iRepeatedNamePos = aName.indexOf(firstToken, 1);
        if (iRepeatedNamePos > 0) {
            return aName.substring(0, iRepeatedNamePos).trim();
        }
        return aName;
    }

    private void debugPdfContents(PdfReader reader) throws IOException {
        System.out.println("*** named Destinations ***");
        HashMap<String, PdfObject> namedDestinationFromNames = reader.getNamedDestinationFromNames();
        for (String key : namedDestinationFromNames.keySet()) {
            System.out.println(key + ": " + namedDestinationFromNames.get(key));
        }

        System.out.println("*** info ***");
        HashMap<String, String> info = reader.getInfo();
        for (String key : namedDestinationFromNames.keySet()) {
            System.out.println(key + ": " + namedDestinationFromNames.get(key));
        }
        System.out.println("*** catalog ***");
        PdfDictionary catalog = reader.getCatalog();
        for (PdfName key : catalog.getKeys()) {
            System.out.println(key + ": " + catalog.get(key));
        }


        System.out.println("*** page contents ***");
        int pages = reader.getNumberOfPages();
        /*    for (int i = 1; i <= pages; i++) {
         System.out.println("**** page " + i);
         //  String decoded = ; 
         System.out.println(new String(reader.getPageContent(i), "UTF-8"));
         // System.out.println(PdfTextExtractor.getTextFromPage(reader, i));
         } */
        final String notParseBefore = config.getProperty("PageHeaderEnd");
        final String notParseAfter = config.getProperty("PageFooterStart");
        for (int i = 1; i <= pages; i++) {
            System.out.println("**** page " + i);
            TextExtractionStrategy strats = new InsuranceProviderPdfTextStrategy(notParseBefore, notParseAfter);
            System.out.println(PdfTextExtractor.getTextFromPage(reader, i, strats));
        }

    }

    public static void main(String[] args) throws Exception {
        File inputFile;
        File outputFile;
        Properties conf = new Properties();
        InputStream in = InsuranceProviderPdfParser.class.getResourceAsStream("InsuranceCompanyPdfParserConfig.properties");
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
            FileFilter pdfFiles = new FileNameExtensionFilter("PDF-Dateien", "pdf");
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
            // Dialog zum Speichern von Dateien anzeigen
            JFileChooser outputChooser = new JFileChooser();
            FileFilter jsonFiles = new FileNameExtensionFilter("JSON-Dateien", "json");
            File defaultOutputPath = new File(conf.getProperty("DefaultOutputPath")).getAbsoluteFile();
            if (defaultOutputPath.exists()) {
                outputChooser.setCurrentDirectory(defaultOutputPath);
            }
            outputChooser.setFileFilter(jsonFiles);
            outputFile = new File(inputFile.getAbsolutePath().replace(".pdf", ".json"));
            outputChooser.setSelectedFile(outputFile);
            final int selectOutputFile = outputChooser.showSaveDialog(null);
            if (JFileChooser.APPROVE_OPTION == selectOutputFile) {
                outputFile = outputChooser.getSelectedFile();
            } else {
                System.err.println("Cancel required.");
                return;
            }
        }
        if (outputFile.exists()) {
            System.err.print("Outputfile already exists.");
            return;
        }
        InsuranceProviderPdfParser parser = new InsuranceProviderPdfParser(conf, inputFile, outputFile);
        parser.parsePdf();
    }
}

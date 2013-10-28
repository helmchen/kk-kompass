/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author helmut
 */
@WebServlet(name = "DataLoader", urlPatterns = {"/dataLoader"})
public class DataLoader extends HttpServlet {

    private String sourcePath;
    private FileFilter propertyFiles;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>KK Compass DataLoader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DataLoader at " + request.getContextPath() + "</h1>");
            out.println("<p>Processing files from " + sourcePath + "</p>");

            if (hasFiles()) {
                final List<File> files = getDirectoryContent();
                out.println("<form action=\"post\">");
                out.println("  <ol>");
                for (File cntFile : files) {
                    out.println("   <li><input type=\"checkbox\" name=\"file\" value=\"" + 
                            cntFile.getAbsolutePath() + ">" + cntFile.getAbsolutePath() + "<input></li>");
                }
                out.println("  </ol>");
                out.println("<button type=\"submit\">Load</button>");
                out.println("</form>");

            } else {
                out.println("<p class=\"warning\">No files found in sourcePath!</p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>KK Compass DataLoader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DataLoader at " + request.getContextPath() + "</h1>");
            try {
                loadFiles(out);
            } catch (IOException ioex) {
                throw ioex;
            } catch (Exception ex) {
                out.println("Load Insurance Models FAILED! " + ex.toString());
                int i = 0;
                for (StackTraceElement element : ex.getStackTrace()) {
                    out.println(element.toString());
                    if (++i > 2) {
                        out.write("... \n");
                        break;
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void loadFiles(final Writer aWriter) throws IOException, ParseException {
        /*
        final CountryCodeIso3166 switzerland
                = CountryCodeIso3166.getCountry(CountryCodeIso3166.SWITZERLAND_CODE);
        final PoliticalStructure withinSwitzerland
                = gds.buildPoliticalStructure(switzerland);
        final DateFormat df = new SimpleDateFormat("MM.yyyy");
        aWriter.write("*************************************************\n");
        aWriter.write("*** Beginning import                          ***\n");
        final InsuranceModelBuilder builder = new InsuranceModelBuilder();
        final List<File> files = getDirectoryContent();
        for (File cntFile : files) {
            if (builder.load(cntFile)) {
                aWriter.flush();
                aWriter.write("*************************************************\n");
                aWriter.write("*** import file: " + cntFile.getName() + " ***\n");
                final InsuranceModel model = builder.createInsuranceModel();
                aWriter.write("id ..............: " + model.getInternalId() + NEXT_LINE);
                aWriter.write("interner Name ...: " + model.getInternalName() + NEXT_LINE);
                aWriter.write("Vertragsnummer ..: " + model.getContractNumber() + NEXT_LINE);
                aWriter.write("Vertragspartner .: " + model.getContractPartner() + NEXT_LINE);
                // Versicherungsmodell speichern
                iadm.storeInsuranceModel(model);
                // Einzugsgebiet auflösen
                final TradingArea tradingArea = new TradingArea();
                final List<String> definitions = builder.createTradingAreaDefinition();
                Date dateValidFrom = null;
                List<PoliticalStructure> lstCntDefinitions = null;
                final PoliticalStructureSearchRequest findBy
                        = new PoliticalStructureSearchRequest();
                findBy.setDomain(withinSwitzerland);
                aWriter.write("Einzugsgebiet ...: ");
                int counter = 1;
                // Definitionen auflösen.
                for (String cntDef : definitions) {
                    try {
                        lstCntDefinitions = new ArrayList<PoliticalStructure>();
                        dateValidFrom = null;
                        if (cntDef.indexOf("/") > 0) {
                            // Variante mit Datum ([cntDef]/mm.yyyy)
                            final String[] split = cntDef.split("/");
                            cntDef = split[0];
                            dateValidFrom = df.parse(split[1]);
                        }
                        // Variante Postleitzahl
                        final Integer postalCode = Integer.parseInt(cntDef);
                        findBy.setMinimumLevel(PoliticalStructure.AdminLevel.CITY);
                        findBy.setMaximumLevel(PoliticalStructure.AdminLevel.CITY);
                        findBy.setSearchTerm(cntDef);
                        final List<PoliticalStructure> hits = gds.findByNumber(findBy);
                        for (PoliticalStructure cntHit : hits) {
                            // auf Gemeinde konvertieren!
                            final PoliticalStructure parent = gds.getParent(cntHit);
                            if (parent != null) {
                                lstCntDefinitions.add(parent);
                            } else {
                                lstCntDefinitions.add(cntHit);
                            }
                        }
                    } catch (IllegalArgumentException notANum) {
                        // Keine Postleitzahl
                        if (cntDef.indexOf("#") < 0) {
                            // Variante Kantonskürzel
                            findBy.setMinimumLevel(PoliticalStructure.AdminLevel.STATE);
                            findBy.setMaximumLevel(PoliticalStructure.AdminLevel.STATE);
                            findBy.setSearchTerm(cntDef);
                            final List<PoliticalStructure> hits = gds.findByNumber(findBy);
                            for (PoliticalStructure cntHit : hits) {
                                lstCntDefinitions.add(cntHit);
                            }
                        } else {
                            // Variante Strukturstring
                            final PoliticalStructure hit = gds.findByPath(cntDef);
                            lstCntDefinitions.add(hit);
                        }
                    }
                    for (PoliticalStructure structure : lstCntDefinitions) {
                        final MultiLingualText named = toDefinitionText(structure);
                        if ((counter + 20) % 20 == 0) {
                            aWriter.write(NEXT_LINE);
                            aWriter.write("                   ");
                        } else if (counter > 1) {
                            aWriter.write(", ");
                        }
                        try {
                            aWriter.write(named.getGermanText());
                            cascadeAndAdd(structure, false, tradingArea, named, dateValidFrom);
                        } catch (NullPointerException npe) {
                            aWriter.write(NEXT_LINE);
                            aWriter.write("ERROR! " + npe.getLocalizedMessage() + " named is null: "
                                    + structure.getCode() + " / " + structure.getName());
                            aWriter.write(NEXT_LINE);
                        }
                        counter++;
                    }
                }
                aWriter.write(NEXT_LINE);
                aWriter.write("Postleitzahlen ..: ");
                counter = 1;
                for (TradingAreaResEntry defEntry : tradingArea.getResolvingEntries()) {
                    iadm.storeTradingAreaEntry(model, defEntry);
                    if ((counter + 50) % 50 == 0) {
                        aWriter.write(NEXT_LINE);
                        aWriter.write("                   ");
                    } else if (counter > 1) {
                        aWriter.write(", ");
                    }
                    aWriter.write(defEntry.getPostalCode().getPostalCode());
                    counter++;
                }
                for (TradingAreaDefEntry resEntry : tradingArea.getDefinitionEntries()) {
                    iadm.storeTradingAreaEntry(model, resEntry);
                }
                aWriter.write(NEXT_LINE);
            }
        }
                */
    }

    private boolean hasFiles() {
        return (getDirectoryContent().size() > 0);

    }

    private List<File> getDirectoryContent() {
        List<File> result = new ArrayList<File>();
        final File directory = new File(sourcePath);
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles(propertyFiles);
            result = Arrays.asList(files);
        }
        return result;
    }
    
/*
    private void cascadeAndAdd(final PoliticalStructure aStructure, final boolean isChild,
            final TradingArea aTradingArea, final MultiLingualText aMultiLingualText,
            final Date aDateValidFrom) {
        if (!isChild) {
            final TradingAreaDefEntry defEntry = new TradingAreaDefEntry();
            defEntry.setAreaDefinition(aStructure.getPath());
            defEntry.setAreaDefinitionText(aMultiLingualText);
            if (aDateValidFrom != null) {
                defEntry.setValidFrom(aDateValidFrom);
            } else {
                defEntry.setValidFrom(InsuranceModelBuilder.getDefaultValidFrom());
            }
            defEntry.setValidUntil(InsuranceModelBuilder.getDefaultValidUntil());
            aTradingArea.addDefinitionEntry(defEntry);
        }

        if (!aStructure.getLevel().equals(PoliticalStructure.AdminLevel.CITY)) {
            final List<PoliticalStructure> children = gds.getChildren(aStructure);
            for (PoliticalStructure child : children) {
                cascadeAndAdd(child, true, aTradingArea, aMultiLingualText, aDateValidFrom);
            }
        } else {
            final PoliticalStructure community = gds.getParent(aStructure);
            final PoliticalStructure province = gds.getParent(community);
            final PoliticalStructure canton = gds.getParent(province);

            final TradingAreaResEntry resEntry = new TradingAreaResEntry();
            resEntry.setCanton(Canton.findFor(canton.getCode()));
            resEntry.setCommunityCode(community.getCode());
            resEntry.setPostalCode(new PostalCode(aStructure.getCode(), null));
            if (aDateValidFrom != null) {
                resEntry.setValidFrom(aDateValidFrom);
            } else {
                resEntry.setValidFrom(InsuranceModelBuilder.getDefaultValidFrom());
            }
            resEntry.setValidUntil(InsuranceModelBuilder.getDefaultValidUntil());
            //entry.setStructureId(aStructure.getPath());
            aTradingArea.addResolvingEntry(resEntry);

        }
    }
       */
 
}

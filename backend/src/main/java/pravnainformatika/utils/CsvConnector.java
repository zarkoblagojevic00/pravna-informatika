package pravnainformatika.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseBaseFilter;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Connector;
import es.ucm.fdi.gaia.jcolibri.exception.InitializingException;
import pravnainformatika.model.CaseDescription;


public class CsvConnector implements Connector {

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/documents/presude/presude.csv");
            if (inputStream == null)
                throw new Exception("Error opening file");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || (line.length() == 0))
                    continue;
                String[] values = line.split(";");

                CBRCase cbrCase = new CBRCase();

                CaseDescription caseDescription = new CaseDescription();
                caseDescription.setId(Integer.parseInt(values[0]));
                caseDescription.setSud(values[1]);
                caseDescription.setPoslovniBroj(values[2]);
                caseDescription.setSudija(values[3]);
                caseDescription.setTuzilac(values[4]);
                caseDescription.setOkrivljeni(values[5]);
                caseDescription.setKrivicnoDelo(values[6]);
                caseDescription.setVrednost(Double.parseDouble(values[7]));
                caseDescription.setNasilno(values[8]);
                caseDescription.setUmisljaj(values[9]);
                caseDescription.setNepogoda(values[10]);
                caseDescription.setVrstaPresude(values[11]);
                caseDescription.setKazna(Double.parseDouble(values[12]));
                caseDescription.setPrimenjeniPropisi(Arrays.asList(values[13].split(",")));

                cbrCase.setDescription(caseDescription);
                cases.add(cbrCase);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cases;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter caseBaseFilter) {
        return null;
    }

    @Override
    public void initFromXMLfile(URL url) throws InitializingException {

    }

    @Override
    public void close() {

    }

    @Override
    public void storeCases(Collection<CBRCase> collection) {
        File f = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "documents", "presude", "presude.csv").toFile();
        try(FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            for (CBRCase cbrCase: collection) {
                CaseDescription caseDescription = (CaseDescription) cbrCase.getDescription();
                String s = "\n" + caseDescription.getId() + ";"
                        + caseDescription.getSud() + ";"
                        + caseDescription.getPoslovniBroj() + ";"
                        + caseDescription.getSudija() + ";"
                        + caseDescription.getTuzilac() + ";"
                        + caseDescription.getOkrivljeni() + ";"
                        + caseDescription.getKrivicnoDelo() + ";"
                        + caseDescription.getVrednost() + ";"
                        + caseDescription.getNasilno() + ";"
                        + caseDescription.getUmisljaj() + ";"
                        + caseDescription.getNepogoda() + ";"
                        + caseDescription.getVrstaPresude() + ";"
                        + caseDescription.getKazna() + ";"
                        + String.join(",", caseDescription.getPrimenjeniPropisi());
                out.print(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCases(Collection<CBRCase> collection) {

    }
}

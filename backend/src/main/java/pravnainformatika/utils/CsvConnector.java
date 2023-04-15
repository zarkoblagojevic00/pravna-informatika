package pravnainformatika.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
            InputStream inputStream = getClass().getResourceAsStream("/presude.csv");
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

    }

    @Override
    public void deleteCases(Collection<CBRCase> collection) {

    }
}

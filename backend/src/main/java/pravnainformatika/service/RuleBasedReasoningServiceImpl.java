package pravnainformatika.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import pravnainformatika.model.CaseDescription;
import pravnainformatika.service.interfaces.RuleBasedReasoningService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class RuleBasedReasoningServiceImpl implements RuleBasedReasoningService {

    private final Path drDevicePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "dr-device-files");

    @Override
    public String start(CaseDescription caseDescription) {
        writeFacts(caseDescription);
        Path scriptPath = Paths.get(drDevicePath.toString(), "start.bat");
        runScriptFromDirectory(scriptPath, drDevicePath);
        return translate(readExport());
    }

    @Override
    public void clean() {
        Path scriptPath = Paths.get(drDevicePath.toString(), "clean.bat");
        runScriptFromDirectory(scriptPath, drDevicePath);
    }

    public static void runScriptFromDirectory(Path scriptPath, Path directoryPath) {
        ProcessBuilder pb = new ProcessBuilder(scriptPath.toString());
        pb.directory(directoryPath.toFile());
        try {
            runProcess(pb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runProcess(ProcessBuilder pb) throws IOException {
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private void writeFacts(CaseDescription caseDescription) {
        File f = Paths.get(drDevicePath.toString(), "facts.rdf").toFile();
        try(FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
                    "        xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
                    "        xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
                    "        xmlns:lc=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#\">\n" +
                    "    <lc:case rdf:about=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#case01\">\n" +
                    "        <lc:name>case 01</lc:name>\n" +
                    "        <lc:defendant>" + caseDescription.getOkrivljeni() + "</lc:defendant>\n" +
                    "        <lc:value rdf:datatype=\"http://www.w3.org/2001/XMLSchema#decimal\">" + caseDescription.getVrednost() + "</lc:value>\n" +
                    "        <lc:violent>" + transform(caseDescription.getNasilno()) + "</lc:violent>\n" +
                    "        <lc:premeditation>" + transform(caseDescription.getUmisljaj()) + "</lc:premeditation>\n" +
                    "        <lc:disaster>" + transform(caseDescription.getNepogoda()) + "</lc:disaster>\n" +
                    "    </lc:case>\n" +
                    "</rdf:RDF>";
            out.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String transform(String s) {
        return s.equals("da") ? "yes" : "no";
    }

    private String translate(String stringToTranslate) {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("is_theft", "Krađa");
        dictionary.put("is_aggravated_theft_lv1", "Teška krađa-prvi stepen");
        dictionary.put("is_aggravated_theft_lv2", "Teška krađa-drugi stepen");
        dictionary.put("min_imprisonment", "Minimalna zatvorska kazna");
        dictionary.put("max_imprisonment", "Maksimalna zatvorska kazna");
        dictionary.put("value", "vrednost");
        dictionary.put("defendant", "okrivljani");
        return  dictionary.entrySet().stream()
                .map(entryToReplace -> (Function<String, String>) s ->
                        s.replace(entryToReplace.getKey(), entryToReplace.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(stringToTranslate);

    }

    private String readExport() {
        StringBuilder ret = new StringBuilder();
        File f = Paths.get(drDevicePath.toString(), "export.rdf").toFile();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(f);
            Node n = document.getChildNodes().item(1);
            NodeList nodeList = n.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeName().contains("export") && node.getTextContent().contains("defeasibly-proven-positive")) {
                    String nodeName = node.getNodeName().split(":")[1];
                    String childNodeName = node.getChildNodes().item(1).getNodeName().split(":")[1];
                    String childNodeText = node.getChildNodes().item(1).getTextContent();
                    ret.append(nodeName).append(": ").append(childNodeName).append("=").append(childNodeText).append(", ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ret.length() < 2)
            return "Nema informacija o primenjenim zakonskim odredbama.";

        ret.setLength(ret.length() - 2);
        return ret.toString();
    }
}

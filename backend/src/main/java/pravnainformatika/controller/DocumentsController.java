package pravnainformatika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequiredArgsConstructor
public class DocumentsController {

    @GetMapping("/documents/judgement")
    ResponseEntity<List<String>> judgements() throws URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("documents/html/presude/");
        File cases = new File(resourceUrl.toURI());
        var files = cases.listFiles();
        List<String> fileNames = Arrays.stream(files)
                .map(File::getName)
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ResponseEntity.ok(fileNames);
    }

    @GetMapping("/documents/judgement/xml/{id}")
    public ResponseEntity<?> judgementXML(@PathVariable String id) throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("documents/presude/" + id + ".xml");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XHTML_XML)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/law/xml")
    public ResponseEntity<?> lawXML() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("documents/zakon.xml");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XHTML_XML)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/judgement/pdf/{id}")
    public ResponseEntity<?> judgementPDF(@PathVariable String id) throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("documents/presude/" + id + ".pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/law/pdf")
    public ResponseEntity<?> lawPDF() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("documents/zakon.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(Path.of(resourceUrl.toURI())));
    }

    @GetMapping("/documents/features")
    public ResponseEntity<?> features() throws IOException, URISyntaxException {
        ClassLoader classLoader = DocumentsController.class.getClassLoader();
        var resourceUrl = classLoader.getResource("documents/presude/presude_extended.csv");

        CSVParser parser = CSVFormat.DEFAULT.withHeader().withDelimiter(';').parse(new FileReader(Path.of(resourceUrl.toURI()).toFile()));

        StringWriter stringWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();

        for (CSVRecord record : parser) {
            String json = mapper.writeValueAsString(record.toMap());
            stringWriter.write(json + "\n");
        }

        String jsonString = stringWriter.toString();

        return ResponseEntity.ok().body(jsonString);
    }





}

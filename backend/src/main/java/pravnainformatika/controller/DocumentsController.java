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



}

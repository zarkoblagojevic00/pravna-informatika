package pravnainformatika.service;

import org.springframework.stereotype.Service;
import pravnainformatika.service.interfaces.RuleBasedReasoningService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RuleBasedReasoningServiceImpl implements RuleBasedReasoningService {

    private final Path drDevicePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "dr-device-files");

    @Override
    public void start() {
        Path scriptPath = Paths.get(drDevicePath.toString(), "start.bat");
        ProcessBuilder pb = new ProcessBuilder(scriptPath.toString());
        pb.directory(drDevicePath.toFile());
        try {
            runProcess(pb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clean() {
        Path scriptPath = Paths.get(drDevicePath.toString(), "clean.bat");
        ProcessBuilder pb = new ProcessBuilder(scriptPath.toString());
        pb.directory(drDevicePath.toFile());
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
}

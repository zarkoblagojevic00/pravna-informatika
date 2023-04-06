package pravnainformatika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pravnainformatika.service.interfaces.CaseBasedReasoningService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cases")
public class CaseBasedReasoningController {

    private final CaseBasedReasoningService ruleBasedReasoningService;

    @GetMapping("/start_reasoning")
    ResponseEntity<String> startReasoning() {
        ruleBasedReasoningService.start();
        return ResponseEntity.ok("Reasoning started");
    }

}

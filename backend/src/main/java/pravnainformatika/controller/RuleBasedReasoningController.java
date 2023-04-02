package pravnainformatika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pravnainformatika.service.interfaces.RuleBasedReasoningService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rules")
public class RuleBasedReasoningController {

    private final RuleBasedReasoningService ruleBasedReasoningService;

    @GetMapping("/start_reasoning")
    ResponseEntity<String> startReasoning() {
        ruleBasedReasoningService.start();
        return ResponseEntity.ok("Reasoning started");
    }

    @GetMapping("/clean")
    ResponseEntity<String> clean() {
        ruleBasedReasoningService.clean();
        return ResponseEntity.ok("Reasoning files cleaned");
    }
}

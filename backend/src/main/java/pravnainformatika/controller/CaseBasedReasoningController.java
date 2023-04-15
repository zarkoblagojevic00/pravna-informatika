package pravnainformatika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pravnainformatika.dto.CaseDTO;
import pravnainformatika.service.interfaces.CaseBasedReasoningService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cases")
public class CaseBasedReasoningController {

    private final CaseBasedReasoningService ruleBasedReasoningService;

    @PostMapping("/start_reasoning")
    ResponseEntity<String> startReasoning(@RequestBody CaseDTO caseDTO) {
        ruleBasedReasoningService.start(caseDTO);
        return ResponseEntity.ok("Reasoning started");
    }

}

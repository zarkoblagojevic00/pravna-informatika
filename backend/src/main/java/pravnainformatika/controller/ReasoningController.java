package pravnainformatika.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pravnainformatika.dto.CaseDTO;
import pravnainformatika.dto.ReasoningResultDTO;
import pravnainformatika.model.CaseDescription;
import pravnainformatika.service.interfaces.CaseBasedReasoningService;
import pravnainformatika.service.interfaces.RuleBasedReasoningService;

@RestController
@RequiredArgsConstructor
public class ReasoningController {

    private final RuleBasedReasoningService ruleBasedReasoningService;
    private final CaseBasedReasoningService caseBasedReasoningService;
    private final ModelMapper modelMapper;

    @GetMapping("rules/clean")
    ResponseEntity<String> clean() {
        ruleBasedReasoningService.clean();
        return ResponseEntity.ok("Reasoning files cleaned");
    }

    @PostMapping("/cases/add")
    ResponseEntity<String> addNewCase(@RequestBody CaseDTO caseDTO) {
        CaseDescription caseDescription = modelMapper.map(caseDTO, CaseDescription.class);
        caseBasedReasoningService.addNewCase(caseDescription);
        return ResponseEntity.ok("New case added");
    }

    @PostMapping("/start_reasoning")
    ResponseEntity<ReasoningResultDTO> startReasoning(@RequestBody CaseDTO caseDTO) {
        ReasoningResultDTO reasoningResultDTO = new ReasoningResultDTO();
        CaseDescription caseDescription = modelMapper.map(caseDTO, CaseDescription.class);
        reasoningResultDTO.setSimilarCases(caseBasedReasoningService.start(caseDescription));
        reasoningResultDTO.setAppliedProvisions(ruleBasedReasoningService.start(caseDescription));
        ruleBasedReasoningService.clean();
        return ResponseEntity.ok(reasoningResultDTO);
    }
}

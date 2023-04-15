package pravnainformatika.service.interfaces;

import pravnainformatika.dto.CaseDTO;

import java.util.List;

public interface CaseBasedReasoningService {
    List<String> start(CaseDTO caseDTO);
}

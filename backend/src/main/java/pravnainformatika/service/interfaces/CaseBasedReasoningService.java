package pravnainformatika.service.interfaces;

import pravnainformatika.model.CaseDescription;

import java.util.List;

public interface CaseBasedReasoningService {
    List<String> start(CaseDescription caseDescription);
}

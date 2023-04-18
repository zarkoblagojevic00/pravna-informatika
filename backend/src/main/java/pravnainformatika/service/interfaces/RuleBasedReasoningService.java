package pravnainformatika.service.interfaces;

import pravnainformatika.model.CaseDescription;

public interface RuleBasedReasoningService {
    String start(CaseDescription caseDescription);

    void clean();
}

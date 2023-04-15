package pravnainformatika.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReasoningResultDTO {

    private String appliedProvisions = "Nema informacija o primenjenim zakonskim odredbama.";
    private List<String> similarCases = new ArrayList<>(List.of("Nema informacija o slicnim slucajevima."));
}

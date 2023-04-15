package pravnainformatika.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseDTO {

    private int id;
    private String sud;
    private String poslovniBroj;
    private String sudija;
    private String tuzilac;
    private String okrivljeni;
    private String krivicnoDelo;
    private double vrednost;
    private String nasilno;
    private String umisljaj;
    private String nepogoda;
    private String vrstaPresude;
    private double kazna;
    private List<String> primenjeniPropisi = new ArrayList<>();
}

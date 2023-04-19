package pravnainformatika.model;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CaseDescription implements CaseComponent {

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

    @Override
    public String toString() {
        return "id=" + id + ", sud=" + sud + ", poslovniBroj=" + poslovniBroj + ", sudija=" + sudija
                + ", tuzilac=" + tuzilac + ", okrivljeni=" + okrivljeni + ", krivicnoDelo=" + krivicnoDelo
                + ", vrednost=" + vrednost + ", nasilno=" + nasilno + ", umisljaj=" + umisljaj
                + ", nepogoda=" + nepogoda + ", vrstaPresude=" + vrstaPresude + ", kazna(trajanje/evra)=" + kazna
                + ", primenjeniPropisi=" + primenjeniPropisi;
    }

    @Override
    public Attribute getIdAttribute() {
        return null;
    }
}

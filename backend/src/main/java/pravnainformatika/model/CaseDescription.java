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
    private List<String> telesnePovrede = new ArrayList<String>();
    private String vrstaPresude;
    private List<String> primenjeniPropisi = new ArrayList<String>();

    @Override
    public String toString() {
        return "CaseDescription [id=" + id + ", sud=" + sud + ", poslovniBroj=" + poslovniBroj + ", sudija=" + sudija
                + ", tuzilac=" + tuzilac + ", okrivljeni=" + okrivljeni + ", krivicnoDelo=" + krivicnoDelo
                + ", telesnePovrede=" + telesnePovrede + ", vrstaPresude=" + vrstaPresude + ", primenjeniPropisi="
                + primenjeniPropisi + "]";
    }

    @Override
    public Attribute getIdAttribute() {
        return null;
    }
}

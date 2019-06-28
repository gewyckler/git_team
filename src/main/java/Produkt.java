import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class Produkt {
    private String nazwa;
    private double cena;
    private int ilosc;
    private boolean czyDostarczono;

    public Produkt(String nazwa, double cena, int ilosc) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    public boolean dostarczono (boolean dostarczono) {
        return this.czyDostarczono = dostarczono;
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                ", ilosc=" + ilosc;
    }
}

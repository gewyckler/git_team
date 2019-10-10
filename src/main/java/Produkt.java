import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class Produkt {
    private String nazwa;
    private double cena;
    private int ilosc;
    private boolean czyDostarczono = false;

    public Produkt(String nazwa, double cena, int ilosc) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    @Override
    public String toString() {
        return "@##@" + nazwa + "@##@" + cena + "@##@" + ilosc + "@##@" + czyDostarczono + "@##@";
    }
}
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

    public void dostarczono() {
        setCzyDostarczono(true);
    }

    public void nieDostarczono() {
        setCzyDostarczono(false);
    }

    @Override
    public String toString() {
        return "\nNazwa: " + nazwa +
                "\nCena: " + cena +
                "\nIlosc: " + ilosc +
                "\nCzyDostarczono: " + czyDostarczono;
    }
}

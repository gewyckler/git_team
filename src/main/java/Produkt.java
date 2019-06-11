import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Produkt {
    private String nazwa;
    private  double cena;
    private int ilosc;
    private boolean czyDostarczono;


    @Override
    public String toString() {
        return "Produkt{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                ", ilosc=" + ilosc +
                ", czyDostarczono=" + czyDostarczono +
                '}';
    }
}

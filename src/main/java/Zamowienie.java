import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Zamowienie {

    private LocalDateTime dataZamowienie;
    private LocalDateTime dataDostarczenia;
    private String numer;
    private Long numerFaktury;
    private Map<String, Produkt> listaProduktowZamawiana = new HashMap<>();
    private boolean czyDostarczoneZamowienie = false;

    public void dostarczono() {
        setCzyDostarczoneZamowienie(true);
    }

    public void ustawyNrFaktury(Long nrFaktury) {
        setNumerFaktury(nrFaktury);
    }




    @Override
    public String toString() {
        return "\nNumer: " + numer +
                "\nDataZamowienie: " + dataZamowienie +
                "\nDataDostarczenia: " + dataDostarczenia +
                "\nNumerFaktury: " + numerFaktury +
                "\nListaProduktowZamawiana: " + listaProduktowZamawiana +
                "\nCzyDostarczone: " + czyDostarczoneZamowienie;
    }
}

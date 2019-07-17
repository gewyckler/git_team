import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Zamowienie {

    private LocalDateTime dataZamowienie;
    private LocalDateTime dataDostarczenia;
    private String numer;
    private Long numerFaktury;
    private Map<String, Produkt> listaProduktowZamawiana = new HashMap<>();
    private boolean czyDostarczone = false;

    public void dostarczono() {
        setCzyDostarczone(true);
    }

    public void ustawyNrFaktury(Long nrFaktury) {
        setNumerFaktury(nrFaktury);
    }
}

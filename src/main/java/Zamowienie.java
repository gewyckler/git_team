import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Zamowienie {
    //    private List <Produkt> listaProduktowZamawiana = new ArrayList<Produkt>();
    private LocalDateTime dataZamowienie;
    private LocalDateTime dataDostarczenia;
    private String numer;
    private Long numerFaktury;
    private Map<String, Produkt> listaProduktowZamawiana = new HashMap<>();

}

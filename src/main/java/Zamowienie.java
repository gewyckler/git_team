import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Zamowienie {
    private String numer;
    private List <Produkt> listaProduktow = new ArrayList<Produkt>();
    private String dataZamowienie;
    private String dataDostarczenia;
    private Long numerFaktury;
}

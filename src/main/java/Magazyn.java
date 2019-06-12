import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Magazyn {
    private Map <String, Integer> listaProduktowWMagazynie = new HashMap<>();
    private Map <String,List <Zamowienie>> mapaZamowien  = new HashMap<>();




}

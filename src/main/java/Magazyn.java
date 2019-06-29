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
    private Map<String, Integer> listaProduktowWMagazynie = new HashMap<>();
    private Map<String, Zamowienie> mapaZamowien = new HashMap<>();

    public void zwiekszLiczbeWMagazynie(Produkt produkt) {

        if (produkt.isCzyDostarczono() == true) {

            //jesli mapa nie zawiera klucza o podanej nazwie to nalezy dodac go do mapy wraz z jego zamawiana iloscia.
            if (!listaProduktowWMagazynie.containsKey(produkt.getNazwa())) {

                listaProduktowWMagazynie.put(produkt.getNazwa(), produkt.getIlosc());
            } else if (listaProduktowWMagazynie.containsKey(produkt.getNazwa())) {
                //jesli produnkt o podanej nazwie znajduje sie w juz w magazynie to nalezy do jego obecnej ilosci dodac ta z podanego produktu.

                int liczbaProduktow = listaProduktowWMagazynie.get(listaProduktowWMagazynie.values()) + produkt.getIlosc();

                listaProduktowWMagazynie.put(produkt.getNazwa(),liczbaProduktow);
//                    listaProduktowWMagazynie.entrySet().stream()
//                            .filter(p -> p.getKey().equalsIgnoreCase(produkt.getNazwa()))
//                            .forEach(pr -> {
//                                pr.setValue(pr.getValue() + produkt.getIlosc());
//                            });
            }
        }


    }
}

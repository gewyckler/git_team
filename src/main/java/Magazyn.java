import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

                int iloscProduktu = listaProduktowWMagazynie.entrySet()
                        .stream()
                        .filter(key -> key.getKey().equalsIgnoreCase(produkt.getNazwa()))
                        .mapToInt(value -> value.getValue().intValue() + produkt.getIlosc())
                        .sum();

                listaProduktowWMagazynie.replace(produkt.getNazwa(),produkt.getIlosc(),iloscProduktu);

            }
        }
    }
}

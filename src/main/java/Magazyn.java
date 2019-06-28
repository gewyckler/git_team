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

        for (String liczbaWMagazynie : listaProduktowWMagazynie.keySet()) {
            if  (produkt.isCzyDostarczono() == true) {
                if (listaProduktowWMagazynie.containsKey(produkt.getNazwa())) { //jesli zawiera to powinno zwiekszyc liczbe w magazynie.
                    listaProduktowWMagazynie.values().add(produkt.getIlosc());
                } else {
                    listaProduktowWMagazynie.put(produkt.getNazwa(), produkt.getIlosc()); //jesli nie zawiera to powinno dodac produkt do magazynu i wpisac ile jest tego produktu
                    //w magazynie.
                }
            } else {
                System.out.println("Nie dostarczono produktu.");
            }


        }

    }
}

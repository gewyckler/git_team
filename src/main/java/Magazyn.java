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

                listaProduktowWMagazynie.replace(produkt.getNazwa(), produkt.getIlosc(), iloscProduktu);

            }
        }
    }

    public void zmniejszLiczbeWMagazynie(String nazwaProduktu, int iloscKupna) {
        getListaProduktowWMagazynie().entrySet()
                .stream().filter(p -> p.getKey().equalsIgnoreCase(nazwaProduktu))
                .mapToInt(value -> value.setValue(value.getValue() - iloscKupna))
                .sum();
    }

    public int liczbaDanegoProduktuWMagazynie(String nazwaProduktu) {
        return getListaProduktowWMagazynie().entrySet()
                .stream().filter(p -> p.getKey().equalsIgnoreCase(nazwaProduktu))
                .mapToInt(value -> value.setValue(value.getValue()))
                .sum();
    }

    public void wypiszZawartoscMagazynuSklepu() {
        System.out.println("Stan magazynu w sklepie\n");
        if (listaProduktowWMagazynie.isEmpty()) {
            System.out.println("Magazyn jest pusty.\n");
        } else {
            for (Map.Entry<String, Integer> produkt : listaProduktowWMagazynie.entrySet()) {
                System.out.println("Produkt: " + produkt.getKey() + " Liczba: " + produkt.getValue());
                System.out.println("");
            }
        }
    }

    public void wypiszAktualneZamowienia() {
        System.out.println("Zamówienia pozostające w realizacji:");
        for (Map.Entry<String, Zamowienie> mapaZamowien : mapaZamowien.entrySet()) {
            if (!mapaZamowien.getValue().isCzyDostarczoneZamowienie()) {
                System.out.println("\nNr zamowienie: " + mapaZamowien.getValue().getNumer()
                        + "\nData zamówienia  " + mapaZamowien.getValue().getDataZamowienie()
                        + "\nLista produktów zamawiana " + mapaZamowien.getValue().getListaProduktowZamawiana() + "."
                        + "\nStatus: " + mapaZamowien.getValue().isCzyDostarczoneZamowienie());
            }
        }
    }

    public void wypiszZrealizowaneZamowienia() {
        System.out.println("Zamówienia  zrealizowane:");
        for (Map.Entry<String, Zamowienie> mapaZamowien : mapaZamowien.entrySet()) {
            if (mapaZamowien.getValue().isCzyDostarczoneZamowienie() == true) {
                System.out.println("\nNr zamowienie " + mapaZamowien.getValue().getNumer()
                        + "\nData zamówienia  " + mapaZamowien.getValue().getDataZamowienie()
                        + "\nLista produktów zamawiana" + mapaZamowien.getValue().getListaProduktowZamawiana() + "."
                        + "\nStatus: " + mapaZamowien.getValue().isCzyDostarczoneZamowienie());
            }
        }
    }
}

import javafx.scene.transform.Scale;

import javax.swing.text.html.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Zamowienie zamowienieTest = new Zamowienie();
        Produkt banan = new Produkt("Banan", 2.30, 5);
        Produkt twarog = new Produkt("Twarog", 4.30, 2);

        zamowienieTest.getListaProduktowZamawiana().put("Banan", banan);
        zamowienieTest.getListaProduktowZamawiana().put("Twarog", twarog);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nuer dostawy: ");
        String nrDostawy = scanner.next();

        dodajDostawe(nrDostawy);
    }

    public static void dodajDostawe(String nrDostawy) {
        Scanner scanner = new Scanner(System.in);
        Magazyn magazynSklepu = new Magazyn();

        String takNie;

        if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {

            for (Zamowienie zamowienie : magazynSklepu.getMapaZamowien().get(nrDostawy)) {
                int liczbaProduktow = 0;
                Produkt produkt = (Produkt) zamowienie.getListaProduktowZamawiana().values();
                System.out.println("Zamówienie zawiera " + zamowienie.getListaProduktowZamawiana().size());
                System.out.println("Czy w dostawie znajduje się produkt: " + zamowienie.getListaProduktowZamawiana().values());
                takNie = scanner.next();
                if (takNie.equalsIgnoreCase("tak")) {
                    magazynSklepu.getListaProduktowWMagazynie().put(String.valueOf(zamowienie.getListaProduktowZamawiana().keySet()), produkt.getIlosc());
                } else if (takNie.equalsIgnoreCase("nie")) {
                    zamowienie.getListaProduktowZamawiana().get(produkt).dostarczono(false);
                }
            }
            System.out.println("Zamowienie zrealizowane. Podaj numer faktury: ");
            Long nrFaktury = scanner.nextLong();

            System.out.println("Czy chcesz ustawić datę dostawy ręcznie? tak/nie");
            String czyTak = scanner.next();
            String dataDostawy;

            DateTimeFormatter formaterDaty = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

            if (czyTak.equalsIgnoreCase("tak")) {
                System.out.println("Podaj datę według wzoru -> HH:mm dd-MM-yyyy");
                dataDostawy = scanner.nextLine();
                LocalDateTime czasOdUzytkownika = LocalDateTime.parse(dataDostawy, formaterDaty);


                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.

            } else {

                LocalDateTime localDateTime = LocalDateTime.now();
                localDateTime.format(formaterDaty);
                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.

            }

        } else if (!magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {
            System.out.println("Zamowienie o podanym numerze nie istnieje.");
        } else if (magazynSklepu.getMapaZamowien().isEmpty()) {
            System.out.println("Lista zamówień jest pusta.");
        }

    }
}

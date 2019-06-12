import javafx.scene.transform.Scale;

import javax.swing.text.html.parser.Parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Magazyn magazynSklepu = new Magazyn();
        Zamowienie zamowienieTest = new Zamowienie();
        Produkt banan = new Produkt("Banan", 2.30, 5);
        Produkt twarog = new Produkt("Twarog", 4.30, 2);

        zamowienieTest.getListaProduktowZamawiana().put("Banan", banan);
        zamowienieTest.getListaProduktowZamawiana().put("Twarog", twarog);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer dostawy: ");
        String nrDostawy = "GD789";
//        zamowienieTest.setNumer(nrDostawy);

        dodajDostawe(nrDostawy, magazynSklepu, zamowienieTest);
    }

    public static void dodajDostawe(String nrDostawy, Magazyn magazynSklepu, Zamowienie zamowienie) {

        Scanner scanner = new Scanner(System.in);

        String takNie;

        if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {


            for (Zamowienie zamowienieObecne : magazynSklepu.getMapaZamowien().get(nrDostawy)) {

                Produkt produkt = (Produkt) zamowienieObecne.getListaProduktowZamawiana().values();
                System.out.println("Zamówienie zawiera " + zamowienieObecne.getListaProduktowZamawiana().size());

                System.out.println("Czy w dostawie znajduje się produkt: " + zamowienieObecne.getListaProduktowZamawiana().values());
                takNie = scanner.next();

                if (takNie.equalsIgnoreCase("tak")) {
                    magazynSklepu.getListaProduktowWMagazynie().put(String.valueOf(zamowienieObecne.getListaProduktowZamawiana().keySet()), produkt.getIlosc());
                } else if (takNie.equalsIgnoreCase("nie")) {
                    zamowienieObecne.getListaProduktowZamawiana().get(produkt).dostarczono(false);
                }
            }
            System.out.println("Zamowienie zrealizowane. Podaj numer faktury: ");
            Long nrFaktury = scanner.nextLong();
            zamowienie.setNumerFaktury(nrFaktury);

            System.out.println("Czy chcesz ustawić datę dostawy ręcznie? tak/nie");
            String czyTak = scanner.next();
            String dataDostawy;

            DateTimeFormatter formaterDaty = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

            if (czyTak.equalsIgnoreCase("tak")) {
                System.out.println("Podaj datę według wzoru -> HH:mm dd-MM-yyyy");
                dataDostawy = scanner.nextLine();
                LocalDateTime czasOdUzytkownika = LocalDateTime.parse(dataDostawy, formaterDaty);
                boolean czyNaCzas;
                if (zamowienie.getDataZamowienie() 1) {

                }

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

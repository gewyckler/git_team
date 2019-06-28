import javafx.scene.transform.Scale;

import javax.swing.text.html.parser.Parser;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Magazyn magazynSklepu = new Magazyn();
        Zamowienie zamowienieTest = new Zamowienie();
        Zamowienie zamowienieTest2 = new Zamowienie();
//PRZYKLADOWE PRODUKTY
        Produkt banan = new Produkt("Banan", 2.30, 5);
        Produkt twarog = new Produkt("Twarog", 4.30, 2);
        Produkt ananas = new Produkt("Ananas", 6.00, 1);
        Produkt jajo = new Produkt("Jajo", 6.00, 1);
//DODANIE DO PRZYKLADOWE ZAMOWIENIE
        zamowienieTest.getListaProduktowZamawiana().put("Banan", banan);
        zamowienieTest.getListaProduktowZamawiana().put("Twarog", twarog);
        zamowienieTest.setDataZamowienie(LocalDateTime.now());
        zamowienieTest.setNumer("GD789");

        zamowienieTest2.getListaProduktowZamawiana().put("Ananas", ananas);
        zamowienieTest2.getListaProduktowZamawiana().put("Jajo", jajo);
        zamowienieTest2.setDataZamowienie(LocalDateTime.now());
        zamowienieTest2.setNumer("GD123");

        magazynSklepu.getMapaZamowien().put("GD789", zamowienieTest);
        magazynSklepu.getMapaZamowien().put("GD123", zamowienieTest2);
        System.out.println("Nr dostawy (np:XY123): ");
        String nrDostawy = scanner.next();

        Zamowienie zamowienie = magazynSklepu.getMapaZamowien().get(nrDostawy);
        dodajDostawe(nrDostawy, magazynSklepu);
    }

    public static void dodajDostawe(String nrDostawy, Magazyn magazynSklepu) {

        Scanner scanner = new Scanner(System.in);

        String takNie;

        Zamowienie zamowienie = magazynSklepu.getMapaZamowien().get(nrDostawy);
        if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {
            System.out.println("Zamówienie zawiera: ");
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println(produkt.getNazwa() + " w ilości: " + produkt.getIlosc());
            }
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println("Czy w dostawie znajduje się produkt: " + produkt.getNazwa() + " ? tak/nie");

                do {
                    takNie = scanner.next();

                    if (takNie.equalsIgnoreCase("tak")) {               //JESLI W ZAMOWNIENIU ZNAJDUJE SIE PRODUKT TO ZWIEKSZAMY JEGO LICZBE W MAGAZYNIE SKLEPU
                        magazynSklepu.getListaProduktowWMagazynie().put(String.valueOf(zamowienie.getListaProduktowZamawiana().keySet()), produkt.getIlosc());
                    } else if (takNie.equalsIgnoreCase("nie")) {        //JESLI W ZAMOWNIENIU NIE MA PRODUKTU ZAMOWIONEGO TO OZNACZAMY JAKO NIEDOSTARCZONY
                        produkt.setCzyDostarczono(false);
                        //                        zamowienie.getListaProduktowZamawiana().get(produkt).setCzyDostarczono(false);
                    } else {
                        System.out.println("Wpisz \"tak\" lub \"nie\"");
                    }
                } while (takNie == "tak" || takNie == "nie");

            }

            System.out.println("Zamowienie zrealizowane. Podaj numer faktury: ");   //DODANIE NR FAKTURY DO ZAMOWNIE
            zamowienie.setNumerFaktury(scanner.nextLong());

            System.out.println("Czy chcesz ustawić datę dostawy ręcznie? tak/nie"); //CZY CHCESZ WPISAC DATE ZAMOWIENIA RECZNIE
            String czyTak = scanner.next();
            String dataDostawy;
            String godzinaDostawy;
            String dataIGodzinaDostawy;
            DateTimeFormatter formaterDaty = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");


            if (czyTak.equalsIgnoreCase("tak")) {                       //JESLI TAK TO WPISUJE W PODANYM FORMACIE
                System.out.println("Podaj godzine dostawy wedlug wzoru -> HH:mm");
                godzinaDostawy = scanner.next();
                System.out.println("Podaj datę według wzoru -> dd-MM-yyyy");
                dataDostawy = scanner.next();
                dataIGodzinaDostawy = godzinaDostawy + " " + dataDostawy;
                LocalDateTime czasOdUzytkownika = LocalDateTime.parse(dataIGodzinaDostawy, formaterDaty);

                Duration duration = Duration.between(zamowienie.getDataZamowienie(), czasOdUzytkownika);
                DateTimeFormatter formatGodziny = DateTimeFormatter.ofPattern("HH:mm");
                System.out.println(duration.getSeconds());

                if (duration.getSeconds() > 60L) {

                    long roznica = duration.getSeconds() - 60;
                    System.out.println("Zamowienie spoznilo sie o " + roznica);

                } else {

                    System.out.println("Zamowienie dostarczono w ciągu " + duration.getSeconds()+ ". " + "Wymagany czas dostawy to 1 min");
                }
                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.

            } else {                                                                //JESLI NIE TO DATA GENERUJE SIE AUTOMATYCZNIE
                LocalDateTime dataAuto = LocalDateTime.now();
                dataAuto.format(formaterDaty);

                Duration duration = Duration.between(zamowienie.getDataZamowienie(), dataAuto);
                System.out.println(duration.getSeconds());
                if (duration.getSeconds() > 60L || duration.getSeconds() < -60) {

                    long roznica = duration.getSeconds() - 60;
                    System.out.println("Zamowienie spoznilo sie o " + roznica);

                } else {
                    System.out.println("Zamowienie dostarczono w ciągu " + duration.getSeconds()+ ". " + "Wymagany czas dostawy to 1 min");

                }
                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
            }
        } else if (!magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {
            System.out.println("Zamowienie o podanym numerze nie istnieje.");

        } else if (magazynSklepu.getMapaZamowien().isEmpty()) {
            System.out.println("Lista zamówień jest pusta.");
        }
    }
}
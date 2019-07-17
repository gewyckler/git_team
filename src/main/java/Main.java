import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char chose = ' ';
        Magazyn magazyn = new Magazyn();

        Zamowienie zamowienieTest = new Zamowienie();
        Zamowienie zamowienieTest2 = new Zamowienie();
//PRZYKLADOWE PRODUKTY
        Produkt banan = new Produkt("Banan", 2.30, 1);
        Produkt twarog = new Produkt("Twarog", 4.30, 1);
        Produkt ananas = new Produkt("Ananas", 6.00, 1);
        Produkt jajo = new Produkt("Jajo", 6.00, 1);
//DODANIE DO PRZYKLADOWE ZAMOWIENIE
        zamowienieTest.getListaProduktowZamawiana().put(banan.getNazwa(), banan);
        zamowienieTest.getListaProduktowZamawiana().put(twarog.getNazwa(), twarog);
        zamowienieTest.getListaProduktowZamawiana().put(jajo.getNazwa(), jajo);
        zamowienieTest.setDataZamowienie(LocalDateTime.now());
        zamowienieTest.setNumer("GD789");

        zamowienieTest2.getListaProduktowZamawiana().put(ananas.getNazwa(), ananas);
        zamowienieTest2.getListaProduktowZamawiana().put(jajo.getNazwa(), jajo);
        zamowienieTest2.setDataZamowienie(LocalDateTime.now());
        zamowienieTest2.setNumer("GD123");

        magazyn.getMapaZamowien().put("GD789", zamowienieTest);
        magazyn.getMapaZamowien().put("GD123", zamowienieTest2);


        do {
            System.out.println("Witaj w programie przyjmującym i realizującym zamówienia.");
            System.out.println("Wybierz opcję podgając cyfre znajdującą się przy wybranej opcji.");
            System.out.println("1. Dodaj zamówienie.");
            System.out.println("2. Dodaj dostawę (odbierz dostawę).");
            System.out.println("3. Wypisz zawartość magazynu w sklepie.");
            System.out.println("4. Listuj aktualne zamówienia.");
            System.out.println("5. Listuj dostarczone zamowienia.");
            System.out.println("6. Zapisz (informację o stanach magazynowych, zamówieniach, produktach).");
            System.out.println("7. Wczytaj (zapisane informację o stanach magazynowych, zamówieniach, produktach).");
            System.out.println("8. Sprzedaż.");
            System.out.println("9. Wyjście.");
            chose = scanner.nextLine().charAt(0);
            switch (chose) {
                case '1': //dodaj zmowienie
                    break;
                case '2': //dadaj doastawe
                    System.out.println("Podaj numer dostawy (np. XY123).");
                    String nrDostawy = scanner.nextLine();
                    dodajDostawe(nrDostawy, magazyn);
                    break;
                case '3': // wypisz zawartosc magazynu
                    wypiszZawartoscMagazynuSklepu(magazyn);
                    czekajNaKlikniecie(scanner);
                    break;
                case '4': // listuj aktualne zamowienia (te niedostarczone)
                    wypiszAktualneZamowienia(magazyn);
                    czekajNaKlikniecie(scanner);
                    break;
                case '5': // listuj dostarczone zamowienia
                    wypiszZrealizowaneZamowienia(magazyn);
                    czekajNaKlikniecie(scanner);
                    break;
                case '6': // zapisz do pliku
                    break;
                case '7': // wczytaj z pliku
                    break;
                case '8': //sprzedaz
                    break;
                case '9': //wyjscie
                    System.out.println("Aplikajca została wyłączona.");
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz ponownie.");
                    break;
            }
        } while (chose != '9');
    }

    public static void wypiszZawartoscMagazynuSklepu(Magazyn magazynSklepu) {
        System.out.println("Stan magazynu w sklepie\n");
        if (magazynSklepu.getListaProduktowWMagazynie().isEmpty()) {
            System.out.println("Magazyn jest pusty.\n");
        } else {
            for (Map.Entry<String, Integer> produkt : magazynSklepu.getListaProduktowWMagazynie().entrySet()) {
                System.out.println("Produkt: " + produkt.getKey() + " Liczba: " + produkt.getValue());
                System.out.println("");
            }
        }
    }

    public static void wypiszAktualneZamowienia(Magazyn magazynSklepu) {
        System.out.println("Zamówienia pozostające w realizacji:");
        for (Map.Entry<String, Zamowienie> mapaZamowien : magazynSklepu.getMapaZamowien().entrySet()) {
            if (!mapaZamowien.getValue().isCzyDostarczone()) {
                System.out.println("Nr zamowienie " + mapaZamowien.getValue().getNumer()
                        + "\ndata zamówienia  " + mapaZamowien.getValue().getDataZamowienie()
                        + "\nlista produktów zamawiana" + mapaZamowien.getValue().getListaProduktowZamawiana() + ".\n");
            } else {
                System.out.println("Zamowienie zostało zrealizowane");
            }

        }
    }

    public static void wypiszZrealizowaneZamowienia(Magazyn magazynSklepu) {
        System.out.println("Zamówienia  zrealizowane:");
        for (Map.Entry<String, Zamowienie> mapaZamowien : magazynSklepu.getMapaZamowien().entrySet()) {
            if (mapaZamowien.getValue().isCzyDostarczone() == true) {
                System.out.println("Nr zamowienie " + mapaZamowien.getValue().getNumer()
                        + "\ndata zamówienia  " + mapaZamowien.getValue().getDataZamowienie()
                        + "\nlista produktów zamawiana" + mapaZamowien.getValue().getListaProduktowZamawiana() + ".\n");
            }
        }
    }

    public static void dodajDostawe(String nrDostawy, Magazyn magazynSklepu) {
        Scanner scanner = new Scanner(System.in);

        String chose = "";

        Zamowienie zamowienie = magazynSklepu.getMapaZamowien().get(nrDostawy);
        if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy) && zamowienie.isCzyDostarczone() == false) {
            System.out.println("Zamówienie zawiera " + zamowienie.getListaProduktowZamawiana().keySet().stream().count() + " produkty.");
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println(produkt.getNazwa() + " w ilości: " + produkt.getIlosc());
            }
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println("Czy w dostawie znajduje się produkt: " + produkt.getNazwa() + ", cena " + produkt.getCena()
                        + ", ilość " + produkt.getIlosc() + "? tak/nie");
                do {
                    chose = takLubNie(scanner);
//                    chose = scanner.nextLine();
                    switch (chose) {
                        //jesli w zamowieniu znajduje sie produkt to zwiększamy jego ilość w magazynie sklepu
                        case "tak":
                            produkt.dostarczono();
                            magazynSklepu.zwiekszLiczbeWMagazynie(produkt);
                            continue;
                        case "nie":
                            //jesli w zamowieniu nie ma produktu zamowionego to oznaczamy jako niedostarczony
                            produkt.nieDostarczono();
                            break;
                        default:
                            System.out.println("Wpisz \"tak\" lub \"nie\".");
                            break;
                    }
                } while /*(chose != "tak");*/  (chose != "tak" || chose != "nie");
            }

//DODANIE NR FAKTURY DO ZAMOWNIE

            do {
                wpiszNrFaktury(scanner, zamowienie);
            } while (false);

//CZY CHCESZ WPISAC DATE ZAMOWIENIA RECZNIE
            System.out.println("Czy chcesz ustawić datę dostawy ręcznie? tak/nie");
            String czyTak = takLubNie(scanner);
            LocalDateTime czasOdUzytkownia;
            DateTimeFormatter formaterDaty = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

//JESLI TAK TO WPISUJE W PODANYM FORMACIE
            if (czyTak.equalsIgnoreCase("tak")) {
                czasOdUzytkownia = manualyGeneratedDate(scanner, formaterDaty);
                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
                checkIfOrderIsLateOrNot(zamowienie, czasOdUzytkownia);
            }
//JESLI NIE TO DATA GENERUJE SIE AUTOMATYCZNIE
            else {
                LocalDateTime dataAuto = autoGeneratedDate(formaterDaty);
                //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
                checkIfOrderIsLateOrNot(zamowienie, dataAuto);
            }
            // po zrealizowaniu zamowienia czyli dostarczeniu do sklepu zamowienie jest oznaczone jako dostarczone
            zamowienie.dostarczono();

        } else if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy) && zamowienie.isCzyDostarczone() == true) {
            System.out.println("Zamówienie o podanym numerze " + zamowienie.getNumer() + " zostało już zrealizowane.");

        } else if (!magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {
            System.out.println("Zamowienie o podanym numerze nie istnieje.");

        } else if (magazynSklepu.getMapaZamowien().isEmpty()) {
            System.out.println("Lista zamówień jest pusta.");
        }


    }

    private static void wpiszNrFaktury(Scanner scanner, Zamowienie zamowienie) {
        System.out.println("Zamowienie zrealizowane. Podaj numer faktury: ");
        Long numerFaktury = parseLong(scanner.nextLine());
        zamowienie.ustawyNrFaktury(numerFaktury);
    }

    private static void checkIfOrderIsLateOrNot(Zamowienie zamowienie, LocalDateTime czasOdUzytkownika) {
        Duration duration = Duration.between(zamowienie.getDataZamowienie(), czasOdUzytkownika);
        System.out.println(duration.getSeconds());

//odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
        if (duration.getSeconds() > 60L) {

            long roznica = duration.getSeconds() - 60;
            System.out.println("Zamowienie spoznilo sie o " + roznica);

        } else {

            System.out.println("Zamowienie dostarczono w ciągu " + duration.getSeconds() + " s. " + "Wymagany czas dostawy to 1 min\n");
        }
    }

    private static LocalDateTime autoGeneratedDate(DateTimeFormatter formaterDaty) {
        LocalDateTime dataAuto = LocalDateTime.now();
        dataAuto.format(formaterDaty);
        return dataAuto;
    }

    private static LocalDateTime manualyGeneratedDate(Scanner scanner, DateTimeFormatter formaterDaty) {
        String godzinaDostawy;
        String dataDostawy;
        String dataIGodzinaDostawy;
        System.out.println("Podaj godzine dostawy wedlug wzoru -> HH:mm");
        godzinaDostawy = scanner.next();
        System.out.println("Podaj datę według wzoru -> dd-MM-yyyy");
        dataDostawy = scanner.next();
        dataIGodzinaDostawy = godzinaDostawy + " " + dataDostawy;
        LocalDateTime czasOdUzytkownika = LocalDateTime.parse(dataIGodzinaDostawy, formaterDaty);
        return czasOdUzytkownika;
    }

    private static String takLubNie(Scanner scanner) {
        String takNie = scanner.nextLine();
        return takNie;
    }

    private static void czekajNaKlikniecie(Scanner scanner) {
        System.out.println("Kliknij Enter aby przejść dalej.");
        scanner.nextLine();
    }
}
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Long.parseLong;

public class ContentLoader {

    private static final Scanner scanner = new Scanner(System.in);

    public void zlozZamowienie(Magazyn magazyn) {
        Zamowienie zamowienie = createZamowienie();
        System.out.println("Podaj liczbe produktów w zamówieniu.");
        int iloscProduktowNaZamowieniu = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < iloscProduktowNaZamowieniu; i++) {
            Produkt produkt = createProduct();

            // Dodanie produktu listy produktów w zamówieniu
            zamowienie.getListaProduktowZamawiana().put(produkt.getNazwa(), produkt);
        }
        magazyn.getMapaZamowien().put(zamowienie.getNumer(), zamowienie);
    }

    private Zamowienie createZamowienie() {
        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setNumer(generujNrZamowienia());
        zamowienie.setDataZamowienie(LocalDateTime.now());
        return zamowienie;
    }

    public Character menuChose() {
        return scanner.nextLine().charAt(0);
    }

    public String podajNrZamowienia() {
        System.out.println("Podaj numer dostawy (np. XY123).");
        return scanner.nextLine();
    }

    private String generujNrZamowienia() {
        StringBuilder numer = new StringBuilder();
        Random rnd = new Random();
        char znak;
        int cyfra;
        for (int i = 0; i < 2; i++) {
            znak = (char) (rnd.nextInt('z' - 'a') + 'a');
            numer.append(znak);
        }
        for (int i = 0; i < 3; i++) {
            cyfra = (int) rnd.nextInt((0) + 9);
            numer.append(cyfra);
        }
        return String.valueOf(numer);
    }

    private Produkt createProduct() {
        System.out.println("Podaj nazwe produktu");
        String nazwaProd = podajNazwe();

        System.out.println("Podaj cene produktu");
        Double cenaProduktu = podajCene();

        System.out.println("Podaj liczbe sztuk produktu");
        Integer iloscProduktow = podajIlosc();

        // Stworzenie produktu na podstawie wpisanych wczesniej iinfromacji
        return new Produkt(nazwaProd, cenaProduktu, iloscProduktow);
    }

    private Integer podajIlosc() {
        return Integer.valueOf(scanner.nextLine());
    }

    private Double podajCene() {
        return Double.parseDouble(scanner.nextLine());
    }

    private String podajNazwe() {
        return scanner.nextLine();
    }

    public void dodajDostawe(String nrDostawy, Magazyn magazynSklepu) {


        Zamowienie zamowienie = magazynSklepu.getMapaZamowien().get(nrDostawy);
        if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy) && !zamowienie.isCzyDostarczoneZamowienie()) {
            System.out.println("Zamówienie zawiera " + zamowienie.getListaProduktowZamawiana().keySet().stream().count() + " produkty.");
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println(produkt.getNazwa() + " w ilości: " + produkt.getIlosc());
            }
            for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                System.out.println("Czy w dostawie znajduje się produkt: " + produkt.getNazwa() + ", cena " + produkt.getCena()
                        + ", ilość " + produkt.getIlosc() + "? tak/nie");

                String chose;
                do {
                    chose = takLubNie();
                    switch (chose) {
                        //jesli w zamowieniu znajduje sie produkt to zwiększamy jego ilość w magazynie sklepu
                        case "tak":
                            produkt.dostarczono();
                            magazynSklepu.zwiekszLiczbeWMagazynie(produkt);
                            break;
                        case "nie":
                            //jesli w zamowieniu nie ma produktu zamowionego to oznaczamy jako niedostarczony
                            produkt.nieDostarczono();
                            break;
                        default:
                            System.out.println("Wpisz \"tak\" lub \"nie\".");
                            break;
                    }
                } while (!chose.equalsIgnoreCase("tak") && !chose.equalsIgnoreCase("nie"));
            }

            wpiszNrFaktury(zamowienie);
            ustawianieDaty(zamowienie);
            zamowienie.dostarczono();

        } else if (magazynSklepu.getMapaZamowien().containsKey(nrDostawy) && zamowienie.isCzyDostarczoneZamowienie() == true) {
            System.out.println("Zamówienie o podanym numerze " + zamowienie.getNumer() + " zostało już zrealizowane.");

        } else if (!magazynSklepu.getMapaZamowien().containsKey(nrDostawy)) {
            System.out.println("Zamowienie o podanym numerze nie istnieje.");

        } else if (magazynSklepu.getMapaZamowien().isEmpty()) {
            System.out.println("Lista zamówień jest pusta.");
        }
    }

    private void ustawianieDaty(Zamowienie zamowienie) {
        //CZY CHCESZ WPISAC DATE ZAMOWIENIA RECZNIE
        System.out.println("Czy chcesz ustawić datę dostawy ręcznie? tak/nie");
        String czyTak = takLubNie();
        LocalDateTime czasOdUzytkownia;
        DateTimeFormatter formaterDaty = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

//JESLI TAK TO WPISUJE W PODANYM FORMACIE
        if (czyTak.equalsIgnoreCase("tak")) {
            czasOdUzytkownia = manualyGeneratedDate(scanner, formaterDaty);
            zamowienie.setDataDostarczenia(czasOdUzytkownia);
            //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
            checkIfOrderIsLateOrNot(zamowienie, czasOdUzytkownia);
        }
//JESLI NIE TO DATA GENERUJE SIE AUTOMATYCZNIE
        else {
            LocalDateTime dataAuto = autoGeneratedDate(formaterDaty);
            zamowienie.setDataDostarczenia(dataAuto);
            //odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
            checkIfOrderIsLateOrNot(zamowienie, dataAuto);
        }
        // po zrealizowaniu zamowienia czyli dostarczeniu do sklepu zamowienie jest oznaczone jako dostarczone
    }

    public void czekajNaKlikniecie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kliknij Enter aby przejść dalej...");
        scanner.nextLine();
    }

    private void wpiszNrFaktury(Zamowienie zamowienie) {
        System.out.println("Zamowienie zrealizowane. Podaj numer faktury: ");
        Long numerFaktury = null;
        do {
            try {
                numerFaktury = parseLong(scanner.nextLine());
                zamowienie.ustawyNrFaktury(numerFaktury);
            } catch (NumberFormatException e) {
                System.err.println("Podano zły format numeru. Tylko cyfry!");
            }
        } while (numerFaktury == null);
    }

    private void checkIfOrderIsLateOrNot(Zamowienie zamowienie, LocalDateTime czasOdUzytkownika) {
        Duration duration = Duration.between(zamowienie.getDataZamowienie(), czasOdUzytkownika);
        System.out.println(duration.getSeconds());

//odjac od daty zamowienia. Jesli powyzej 1 min to spoznione, jesli ponizej to zmiescilo sie w czasie.
        if (duration.getSeconds() > 60L) {

            long roznica = duration.getSeconds() - 60L;
            System.out.println("Zamowienie dostarczono w ciągu: " + duration.getSeconds() + "\nZamowienie spoznilo sie o " + roznica);

        } else {

            System.out.println("Zamowienie dostarczono w ciągu " + duration.getSeconds() + " s. " + "Wymagany czas dostawy to 1 min\n");
        }
    }

    private LocalDateTime autoGeneratedDate(DateTimeFormatter formaterDaty) {
        LocalDateTime dataAuto = LocalDateTime.now();
        dataAuto.format(formaterDaty);
        return dataAuto;
    }

    private LocalDateTime manualyGeneratedDate(Scanner scanner, DateTimeFormatter formaterDaty) {
        String godzinaDostawy;
        String dataDostawy;
        String dataIGodzinaDostawy;
        LocalDateTime czasOdUzytkownika = null;
        do {
            System.out.println("Podaj godzine dostawy wedlug wzoru -> HH:mm");
            godzinaDostawy = scanner.next();
            System.out.println("Podaj datę według wzoru -> dd-MM-yyyy");
            dataDostawy = scanner.next();
            dataIGodzinaDostawy = godzinaDostawy + " " + dataDostawy;
            czasOdUzytkownika = null;
            try {
                czasOdUzytkownika = LocalDateTime.parse(dataIGodzinaDostawy, formaterDaty);
            } catch (DateTimeParseException dtpe) {
                System.err.println("Zły format!");
            }
        } while (czasOdUzytkownika == null);
        return czasOdUzytkownika;
    }

    private String takLubNie() {
        String takNie = scanner.nextLine();
        return takNie;
    }

    public void sprzedaz(Magazyn magazyn) {
        String toDo;
        do {
            String nazwaProduktu = "nic";
            int ilosc = 0;
            try {
                magazyn.wypiszZawartoscMagazynuSklepu();
                nazwaProduktu = coUzytkownikChceKupic();
                ilosc = ileUzytkownikChceKupic();
                if (sprawdzMagazyn(magazyn, nazwaProduktu, ilosc)) {
                    magazyn.zmniejszLiczbeWMagazynie(nazwaProduktu, ilosc);
                    if (ilosc == 0) {
                        magazyn.getListaProduktowWMagazynie().remove(nazwaProduktu);
                    }
                    System.out.println("Sprzedano " + nazwaProduktu + ". Szt. " + ilosc + ".");
                    System.out.println("Pozostało " + magazyn.getListaProduktowWMagazynie().get(nazwaProduktu)
                            + " szt. produktu w magazynie sklepu.");
                } else {
                    System.out.println("Sklep nie posiada tyle produktu: " + nazwaProduktu + ".");

                }
            } catch (NullPointerException e) {
                System.err.println("Sklep nie posiada produktu o nazwie: " + nazwaProduktu);
            }
            System.out.println("Powtórzyć sprzedaż? tak/nie");
            toDo = takLubNie();
        } while (!toDo.equalsIgnoreCase("nie"));
    }

    private boolean sprawdzMagazyn(Magazyn magazyn, String nazwaProduktu, int ilosc) throws NullPointerException {
        if (magazyn.getListaProduktowWMagazynie().containsKey(nazwaProduktu)) {
            if (sprawdzCzyJestTyle(magazyn, ilosc, nazwaProduktu)) {
                return true;
            }
        }
        return false;
    }

    private boolean sprawdzCzyJestTyle(Magazyn magazyn, int ilosc, String nazwa) {
        int iloscWMagazynie = magazyn.liczbaDanegoProduktuWMagazynie(nazwa);
        if (iloscWMagazynie >= ilosc) {
            return true;
        } else {
            return false;
        }
    }

    private String coUzytkownikChceKupic() {
        System.out.println("Wpisz nazwę produktu który chce kupić klient:");
        return scanner.nextLine();
    }

    private int ileUzytkownikChceKupic() {
        System.out.println("Wpisz ile sztuk danego produktu chce kupić klient:");
        return Integer.parseInt(scanner.nextLine());
    }

}

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContentLoader contentLoader = new ContentLoader();
        File file = new File();

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
                    contentLoader.dodajDostawe(nrDostawy, magazyn);
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '3': // wypisz zawartosc magazynu
                    magazyn.wypiszZawartoscMagazynuSklepu();
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '4': // listuj aktualne zamowienia (te niedostarczone)
                    magazyn.wypiszAktualneZamowienia();
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '5': // listuj dostarczone zamowienia
                    magazyn.wypiszZrealizowaneZamowienia();
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '6': // zapisz do pliku
                    file.updateFile(magazyn);
                    file.zapiszDoPliku(magazyn);
                    break;
                case '7': // wczytaj z pliku
                    file.wczytajZPliku(magazyn);
                    contentLoader.czekajNaKlikniecie();
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

    public static void sprzedaj() {
        System.out.println();
    }


}
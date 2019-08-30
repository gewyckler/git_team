import java.util.*;

public class Main {


    public static void main(String[] args) {
        ContentLoader contentLoader = new ContentLoader();
        File file = new File();
        Magazyn magazyn = new Magazyn();
        file.wczytajZPliku(magazyn);

        char chose;
        do {
            System.out.println("Witaj w programie przyjmującym i realizującym zamówienia.");
            System.out.println("Wybierz opcję podgając cyfre znajdującą się przy wybranej opcji.");
            System.out.println("Program automatycznie wczytuje stan magazynu i zamowień z pliku.");
            System.out.println("1. Dodaj zamówienie.");
            System.out.println("2. Dodaj dostawę (odbierz dostawę).");
            System.out.println("3. Wypisz zawartość magazynu w sklepie.");
            System.out.println("4. Listuj aktualne zamówienia.");
            System.out.println("5. Listuj dostarczone zamowienia.");
            System.out.println("6. Sprzedaż.");
            System.out.println("7. Zapisz i wyjdź.");
            chose = contentLoader.menuChose();
            switch (chose) {
                case '1': //dodaj zmowienie

                    contentLoader.zlozZamowienie(magazyn);
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '2': //dadaj doastawe
                    String nrDostawy = contentLoader.podajNrZamowienia();
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
                case '6': //sprzedaz
                    contentLoader.sprzedaz(magazyn);
                    contentLoader.czekajNaKlikniecie();
                    break;
                case '7': //wyjscie
                    file.zapiszDoPliku(magazyn);
                    System.out.println("Aplikajca została wyłączona.");
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz ponownie.");
                    break;
            }
        } while (chose != '7');
    }
}
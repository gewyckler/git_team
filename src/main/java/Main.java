public class Main {

    public static void main(String[] args) {
        ContentLoader contentLoader = new ContentLoader();

        File file = new File();
        Magazyn magazyn = new Magazyn();
        file.loadOrderFromFile(magazyn);
        file.loadMagazinFromFile(magazyn);

        char chose = '.';
        do {
            System.out.println("Witaj w programie przyjmującym i realizującym zamówienia.");
            System.out.println("Wybierz opcję wpisując cyfre od 1 - 7.");
            System.out.println("Program automatycznie wczytuje stan magazynu i zamowień z pliku.\n");
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

                    contentLoader.placeAnOrder(magazyn);
                    contentLoader.waitForUserClick();
                    break;
                case '2': //dadaj doastawe
                    String nrDostawy = contentLoader.typeOrderNumber();
                    contentLoader.pickUpDelivery(nrDostawy, magazyn);
                    contentLoader.waitForUserClick();
                    break;
                case '3': // wypisz zawartosc magazynu
                    magazyn.wypiszZawartoscMagazynuSklepu();
                    contentLoader.waitForUserClick();
                    break;
                case '4': // listuj aktualne zamowienia (te niedostarczone)
                    magazyn.wypiszAktualneZamowienia();
                    contentLoader.waitForUserClick();
                    break;
                case '5': // listuj dostarczone zamowienia
                    magazyn.wypiszZrealizowaneZamowienia();
                    contentLoader.waitForUserClick();
                    break;
                case '6': //sell
                    contentLoader.sell(magazyn);
                    contentLoader.waitForUserClick();
                    break;
                case '7': //wyjscie
                    file.zapiszDoPliku(magazyn);
                    System.out.println("Aplikajca została wyłączona.");
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz ponownie.");
                    contentLoader.waitForUserClick();
                    break;
            }
        } while (chose != '7');
    }
}
import java.time.LocalDateTime;
import java.util.*;

public class Main {
  
    public static String nrZamowienia ( int dlugoscKoduZamowienia)
    {
        char[] generator = new char[6];
        for (int i = 0; i < dlugoscKoduZamowienia; i++) {

            generator[i] = (char) (((int) (Math.random() * 16)) + (int) 'A');
        return (new String(generator, 0, dlugoscKoduZamowienia));
          
        }

        
      public static void main(String[] args) {
      
       // Produkt zamawiany = new Zamowienie();
        // Produkt chleb = Produkt("Chleb", 2.25, 8);


        System.out.println("Podaj liczbe zamówień");
        Scanner scanner3 = new Scanner(System.in);
        int iloscProduktowNaZamowieniu = scanner3.nextInt();


        for (int i = 0; i < iloscProduktowNaZamowieniu; i++) {

            System.out.println("podaj nazwe produktu");
            //Scanner scanner3 = new Scanner(System.in);
            String nazwaProd = scanner3.next();

            System.out.println("podaj cene produktu");
            //  Scanner scanner4 = new Scanner(System.in);
            Double cenaProduktu = scanner3.nextDouble();

            System.out.println("Podaj ilosc zamawanych produktow");
            //  Scanner scanner5 = new Scanner(System.in);
            Integer iloscProduktow = scanner3.nextInt();

            Produkt produktZeskanera = new Produkt(nazwaProd, cenaProduktu, iloscProduktow);


            Map<String, Produkt> zamowienie = new HashMap();
            zamowienie.put(nazwaProd, produktZeskanera);
            // System.out.println(zamowienie);  - dodaje informaje jaka opcje wybralem



        }
      
      
           
      
      
        Scanner scanner = new Scanner(System.in);
        ContentLoader contentLoader = new ContentLoader();
        File file = new File();
        Magazyn magazyn = new Magazyn();
        file.wczytajZPliku(magazyn);


/*
    Od linijki 11 do linijki 33 stworzone są dwa przykładowe zamówienia na potrzeby testwania aplikacji.
    Z racji że na składanie zamówień nie dostałem jeszcze pull requesta na gicie (mam nadzieje ze dobrze pamietałem nazwe).
 */
        char chose;
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
            System.out.println("9. Zapisz i wyjdź.");
            chose = scanner.nextLine().charAt(0);
            switch (chose) {
                case '1': //dodaj zmowienie
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
                    file.zapiszDoPliku(magazyn);
                    break;
                case '7': // wczytaj z pliku
                    file.wczytajZPliku(magazyn);
                    break;
                case '8': //sprzedaz
                    break;
                case '9': //wyjscie
                    file.zapiszDoPliku(magazyn);
                    System.out.println("Aplikajca została wyłączona.");
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz ponownie.");
                    break;
            }
        } while (chose != '9');
    }
}
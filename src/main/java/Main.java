import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static String main(String[] args) {

        // Produkt zamawiany = new Zamowienie();
        // Produkt chleb = Produkt("Chleb", 2.25, 8);


        System.out.println("Podaj liczbe zamówień");
        Scanner scanner2 = new Scanner(System.in);
        int iloscProduktowNaZamowieniu = scanner2.nextInt();


        for (int i = 0; i < iloscProduktowNaZamowieniu; i++) {

            System.out.println("podaj nazwe produktu");
            Scanner scanner3 = new Scanner(System.in);
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

            System.out.println(zamowienie.values());

        }

        public static String nrZamowienia (int dlugoscKoduZamowienia){
            char[] generator = new char[7];
            for (int i = 0; i < dlugoscKoduZamowienia; i++) {
                generator[i] = (char) (((int.Math.random()* 16))+ (int)'A');
                return (new String(generator, 0, dlugoscKoduZamowienia));

            }
            System.out.println(nrZamowienia);
            
            

        }


        // nr zamowienia

    }


}











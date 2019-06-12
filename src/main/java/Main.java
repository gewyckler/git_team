import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

       // Produkt zamawiany = new Zamowienie();
       // Produkt chleb = Produkt("Chleb", 2.25, 8);



            System.out.println("Podaj liczbe zamówień");
            Scanner scanner2 = new Scanner(System.in);
            int iloscProduktowNaZamowieniu = scanner2.nextInt();


            for (int i = 0; i < iloscProduktowNaZamowieniu; ) {

                System.out.println("podaj nazwe produktu");
                Scanner scanner3 = new Scanner(System.in);
                String nazwa = scanner3.next();

                System.out.println("podaj cene produktu");
                Scanner scanner4 = new Scanner(System.in);
                String cenaProduktu = scanner4.next();

                System.out.println("Podaj ilosc zamawanych produktow");
                Scanner scanner5 = new Scanner(System.in);
                String iloscProduktow = scanner5.next();


                i++;

                // nr zamowienia

            }




        }

        public static void dalej () {


    }
}




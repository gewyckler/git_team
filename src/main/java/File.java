import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class File {
    private String PATH = "stan_magazynu.txt";
    private Scanner scanner;

    public void zapiszDoPliku(Magazyn magazyn) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(PATH, false));
            for (Zamowienie zamowienie : magazyn.getMapaZamowien().values()) {
                if (magazyn.getMapaZamowien().containsKey(zamowienie.getNumer())) {

                    printWriter.print(zamowienie.getNumer() + "#%%#" + zamowienie.getDataZamowienie() + "#%%#"
                            + zamowienie.getDataDostarczenia() + "#%%#" + zamowienie.getNumerFaktury() + "#%%#" +
                            zamowienie.isCzyDostarczoneZamowienie() + "#%%#");

                    for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                        printWriter.print(produkt.getNazwa() + "#%%#" + produkt.getCena() + "#%%#" + produkt.getIlosc()
                                + "#%%#" + produkt.isCzyDostarczono() + "#%%#");
                    }
                    printWriter.println("");
                }
                magazyn.getMapaZamowien().put(zamowienie.getNumer(), zamowienie);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void wczytajZPliku(Magazyn magazyn) {
        try {

            scanner = new Scanner(new FileReader(PATH));
            scanner.useDelimiter("#%%#");

            while (scanner.hasNextLine()) {

                // Tworzenie zamówienia z danymi wczytanymi z pliku.
                Zamowienie zamowienie = new Zamowienie();

                String line = scanner.nextLine();
                String[] dane = line.split("#%%#");
                // Zebranie danych z pliku o zamówieniu
                zamowienie.setNumer(dane[0]);

                String dataZamowieniaBase = dane[1];
                zamowienie.setDataZamowienie(LocalDateTime.parse(dataZamowieniaBase));

                try {
                    String dataDostarczeniaBase = dane[2];
                    zamowienie.setDataDostarczenia(LocalDateTime.parse(dataDostarczeniaBase));
                } catch (DateTimeParseException dtpe) {
                    zamowienie.setDataDostarczenia(null);
                }
                try {
                    zamowienie.setNumerFaktury(Long.parseLong(dane[3]));
                } catch (NumberFormatException ime) {
                    zamowienie.setNumerFaktury(null);
                }
                zamowienie.setCzyDostarczoneZamowienie(Boolean.parseBoolean(dane[4]));

                // Tworzenie mapy listy produktów znajdujących się w pojedynczym zamówieniu.
                Map<String, Produkt> listaProduktowZamawiana = new HashMap<>();

                // Zebranie danych o produkcie znajdujacych się na liście produktów zamawianych
                for (int i = 5; i < dane.length; i++) {
                    // Stworzenie z zebranych danych produktu
                    Produkt produkt = new Produkt();

                    produkt.setNazwa(dane[i]);
                    i++;
                    produkt.setCena(Double.parseDouble(dane[i]));
                    i++;
                    produkt.setIlosc(Integer.parseInt(dane[i]));
                    i++;
                    produkt.setCzyDostarczono(Boolean.parseBoolean(dane[i]));

                    // Dodanie produktu do mapy listy produktów zamawianych
                    listaProduktowZamawiana.put(produkt.getNazwa(), produkt);

                    // Dodanie liczby produktów do magazyny w sklepie jesli zamowienie w ktorym sie znajduja jest dostarczone
                    magazyn.zwiekszLiczbeWMagazynie(produkt);
                }
                zamowienie.setListaProduktowZamawiana(listaProduktowZamawiana);

                // Dodanie zamówienia do mapy zamówień w magazynie
                magazyn.getMapaZamowien().put(zamowienie.getNumer(), zamowienie);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

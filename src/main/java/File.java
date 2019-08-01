import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class File {
    private String PATH = "stan_magazynu.txt";
    private Scanner scanner;

    public void zapiszDoPliku(Magazyn magazyn) {
        try {
            List<String> listNr = checkIfFileContainsOrder();

            PrintWriter printWriter = new PrintWriter(new FileWriter(PATH, true));
            for (Zamowienie zamowienie : magazyn.getMapaZamowien().values()) {

                if (!listNr.contains(zamowienie.getNumer() + zamowienie.isCzyDostarczoneZamowienie())) {

                    printWriter.print(zamowienie.getNumer() + "#%%#" + zamowienie.getDataZamowienie() + "#%%#"
                            + zamowienie.getDataDostarczenia() + "#%%#" + zamowienie.getNumerFaktury() + "#%%#" + zamowienie.isCzyDostarczoneZamowienie() + "#%%#");

                    for (Produkt produkt : zamowienie.getListaProduktowZamawiana().values()) {
                        printWriter.print(produkt.getNazwa() + "#%%#" + produkt.getCena() + "#%%#" + produkt.getIlosc() + "#%%#" + produkt.isCzyDostarczono() + "#%%#");
                    }
                    printWriter.println("");
                }
                magazyn.getMapaZamowien().replace(zamowienie.getNumer(), magazyn.getMapaZamowien().get(listNr.get(0)), zamowienie);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> checkIfFileContainsOrder() {
        List<String> listaNr = new ArrayList<>();
        try {
            scanner = new Scanner(new FileReader(PATH));
            String nrZam;
            while (scanner.hasNextLine()) {

                String tab[] = scanner.nextLine().split("#%%#");
                nrZam = tab[0] + tab[4];
                listaNr.add(nrZam);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listaNr;
    }

    public void wczytajZPliku(Magazyn magazyn) {
        try {

            for (Map.Entry<String, Zamowienie> zamowienie : magazyn.getMapaZamowien().entrySet()) {
                magazyn.getMapaZamowien().remove(zamowienie.getKey());
            }

            // Tworzenie zamówienia z danymi wczytanymi z pliku.
            Zamowienie zamowienie = new Zamowienie();

            // Tworzenie mapy listy produktów znajdujących się w pojedynczym zamówieniu.
            Map<String, Produkt> listaProduktowZamawiana = new HashMap<>();

            scanner = new Scanner(new FileReader(PATH));
            scanner.useDelimiter("#%%#");

            while (scanner.hasNextLine()) {
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
                }

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

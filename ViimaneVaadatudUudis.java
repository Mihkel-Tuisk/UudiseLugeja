import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 Klassi eesmärk:     Salvestada ja lugeda viimati vaadatud uudist.
 Tähtsamad Meetodid: getViimaneVaadatudUudis() ja setViimaneVaadatudUudis(String pealkiri, String sisu, String url)
 */
public class ViimaneVaadatudUudis {
    private static final String FAILINIMI = "viimane_uudis.txt";

    /**
     * Loeb viimase vaadatud uudise.
     *
     * @return List, mis hoiab pealkirja, sisu ja URL-i, list on tühi kui ei saa viimast loetud uudist.
     */
    public static List<String> getViimaneVaadatudUudis() {
        List<String> list = new ArrayList<>();
        StringBuilder sisuBuilder = new StringBuilder();
        boolean sisuLugemine = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FAILINIMI))) {
            String pealkiri = reader.readLine();
            if (pealkiri == null) {
                throw new IOException("Pealkiri puudub failis.");
            }

            String line;
            String url = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("---SISU ALGUS---")) {
                    sisuLugemine = true;
                    continue;
                } else if (line.equals("---SISU LÕPP---")) {
                    sisuLugemine = false;
                    url = reader.readLine();
                    break;
                }

                if (sisuLugemine) {
                    sisuBuilder.append(line).append(System.lineSeparator());
                }
            }

            if (url == null) {
                throw new IOException("URL puudub või sisu lõppmärgistus puudub.");
            }

            list.add(pealkiri);
            list.add(sisuBuilder.toString().trim());
            list.add(url);

        } catch (IOException e) {
            System.out.println("Viga uudise lugemisel: " + e.getMessage());
        }

        return list;
    }

    /**
     * Seab viimati vaadatud uudise.
     *
     * @param pealkiri Uudise pealkiri.
     * @param sisu Uudise sisu.
     * @param url Uudise URL, kust on saadud informatsioon.
     */
    public static void setViimaneVaadatudUudis(String pealkiri, String sisu, String url) {
        // Sisul on newline'd, eii saa lugeda seda failist rea kaupa, sest ei tea kui mitu rida on sisu.
        // Sellepärast kirjutame need markerid kus sisu algab ja sisu lõpeb.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAILINIMI))) {
            writer.write(pealkiri);
            writer.newLine();
            writer.write("---SISU ALGUS---");
            writer.newLine();
            writer.write(sisu);
            writer.write("---SISU LÕPP---");
            writer.newLine();
            writer.write(url);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Viga uudise salvestamisel: " + e.getMessage());
        }
    }
}
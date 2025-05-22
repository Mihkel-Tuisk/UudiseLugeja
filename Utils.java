import java.util.Random;

/**
 * Faili Autor: Mihkel Tuisk
*/

/**
    Klassi eesmärk:     Abistada teise klasse, ja vätida koodijuppide kordamist.
    Tähtsamad Meetodid: String lihtsustaHtmlSildid(String tekst) ja String leiaAlaSõne(String sisu, String alguseMärge, String lõpuMärge)
*/
public class Utils {
    private static final Random random = new Random();

    /**
     * Tagastab suvalise arvu vahemikus 0 (kaasa arvatud) ja antud maksimum (kaasa arvatud).
     *
     * @param maksimum Ülemine piir, milleni arve saab genereerida.
     * @return Suvaline arv vahemikus 0 kuni maksimum.
     */
    static int saaSuvalineArv(int maksimum) {
        return random.nextInt(maksimum);
    }

    /**
     * Otsib ja tagastab ala-sõne, mis asub antud sisu sees, mis algab antud alguse-märgist
     * ja lõpeb antud lõpu-märgiga.
     *
     * @param sisu Tekst, millest ala-sõne otsitakse.
     * @param alguseMärge Märge, millest ala-sõne otsing algab.
     * @param lõpuMärge Märge, mille järgi otsing lõpeb.
     * @return Ala-sõne, mis asub alguse ja lõpu märgi vahel, või tühi string, kui ei leita.
     */
    static String leiaAlaSõne(String sisu, String alguseMärge, String lõpuMärge) {
        int algus = sisu.indexOf(alguseMärge);

        if (algus != -1) {

            algus += alguseMärge.length();

            int lõpp = sisu.indexOf(lõpuMärge, algus);

            if (lõpp != -1) {
                return sisu.substring(algus, lõpp);
            }
        }

        return "";
    }

    /**
     * Lihtsustab HTML sildid lihtsustatud tekstiks, eemaldades HTML sildid ja teisendades
     * HTML entiteedid vastavateks erimärkideks.
     *
     * @param tekst Tekst, mis sisaldab HTML silte.
     * @return Tekst, kus HTML sildid on eemaldatud ja on asendatud vastavate sümbolitega.
     */
    static String lihtsustaHtmlSildid(String tekst) {
        tekst = tekst.replaceAll("<a href=\"([^\"]+)\">([^<]+)</a>", "$2 ($1)");
        tekst = tekst.replaceAll("<strong>|<b>", "").replaceAll("</strong>|</b>", "");
        tekst = tekst.replaceAll("<em>", "").replaceAll("</em>", "");
        tekst = tekst.replaceAll("<[^>]+>", "");

        // https://www.w3schools.com/html/html_entities.asp
        tekst = tekst.replaceAll("&nbsp;", " ");
        tekst = tekst.replaceAll("&lt;", "<");
        tekst = tekst.replaceAll("&gt;", ">");
        tekst = tekst.replaceAll("&amp;", "&");
        tekst = tekst.replaceAll("&quot;", "\"");
        tekst = tekst.replaceAll("&apos;", "'");
        tekst = tekst.replaceAll("&cent;", "¢");
        tekst = tekst.replaceAll("&pound;", "£");
        tekst = tekst.replaceAll("&yen;", "¥");
        tekst = tekst.replaceAll("&euro;", "€");
        tekst = tekst.replaceAll("&copy;", "©");
        tekst = tekst.replaceAll("&reg;", "®");
        tekst = tekst.replaceAll("&trade;", "™");

        return tekst;
    }
}
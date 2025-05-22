import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Faili Autor: Mihkel Tuisk
*/

/**
    Klassi eesmärk:     Saada informatsiooni Delfi uudisteportaalist.
    Tähtsamad Meetodid: List<Uudis> leiaKõikUudised() ja void päriUudiseSisu(Uudis)
*/
public class Delfi {
    private String html;
    private final List<String> keelatedUudiseDomeenid = Arrays.asList(
            "omamaitse.delfi.ee", "lemmikloom.delfi.ee",
            "epl.delfi.ee" // 'paywalli' taga
    );

    /**
     * Pärib Delfi pealehekülje HTML sisu.
     *
     * Kui päring ebaõnnestub, siis trükitakse veateade ja HTML jääb tühjaks.
     */
    private void päriHtml() {
        String link = "https://www.delfi.ee/viimased";
        Skraaper skraaper = new Skraaper(link);
        try {
            html = skraaper.getHtml();
        } catch (IOException e) {
            System.out.println("Viga uudiseportaali \"" + link + "\" pealehekülje pärimisel: " + e.getMessage());
            html = "";
        }
    }

    /**
     * Leiab kõik Delfi pealeheküljelt saadaval olevad uudised.
     *
     * @return List<Uudis> Kõik Delfi uudised, kus iga uudis sisaldab linki ja pealkirja.
     */
    public List<Uudis> leiaKõikUudised() {
        päriHtml();

        List<Uudis> uudised = new ArrayList<>();

        String algusHtmlTag = "<article data-";
        String lõppHtmlTag = "</article>";

        int indeks = 0;
        while ((indeks = html.indexOf(algusHtmlTag, indeks)) != -1) {
            int lõpuindeks = html.indexOf(lõppHtmlTag, indeks);
            if (lõpuindeks != -1) {
                String htmlInformatsioon = html.substring(indeks + algusHtmlTag.length(), lõpuindeks);

                String pealkiri = Utils.leiaAlaSõne(htmlInformatsioon, " loading=\"lazy\" alt=\"", "\"></picture>");

                String link = Utils.leiaAlaSõne(htmlInformatsioon, "m-h6\"><a href=\"", "\">");

                if (keelatedUudiseDomeenid.stream().anyMatch(link::contains)) {
                    indeks = lõpuindeks;
                    continue;
                }

                if (!link.contains("https://")) {
                    link = "https://www.delfi.ee" + link;
                }

                // Lisame uue uudise.
                uudised.add(new Uudis(link, Utils.lihtsustaHtmlSildid(pealkiri)));


                indeks = lõpuindeks;
            }
        }

        return uudised;
    }

    /**
     * Pärib konkreetse uudise sisu, et saada kogu uudise täisteksti.
     *
     * @param uudis Uudis objekt, mille sisu tuleb pärida.
     */
    public void päriUudiseSisu(Uudis uudis) {
        // Igat uudist korraga ei ole mõtet pärida, see võtab palju aega ja ka uudiseportaal võib meid "ratelimitida"
        uudis.päriSisu();

        String sisuHtml = uudis.getSisuHTML();

        StringBuilder uudiseSisu = new StringBuilder();
        String algusHtmlTag = "<div class=\"fragment fragment-html fragment-html--paragraph\">";
        String lõppHtmlTag = "</div>";

        int index = 0;
        while ((index = sisuHtml.indexOf(algusHtmlTag, index)) != -1) {
            int endIndex = sisuHtml.indexOf(lõppHtmlTag, index);
            if (endIndex != -1) {
                String htmlInformatsioon = sisuHtml.substring(index + algusHtmlTag.length(), endIndex);

                String algusP = "<p>";
                String lõppP = "</p>";

                int pIndex = 0;
                while ((pIndex = htmlInformatsioon.indexOf(algusP, pIndex)) != -1) {
                    int pEndIndex = htmlInformatsioon.indexOf(lõppP, pIndex);
                    if (pEndIndex != -1) {
                        String pSisu = htmlInformatsioon.substring(pIndex + algusP.length(), pEndIndex);

                        pSisu = Utils.lihtsustaHtmlSildid(pSisu);

                        uudiseSisu.append(pSisu).append("\n");

                        pIndex = pEndIndex + lõppP.length();
                    }
                }

                index = endIndex + lõppHtmlTag.length();
            }
        }

        uudis.setSisu(uudiseSisu.toString());
    }
}
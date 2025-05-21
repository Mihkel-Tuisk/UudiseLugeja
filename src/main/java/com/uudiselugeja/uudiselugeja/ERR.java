package com.uudiselugeja.uudiselugeja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
    Klassi eesmärk:     Saada informatsiooni ERR uudisteportaalist.
    Tähtsamad Meetodid: List<Uudis> leiaKõikUudised() ja void päriUudiseSisu(Uudis)
 */
public class ERR {
    private String html;
    private final List<String> lubatudUudiseDomeenid = Arrays.asList(
            "www.err.ee", "novaator.err.ee", "eeter.err.ee", "kultuur.err.ee"
    );

    /**
     * Pärib ERR pealehekülje HTML sisu.
     *
     * Kui päring ebaõnnestub, siis trükitakse veateade ja HTML jääb tühjaks.
     */
    private void päriHtml() {
        String link = "https://www.err.ee/";
        Skraaper skraaper = new Skraaper(link);
        try {
            html = skraaper.getHtml();
        } catch (IOException e) {
            System.out.println("Viga uudiseportaali \"" + link + "\" pealehekülje pärimisel: " + e.getMessage());
            html = "";
        }
    }

    /**
     * Leiab kõik ERR pealeheküljelt saadaval olevad uudised.
     *
     * @return List<Uudis> Kõik ERR uudised, kus iga uudis sisaldab linki ja pealkirja.
     */
    public List<Uudis> leiaKõikUudised() {
        päriHtml();

        List<Uudis> uudised = new ArrayList<>();

        String algusHtmlTag = "<div class=\"article\"";
        String lõppHtmlTag = "</article>";

        int indeks = 0;
        while ((indeks = html.indexOf(algusHtmlTag, indeks)) != -1) {
            int lõpuIndeks = html.indexOf(lõppHtmlTag, indeks);
            if (lõpuIndeks != -1) {
                String htmlInformatsioon = html.substring(indeks + algusHtmlTag.length(), lõpuIndeks);

                String pealkiri = Utils.leiaAlaSõne(htmlInformatsioon, "aria-label=\"", "\" href=\"");

                if (pealkiri.contains("\" class=\"")) {
                    pealkiri = pealkiri.replace("\" class=\"", "");
                }

                // Kui pealkiri on tühi või ei sobi, siis jätame selle artikli vahele
                if (pealkiri.equals("ERR.ee materjalide kasutustingimused") || pealkiri.isEmpty()) {
                    indeks = lõpuIndeks;
                    continue;
                }

                // Kõik ei ole uudised, mõned on reklaamid https://jupiter.err.ee/ jaoks.
                String link = Utils.leiaAlaSõne(htmlInformatsioon, "href=\"//", "\"><span class=");

                if (lubatudUudiseDomeenid.stream().noneMatch(link::contains)) {
                    indeks = lõpuIndeks;
                    continue;
                }

                if (!link.contains("https://")) {
                    link = "https://" + link;
                }

                // Lisame uue uudise.
                uudised.add(new Uudis(link, Utils.lihtsustaHtmlSildid(pealkiri)));

                indeks = lõpuIndeks;
            }
        }

        System.out.println("Kokku uudiseid: " + uudised.size());

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
        String algusHtmlTag = "<div class=\"text flex-row\">";
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
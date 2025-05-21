<<<<<<< Updated upstream
package com.example.projectuudiselugeja;
=======
package com.uudiselugeja.uudiselugeja;
>>>>>>> Stashed changes

import java.io.IOException;

/**
    Klassi eesmärk:     Saab luua universaalse uudise.
    Tähtsamad Meetodid: Konstruktor, void setSisu(String sisu), String toString(), void päriSisu()
*/
public class Uudis {
    private final String url;
    private final String pealkiri;
    private String sisuHTML;
    private String sisu;

    /**
     * Konstruktor, mis loob Uudis objekti, millel on määratud URL ja pealkiri.
     *
     * @param url Uudise URL, mis sisaldab lingi uudisele.
     * @param pealkiri Uudise pealkiri.
     */
    public Uudis(String url, String pealkiri) {
        this.url = url;
        this.pealkiri = pealkiri;
    }

    /**
     * Tagastab uudise sisu HTML-vormingus.
     *
     * @return Uudise sisu HTML-vormingus.
     */
    public String getSisuHTML() {
        return sisuHTML;
    }

    /**
     * Määrab uudise sisu lihtsustatud tekstivormingus.
     *
     * @param sisu Uudise tekstiline sisu.
     */
    public void setSisu(String sisu) {
        this.sisu = sisu;
    }

    /**
     * Pärib uudise sisu antud URL-i järgi ja salvestab HTML-vormingus sisu.
     * Kui sisu pärimine ebaõnnestub, siis trükitakse veateade ja sisu jääb tühjaks.
     */
    public void päriSisu() {
        Skraaper skraaper = new Skraaper(url);
        try {
            sisuHTML = skraaper.getHtml();
        } catch (IOException e) {
            System.out.println("Viga uudise \"" + url + "\" sisu pärimisel: " + e.getMessage());
            sisuHTML = "";
        }
    }

    /**
     * Tagastab uudise kogu teabe stringina, mis sisaldab URL-i, pealkirja ja sisu.
     *
     * @return String, mis esindab uudist koos URL-i, pealkirja ja sisu tekstiga.
     */
    @Override
    public String toString() {
        return "\n" + url + "\n\nPealkiri:\n" + pealkiri + "\n\nSisu:\n" + sisu;
    }

    public String getUrl() {
        return url;
    }

    public String getPealkiri() {
        return pealkiri;
    }

    public String getSisu() {
        return sisu;
    }
}
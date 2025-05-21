package com.example.projectuudiselugeja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
    Klassi eesmärk:     Saada veebilehtede html kood.
    Tähtsamad Meetodid: String getHtml()
*/
public class Skraaper {
    private final String url;

    /**
     * Konstruktor, mis loob Skraaper objekti antud URL-iga.
     *
     * @param url Veebilehe URL, mille HTML sisu soovitakse pärida.
     */
    public Skraaper(String url) {
        this.url = url;
    }

    /**
     * Pärib veebilehe HTML sisu kasutades HTTP GET päringut.
     *
     * @return Tagastab veebilehe HTML sisu stringina.
     * @throws IOException Kui tekib viga veebilehe sisu pärimisel.
     */
    public String getHtml() throws IOException {
        URL url = new URL(this.url);

        HttpURLConnection ühendus = (HttpURLConnection) url.openConnection();
        ühendus.setRequestMethod("GET");

        BufferedReader buffer = new BufferedReader(new InputStreamReader(ühendus.getInputStream()));
        StringBuilder sisu = new StringBuilder();
        String rida;

        while ((rida = buffer.readLine()) != null) {
            sisu.append(rida);
        }

        buffer.close();

        return sisu.toString();
    }
}
package com.example.projectuudiselugeja;

import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class SuvalineDelfi implements EventHandler<MouseEvent> {

    private TextArea text;
    private Label pealkiri;
    private Hyperlink link;

    public SuvalineDelfi(TextArea text, Label pealkiri, Hyperlink link) {
        this.text = text;
        this.pealkiri = pealkiri;
        this.link = link;
    }

    private static Uudis kuvaSuvalineDelfiUudis() { //Main klassist võetud ja muudetud meetod, et töötaks graafikaga
        Delfi delfi = new Delfi();
        List<Uudis> uudised = delfi.leiaKõikUudised();

        if (!uudised.isEmpty()) {
            Uudis uudis = uudised.get(Utils.saaSuvalineArv(uudised.size()));
            delfi.päriUudiseSisu(uudis);
            return uudis;
        }

        return null;
    }

    public void handle(MouseEvent me){
        Uudis uudis = kuvaSuvalineDelfiUudis();
        if (uudis == null) {
            text.setText("");
            pealkiri.setText("Uudiseid ei leitud. :(");
            link.setText("");
        }
        text.setText(uudis.getSisu());
        pealkiri.setText(uudis.getPealkiri());
        link.setText(uudis.getUrl());
    }
}

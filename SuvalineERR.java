import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * Autor: Gregor Saar
*/

/**
 * Klassi eesmärk: Lisada tekstiväljadele uudise pealkirja, sisu ja lingi.
 * Tähtsamad meetodid: Uudis kuvasuvalineERRUudis(), void handle()
*/

public class SuvalineERR implements EventHandler<MouseEvent> {

    private TextArea text;
    private Label pealkiri;
    private Hyperlink link;

    /**
     * Konstruktor, mis loob eventi
     * @param text Sisu teksti väli
     * @param pealkiri Pealkirja teksti väli
     * @param link Lingi teksti väli
     */
    public SuvalineERR(TextArea text, Label pealkiri, Hyperlink link) {
        this.text = text;
        this.pealkiri = pealkiri;
        this.link = link;
    }

    /**
     * Tagastab suvalise ERR-i uudise
     * @return Suvalise uudise objekt
     */
    private static Uudis kuvaSuvalineERRUudis() { //Main klassist võetud ja muudetud meetod, et töötaks graafikaga
        ERR ERR = new ERR();
        List<Uudis> uudised = ERR.leiaKõikUudised();

        if (!uudised.isEmpty()) {
            Uudis uudis = uudised.get(Utils.saaSuvalineArv(uudised.size()));
            ERR.päriUudiseSisu(uudis);
            return uudis;
        }

        return null;
    }

    /**
     * Lisab, vajalikud tekstid oma tekstiväljadesse
     * @param me
     */
    public void handle(MouseEvent me){
        Uudis uudis = kuvaSuvalineERRUudis();
        if (uudis == null) {
            text.setText("");
            pealkiri.setText("Uudiseid ei leitud. :(");
            link.setText("");
        }
        text.setText(uudis.getSisu());
        pealkiri.setText(uudis.getPealkiri());
        link.setText(uudis.getUrl());

        ViimaneVaadatudUudis.setViimaneVaadatudUudis(uudis.getPealkiri(), uudis.getSisu(), uudis.getUrl());
    }
}

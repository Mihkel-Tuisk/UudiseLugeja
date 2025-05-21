package com.example.projectuudiselugeja;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/*Autor:                   Mihkel Tuisk, Gregor Saar

Programmi Kirjeldus:     Saada kiirelt suvalisi uudiseid teatud uudisteportaalidest. Konsoolist pead sisestama millisest
uudiseportaalist saada uudiseid.

        Etapid:                  1. Loo Skraaper klass
                             2. Loo Utils klass
                             3. Loo Delfi ja ERR klass
                             4. Loo Uudis klass
                             6. Pane kõik kokku main klassis ja testi.
                             7. Loo graafika jaoks klass ning eventide jaoks eraldi klassid
                             8. Testi ja muuda paigutust, kuni näeb välja ja töötab õigesti

Liikmete panus:          Mihkel Tuisk 50%
Gregor Saar 50%

Ligikaudne Ajakulu:      Esimene etapp: 7h 30min
Teine etapp: 5h

Tegemise Mured:          Igal uudiseportaalil on erinev viis kuidas kuvatakse uudiseid html-is
Javafx üldiselt ajab segadusse sellega, et mis elemente on hea kasutada

Hinnang Töö Lõppemusele: 9.2/10. String lihtsustaHtmlSildid(String tekst) vajab veel arendamist, vahepeal satub HTMLi
inimloetavasse teksti sisse.
Vahepeal pealkirja ja lingi tekst võib mitte mahtuda korralikult

Kas Programm Töötab?     Main meetodis on võimal testida kõiki variante. Alguses lõin Skraaperi, et testida kuidas
saada HTML sisu lehekülgedelt, siis lõin Utils classi, et lihtsusdada elu. Pealse seda
testisin kuidas täpsemalt HTMList välja võtta pealkiri ja sisu (Delfi ja ERR class).
        */

public class UudiseLugeja extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox vasakpool = new VBox(10); // Loome vboxi kuhu tekivad vasaku poole
        vasakpool.setPrefWidth(200);
        vasakpool.setPadding(new Insets(10));

        Label tiitel = new Label("Uudise Lugeja"); // loome pealkirja programmile
        tiitel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold");
        VBox.setVgrow(tiitel, Priority.ALWAYS);

        Label kirjeldus = new Label("Valige, kummast lehest soovite suvalist uudist:"); // kiire juhis, mida peab tegema
        kirjeldus.setWrapText(true);

        Button delfi = new Button("Delfist"); // nupp, millega saab suvalise delfi uudise
        delfi.setMinSize(90,90);
        Button err = new Button("ERR-ist"); // nupp, millega saab suvalise err-i uudise
        err.setMinSize(90,90);
        Button kustuta = new Button("Tühjenda"); // nupp, mis tühjendab teksti kastid
        err.setMinSize(90,90);

        HBox hb = new HBox(10); // hbox millesse lisame eer-i ja delfi nupud, et need oleks kõrvuti
        hb.setSpacing(10);
        VBox.setVgrow(hb, Priority.ALWAYS);
        HBox.setHgrow(hb, Priority.ALWAYS);
        hb.getChildren().addAll(delfi, err); // Lisame nuppud Hboxi

        vasakpool.getChildren().addAll(tiitel, kirjeldus, hb, kustuta); // lisame kõik elemendid vasakusse poole
        vasakpool.setMinWidth(210);
        vasakpool.setMaxWidth(210);

        TextArea sisu = new TextArea(); // loome sisu teksti kasti
        sisu.setWrapText(true);
        sisu.setEditable(false);
        VBox.setVgrow(sisu, Priority.ALWAYS);

        ScrollPane pane = new ScrollPane(sisu); // Scrollpane laseb meil kerida, kui sisu liiga pikk on
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        VBox.setVgrow(pane, Priority.ALWAYS);

        Label pealkiri = new Label("Uudis tekib siia:"); // Teksti kast, kuhu tekib uudise pealkiri
        pealkiri.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        pealkiri.setWrapText(true);
        pealkiri.setMinHeight(50);
        VBox.setVgrow(pealkiri, Priority.ALWAYS);

        Hyperlink link1 = new Hyperlink("Uudise link tekib siia"); // Hyperlink, et lingile vajutades saaks uudise lehele minna
        link1.setWrapText(true);
        link1.setMinHeight(50);
        VBox.setVgrow(link1, Priority.ALWAYS);

        link1.setOnAction(e -> { // lisame tegevuse, et hyperlink avaks lingi
            String linktekst = link1.getText();
            if(Desktop.isDesktopSupported() && linktekst.contains("http://") || linktekst.contains("https://")) { // kontroll, et kas link on sobilik
                try {
                    Desktop.getDesktop().browse(new URI(linktekst));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox parempool = new VBox(10, pealkiri, pane, link1); // Loome Vboxi parema poole elementide jaoks ning lisame need ka kohe sisse
        parempool.setStyle("-fx-padding: 10;");
        parempool.setMinWidth(200);

        SplitPane splitPane = new SplitPane(); // loome splitpanei, et programmil oleks eraldatud kaks poolt
        splitPane.getItems().addAll(vasakpool, parempool); // lisame mõlemad pooled
        splitPane.setDividerPositions(0.3); // Et pooled ei oleks tapselt pooleks
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.setMinWidth(500);

        delfi.setOnMouseClicked(new SuvalineDelfi(sisu, pealkiri, link1)); // lisame tegevused nuppudele
        err.setOnMouseClicked(new SuvalineDelfi(sisu, pealkiri, link1));
        kustuta.setOnMouseClicked(e -> {
            sisu.setText("");
            pealkiri.setText("Uudis tekib siia:");
            link1.setText("Uudise link tekib siia");
        });

        Scene scene = new Scene(splitPane, 500,600); // loome stseeni
        stage.setTitle("Uudise Lugeja");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
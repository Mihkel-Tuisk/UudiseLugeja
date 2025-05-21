<<<<<<< Updated upstream
package com.example.projectuudiselugeja;
=======
package com.uudiselugeja.uudiselugeja;
>>>>>>> Stashed changes

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UudiseLugeja extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox vasakpool = new VBox(10);
        vasakpool.setPrefWidth(200);
        vasakpool.setPadding(new Insets(10));

        Label tiitel = new Label("Uudise Lugeja");
        tiitel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold");
        VBox.setVgrow(tiitel, Priority.ALWAYS);

        Label kirjeldus = new Label("Valige, kummast lehest soovite suvalist uudist:");
        kirjeldus.setWrapText(true);

        Button delfi = new Button("Delfist");
        delfi.setMinSize(90,90);
        Button err = new Button("ERR-ist");
        err.setMinSize(90,90);

        HBox hb = new HBox(10);
        hb.setSpacing(10);
        VBox.setVgrow(hb, Priority.ALWAYS);
        HBox.setHgrow(hb, Priority.ALWAYS);
        hb.getChildren().addAll(delfi, err);

        vasakpool.getChildren().addAll(tiitel, kirjeldus, hb);
        vasakpool.setMinWidth(210);
        vasakpool.setMaxWidth(210);

        TextArea sisu = new TextArea();
        sisu.setWrapText(true);
        sisu.setEditable(false);
        VBox.setVgrow(sisu, Priority.ALWAYS);

        ScrollPane pane = new ScrollPane(sisu);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        VBox.setVgrow(pane, Priority.ALWAYS);

        Label pealkiri = new Label("Uudis tekib siia:");
        pealkiri.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        pealkiri.setWrapText(true);

        Label link = new Label("Uudise link tekib siia");
        link.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        link.setWrapText(true);

        VBox parempool = new VBox(10, pealkiri, pane, link);
        parempool.setStyle("-fx-padding: 10;");
        parempool.setMinWidth(200);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(vasakpool, parempool);
        splitPane.setDividerPositions(0.3);
        splitPane.setMinWidth(500);

        delfi.setOnMouseClicked(new SuvalineDelfi(sisu, pealkiri, link));
        err.setOnMouseClicked(new SuvalineDelfi(sisu, pealkiri, link));

        Scene scene = new Scene(splitPane, 500,300);
        stage.setTitle("Uudise Lugeja");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
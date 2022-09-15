module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.example.tictactoe to javafx.fxml;
    exports com.example.tictactoe;
}
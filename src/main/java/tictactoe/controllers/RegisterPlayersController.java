package tictactoe.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tictactoe.enums.Symbol;
import tictactoe.models.Match;
import tictactoe.models.Player;

import java.io.IOException;

public class RegisterPlayersController {

    @FXML
    private TextField xPlayerName;
    @FXML
    private TextField oPlayerName;


    @FXML
    private void registerNames(ActionEvent event) throws IOException {
        Player xPlayer = new Player(1, xPlayerName.getText(), Symbol.X);
        Player oPlayer = new Player(2, oPlayerName.getText(), Symbol.O);

        Match match = new Match(xPlayer, oPlayer);

        loadBoardScreen(event, match);
    }


    private void loadBoardScreen(ActionEvent event, Match match) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/Board.fxml"));
        // Define fmxl's controller
        fxmlloader.setController(new BoardController(match));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Load fxml on scene
        stage.setTitle("Jogo da Velha");
        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

}

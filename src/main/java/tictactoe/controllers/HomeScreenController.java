package tictactoe.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tictactoe.models.Match;

import java.io.*;

public class HomeScreenController {

    @FXML
    private void newGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/RegisterPlayers.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Load fxml on scene
        stage.setTitle("Jogo da Velha");
        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    @FXML
    private void continueGame(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo");
            alert.setContentText("Nenhum arquivo foi selecionado");
            alert.show();
            return;
        }

        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(file))) {

            // Read save file
            Match match = (Match) read.readObject();
            BoardController boardController = new BoardController(match);

            // Call board screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/fxmls/Board.fxml"));
            // Define fmxl's controller
            fxmlLoader.setController(boardController);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load fxml on scene
            stage.setTitle("Jogo da Velha");
            stage.setScene(new Scene(fxmlLoader.load()));
            boardController.mapMatrixToButtons();
            stage.show();

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo");
            alert.setContentText("Arquivo inválido");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo");
            alert.setContentText("Um erro ocorreu ao ler o arquivo");
            alert.show();
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo");
            alert.setContentText("Não é possível carregar arquivos no momento.");
            alert.show();
        }

    }

}

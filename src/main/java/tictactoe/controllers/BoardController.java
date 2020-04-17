package tictactoe.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import tictactoe.enums.Symbol;
import tictactoe.models.Match;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Label xPlayerLabel;
    @FXML
    private Label oPlayerLabel;
    @FXML
    private Label turnLabel;

    private FileChooser fileChooser = new FileChooser();

    private Match match;
    private int turn;
    private Symbol symbol;


    public BoardController(Match match) {
        this.match = match;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TicTacToe Files", "*.jv"));

        turn = match.getTurn();

        turnLabel.setText(turn == 1 ? Symbol.X.getSymbol() : Symbol.O.getSymbol());
        xPlayerLabel.setText(match.getXPlayer().getName() + ": " + match.getXPlayer().getScore() + " pontos");
        oPlayerLabel.setText(match.getOPlayer().getName() + ": " + match.getOPlayer().getScore() + " pontos");
    }


    // Button action

    @FXML
    private void move(ActionEvent event) {
        // Get and change turn
        symbol = (turn == 1) ? Symbol.X : Symbol.O;
        turnLabel.setText(symbol == Symbol.X ? Symbol.O.getSymbol() : Symbol.X.getSymbol());
        turn = match.changeTurn();

        // Change button visual
        Button button = (Button) event.getSource();
        button.setText(symbol.getSymbol());
        button.setDisable(true);

        mapButtonToMatrix(button);

        analyzeWinner();
    }


    // MenuItems actions

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void loadGame(ActionEvent event) {
        File file = fileChooser.showOpenDialog(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo");
            alert.setContentText("Nenhum arquivo foi selecionado");
            alert.show();
            return;
        }

        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(file))) {

            match = (Match) read.readObject();

            mapMatrixToButtons();

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo salvo");
            alert.setContentText("Arquivo inválido");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo salvo");
            alert.setContentText("Um erro ocorreu ao ler o arquivo");
            alert.show();
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carregar jogo");
            alert.setHeaderText("Erro ao carregar jogo salvo");
            alert.setContentText("Não é possível carregar arquivos no momento.");
            alert.show();
        }

    }

    @FXML
    private void newGame() {
        grid.getChildren().forEach(node -> {
            Button button = (Button) node;

            // Get button index
            int row = setNullToZero(GridPane.getRowIndex(button));
            int column = setNullToZero(GridPane.getColumnIndex(button));

            button.setText("");
            button.setDisable(false);
            match.setBoardCell(row, column, null);
        });
    }

    @FXML
    private void saveGame(ActionEvent event) {
        File file = fileChooser.showSaveDialog(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        if (file == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Salvar jogo");
            alert.setHeaderText("Erro ao salvar jogo");
            alert.setContentText("Nenhum arquivo foi selecionado");
            alert.show();
            return;
        }

        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))) {

            write.writeObject(match);

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Salvar jogo");
            alert.setHeaderText("Erro ao salvar jogo");
            alert.setContentText("Arquivo inválido");
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Salvar jogo");
            alert.setHeaderText("Erro ao salvar jogo");
            alert.setContentText("Um erro ocorreu ao salvar o arquivo");
            alert.show();
        }
    }


    // Helper methods

    /**
     * <p>
     * Analyzes if there's winner
     * </p>
     */
    private void analyzeWinner() {
        int winner = match.analyzeMove();
        if (winner != -1) { // Game ended
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Vencedor");
            alert.setHeaderText(null);
            String contentText = "";
            if (winner == 1) {
                contentText = "Vencedor: " + match.getXPlayer().getName();
            } else if (winner == 2) {
                contentText = "Vencedor: " + match.getOPlayer().getName();
            } else {
                contentText = "Deu velha!!!";
            }
            alert.setContentText(contentText);

            // Show alert and wait confirmation to display new board
            ButtonType buttonTypeNewGame = new ButtonType("Novo Jogo");
            alert.getButtonTypes().setAll(buttonTypeNewGame);
            alert.showAndWait()
                    .filter(response -> response == buttonTypeNewGame)
                    .ifPresent(result -> newGame());

            xPlayerLabel.setText(match.getXPlayer().getName() + ": " + match.getXPlayer().getScore() + " pontos");
            oPlayerLabel.setText(match.getOPlayer().getName() + ": " + match.getOPlayer().getScore() + " pontos");
        }
    }

    /**
     * <p>
     * Map button pressed to matrix
     * </p>
     *
     * @param button the button pressed
     */
    private void mapButtonToMatrix(Button button) {
        int row = setNullToZero(GridPane.getRowIndex(button));
        int column = setNullToZero(GridPane.getColumnIndex(button));
        match.setBoardCell(row, column, symbol);
//		match.showboard();
//		System.out.println("(" + row + ", " + column + ")");
    }

    /**
     * <p>
     * Map matrix values to buttons
     * </p>
     */
    public void mapMatrixToButtons() {
        grid.getChildren().forEach(node -> {
            Button button = (Button) node;

            // Get button index
            int row = setNullToZero(GridPane.getRowIndex(button));
            int column = setNullToZero(GridPane.getColumnIndex(button));

            // Get button symbol
            Symbol symbol = match.getBoardCell(row, column);

            // Map to button
            if (symbol == null) {
                button.setText("");
                button.setDisable(false);
            } else {
                button.setText(symbol.getSymbol());
                button.setDisable(true);
            }
        });
    }

    /**
     * <p>
     * Treats method inconsistency when used in junction with SceneBuilder by
     * setting null values to zero.
     * </p>
     *
     * <p>
     * SceneBuilder treats 0 as null or 'not set' so if a node row or column index
     * is 0 the method returns null.
     * </p>
     *
     * @param index the row or column index
     * @return 0 or the original value
     */
    private int setNullToZero(Integer index) {
        if (index == null) {
            index = 0;
        }
        return index;
    }

}

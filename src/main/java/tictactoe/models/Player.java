package tictactoe.models;

import tictactoe.enums.Symbol;

import java.io.Serializable;


public class Player implements Serializable {

    private static final long serialVersionUID = -1685448241695171627L;

    private int id;
    private String name;
    private Symbol symbol;
    private int score;


    public Player(int id, String name, Symbol symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return String.format("Player [id: %s, name: %s, symbol: %s, score: %d]", id, name, symbol, score);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return result = prime * result + ((id == 1 || id == 2) ? 0 : name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;
        return id == other.id;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}

package tictactoe.enums;

public enum Symbol {

    X("X"), O("O");

    private final String symbol;


    Symbol(String symbol) {
        this.symbol = symbol;
    }


    public String getSymbol() {
        return symbol;
    }

}

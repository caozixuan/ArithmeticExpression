import java.util.ArrayList;

public class Parser {
    public ArrayList<TokenInformation> tokens;
    public int curIndex = 0;

    public Parser(ArrayList<TokenInformation> tokens) {
        this.tokens = tokens;
    }

    public boolean E() {
        if (T())
            return E2();
        return false;
    }

    public boolean E2() {
        if (tokens.get(curIndex).type == TokenType.PLUS || tokens.get(curIndex).type == TokenType.MINUS) {
            readNextToken();
            if (T()) {
                return E2();
            }
            return false;
        }
        return true;
    }

    public boolean T() {
        if (F())
            if (T2())
                return true;
        return false;
    }

    public boolean T2() {
        if (tokens.get(curIndex).type == TokenType.MULTIPLE || tokens.get(curIndex).type == TokenType.DIVIDE || tokens.get(curIndex).type == TokenType.IDENTIFIER
                || tokens.get(curIndex).type == TokenType.INT || tokens.get(curIndex).type == TokenType.DOUBLE) {
            if (readNextToken()) {
                if (F())
                    if (T2())
                        return true;
                    else
                        return false;
            }
        }
        return true;
    }

    public boolean F() {
        if (tokens.get(curIndex).type == TokenType.LEFTBRACKET) {
            if (readNextToken()) {
                if (E()) {
                    if (tokens.get(curIndex).type == TokenType.RIGHTBRACKET)
                        return true;
                }
                return false;
            }
        } else if (tokens.get(curIndex).type == TokenType.IDENTIFIER || tokens.get(curIndex).type == TokenType.INT
                || tokens.get(curIndex).type == TokenType.DOUBLE) {
            readNextToken();
            return true;
        }

        return false;
    }

    public boolean readNextToken() {
        if (curIndex < tokens.size() - 1) {
            curIndex++;
            return true;
        } else {
            curIndex++;
            return false;
        }
    }

    public boolean parser() {
        return E();
    }

    

/*
public static void main(String[] args) {

    }
 */

}

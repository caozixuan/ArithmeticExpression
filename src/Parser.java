import java.util.ArrayList;

public class Parser {
    public ArrayList<TokenInformation> tokens;
    public int curIndex = 0;

    public Parser (ArrayList<TokenInformation> tokens) {
        this.tokens = tokens;
    }

    public boolean E() {
        return true;
    }

    public boolean E2() {
        return true;
    }

    public boolean T() {
        return true;
    }

    public boolean T2() {
        return true;
    }

    public boolean F() {
        return true;
    }

    public boolean readNextToken() {
        if (curIndex < tokens.size() - 1) {
            curIndex++;
            return true;
        }
        else {
            curIndex++;
            return false;
        }
    }

    public boolean parser() {
        return true;
    }

    


    public static void main(String[] args) {

    }
}

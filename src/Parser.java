import java.util.ArrayList;

public class Parser {
    public ArrayList<TokenInformation> tokens;
    public int curIndex = 0;

    public Parser(ArrayList<TokenInformation> tokens) {
        this.tokens = tokens;
    }

    public boolean E() {
        System.out.println("E->TE'");
        if (T())
            return E2();
        return false;
    }

    public boolean E2() {
        if (tokens.get(curIndex).type == TokenType.PLUS || tokens.get(curIndex).type == TokenType.MINUS) {
            if(tokens.get(curIndex).type == TokenType.PLUS)
                System.out.println("E'->+TE'");
            else
                System.out.println("E'->-TE'");
            readNextToken();
            if (T()) {
                return E2();
            }
            return false;
        }
        System.out.println("E'->e");
        return true;
    }

    public boolean T() {
        System.out.println("T->FT'");
        if (F())
            if (T2())
                return true;
        return false;
    }

    public boolean T2() {
        if (tokens.get(curIndex).type == TokenType.MULTIPLE || tokens.get(curIndex).type == TokenType.DIVIDE) {
            if(tokens.get(curIndex).type == TokenType.MULTIPLE)
                System.out.println("T'->*FT'");
            else
                System.out.println("T'->/FT'");
            if (readNextToken()) {

                if (F())
                    if (T2())
                        return true;
                    else
                        return false;
            }
        }
        System.out.println("T'->e");
        return true;
    }

    public boolean F() {
        if (tokens.get(curIndex).type == TokenType.LEFTBRACKET) {
            System.out.println("F->(E)");
            if (readNextToken()) {
                if (E()) {
                    if (tokens.get(curIndex).type == TokenType.RIGHTBRACKET){
                        if(readNextToken())
                            return true;
                        else{
                            curIndex--;
                            return true;
                        }
                    }
                }
                return false;
            }
        } else if (tokens.get(curIndex).type == TokenType.IDENTIFIER || tokens.get(curIndex).type == TokenType.INT
                || tokens.get(curIndex).type == TokenType.DOUBLE) {
            System.out.println("F->i");
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
            //curIndex++;
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

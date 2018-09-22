import java.util.ArrayList;

enum TokenType{
    INT,           //整数
    DOUBLE,          //浮点数
    IDENTIFIER,   //标识符
    PLUS,          //加
    MINUS,         //减
    MULTIPLE,      //乘
    DIVIDE,        //除
    LEFTBRACKET,  //左括号
    RIGHTBRACKET,  //右括号
    ERROR         //报错
}

class TokenInformation{
    TokenType type;                    //记录token类型
    String information;               //记录token的具体信息比如一个double值为2.55

    public TokenInformation(TokenType type, String information) {
        this.type = type;
        this.information = information;
    }
}


public class Parser {
    int curIndex;      //记录当前正在处理的字符
    int lastIndex;     //记录上一个处理好的字符
    char[] chars;      //要分析的字符串

    public Parser(int curIndex, int lastIndex, String sentence) {
        this.curIndex = curIndex;
        this.lastIndex = lastIndex;
        this.chars = sentence.toCharArray();
    }

    public void readNextChar(){
        curIndex++;
    }

    public ArrayList<TokenInformation> parser(String sentence) throws Exception{
        ArrayList<TokenInformation> tokenInformations = new ArrayList<>();
        while(curIndex<chars.length){
            char c = chars[curIndex];
            if(('a'<c&&c<'z')||('A'<c&&c<'Z')||c=='_'||c=='$'){
                TokenInformation tokenInformation = isIndentifier();
                tokenInformations.add(tokenInformation);
            }
            else if(('0'<c&&c<'9')||c=='-'){
                //如果为-，可能返回一个operator
                TokenInformation tokenInformation = isIntegerOrDouble();
                tokenInformations.add(tokenInformation);
            }
            else if(c=='+'||c=='*'||c=='/'||c=='('||c==')'){
                TokenInformation tokenInformation = isOperator();
                tokenInformations.add(tokenInformation);
            }
            else{
                throw new Exception("unknown error");
            }
        }
        return tokenInformations;
    }

    public TokenInformation isIntegerOrDouble() throws Exception{
        readNextChar();   //规定：读取下一个字符的过程在函数中进行而不是在parser中进行
        return null;
    }

    public TokenInformation isIndentifier() throws Exception{
        readNextChar();
        char c = chars[curIndex];
        while(curIndex<chars.length&&('a'<c&&c<'z')||('0'<c&&c<'9')||('A'<c&&c<'Z')||c=='_'||c=='$'){
            readNextChar();
            c = chars[curIndex];
        }

        readNextChar();
        return null;
    }

    public String getString(int start, int end){
        char[] chars_part = new char[end-start];
        for(int i=0;i<=end-start;i++){
            chars_part[i]=chars[start+i];
        }
        String s = String.copyValueOf(chars_part);
        return s;
    }
    public TokenInformation isOperator() throws Exception{
        readNextChar();
        return null;
    }
}

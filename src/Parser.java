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

    @Override
    public String toString(){
        String result = "";
        result=result+" type:"+type+" information:"+information+" ";
        return result;
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

    public boolean readNextChar(){
        if(curIndex<chars.length){
            curIndex++;
            if(curIndex==chars.length)
                return false;
            return true;
        }
        return false;
    }

    public ArrayList<TokenInformation> parser() throws Exception{
        ArrayList<TokenInformation> tokenInformations = new ArrayList<>();
        while(curIndex<chars.length){
            char c = chars[curIndex];
            if(('a'<=c&&c<='z')||('A'<=c&&c<='Z')||c=='_'||c=='$'){
                TokenInformation tokenInformation = isIndentifier();
                tokenInformations.add(tokenInformation);
            }
            else if('0'<=c&&c<='9'){
                //如果为-，可能返回一个operator
                TokenInformation tokenInformation = isIntegerOrDouble();
                tokenInformations.add(tokenInformation);
            }
            else if(c=='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')'){
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
        //readNextChar();   //规定：读取下一个字符的过程在函数中进行而不是在parser中进行
        char c = chars[curIndex];
        boolean isNegative = false;   // 可能不需要
        boolean isDouble = false;
        TokenInformation tokenInformation = null;

        if ('0' <= c && c <= '9') {
            if (c == '0') {
                if (readNextChar()) {
                    c = chars[curIndex];
                    if (c == '.')
                        curIndex--;
                    else {
                        tokenInformation = new TokenInformation(TokenType.INT, "0");
                        return tokenInformation;
                    }
                }
                else {
                    tokenInformation = new TokenInformation(TokenType.INT, "0");
                    return tokenInformation;
                }
            }
            if(!readNextChar()){
                String information = getString(lastIndex+1,curIndex-1);
                if (isDouble)
                    tokenInformation = new TokenInformation(TokenType.DOUBLE, information);
                else
                    tokenInformation = new TokenInformation(TokenType.INT, information);
                return tokenInformation;
            }
            else
                curIndex--;
            c = chars[curIndex];
            int dotCount = 0;
            while (curIndex < chars.length && (('0' <= c && c <= '9') || c == '.')) {
                if (c == '.' && dotCount > 0) {
                    throw new Exception("两个小数点");   // 错误：两个小数点
                }
                else if (c == '.') {
                    dotCount++;
                    isDouble = true;
                    readNextChar();
                    if (curIndex < chars.length)
                        c = chars[curIndex];
                }
                else {
                    readNextChar();
                    if (curIndex < chars.length)
                        c = chars[curIndex];
                }
            }
            if (('a' <= c && c <= 'z') || ('A'<= c && c <= 'Z') || c == '_') {
                // 注意：这里只考虑了数字里面不能存在字母的情况，其他特殊情况之后按需添加。
                throw new Exception();
            }
            String information = getString(lastIndex+1,curIndex-1);
            if (isDouble)
                tokenInformation = new TokenInformation(TokenType.DOUBLE, information);
            else
                tokenInformation = new TokenInformation(TokenType.INT, information);

            lastIndex = curIndex-1;
            return tokenInformation;
        }
        else
            throw new Exception();
        // 注：此处未处理4.2000000000的情况，之后按需添加。
    }

    public TokenInformation isIndentifier() throws Exception{
        if(!readNextChar()){
            String information = getString(lastIndex+1,curIndex-1);
            TokenInformation tokenInformation = new TokenInformation(TokenType.IDENTIFIER, information);
            return tokenInformation;
        }
        char c = chars[curIndex];
        while(curIndex<chars.length&&('a'<=c&&c<='z')||('0'<=c&&c<='9')||('A'<=c&&c<='Z')||c=='_'||c=='$'){
            if(!readNextChar()){
                String information = getString(lastIndex+1,curIndex-1);
                TokenInformation tokenInformation = new TokenInformation(TokenType.IDENTIFIER, information);
                return tokenInformation;
            }
            c = chars[curIndex];
        }
        String information = getString(lastIndex+1,curIndex-1);
        TokenInformation tokenInformation = new TokenInformation(TokenType.IDENTIFIER, information);
        lastIndex = curIndex-1;
        return tokenInformation;
    }

    public String getString(int start, int end){
        char[] chars_part = new char[end-start+1];
        for(int i=0;i<=end-start;i++){
            chars_part[i]=chars[start+i];
        }
        String s = String.copyValueOf(chars_part);
        return s;
    }
    public TokenInformation isOperator() throws Exception{
        TokenInformation tokenInformation = null;
        char c = chars[curIndex];
        switch (c){
            case '+':
                tokenInformation = new TokenInformation(TokenType.PLUS,null);
                break;
            case '-':
                tokenInformation = new TokenInformation(TokenType.MINUS,null);
                break;
            case '*':
                tokenInformation = new TokenInformation(TokenType.MULTIPLE,null);
                break;
            case '/':
                tokenInformation = new TokenInformation(TokenType.DIVIDE,null);
                break;
            case '(':
                tokenInformation = new TokenInformation(TokenType.LEFTBRACKET,null);
                break;
            case ')':
                tokenInformation = new TokenInformation(TokenType.RIGHTBRACKET,null);
                break;
        }
        lastIndex = curIndex;
        readNextChar();
        return tokenInformation;
    }
}

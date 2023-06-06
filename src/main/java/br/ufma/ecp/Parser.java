package br.ufma.ecp;

import javax.lang.model.type.TypeKind;

import br.ufma.ecp.token.Token;
import br.ufma.ecp.token.TokenType;

public class Parser {

    private static class ParseError extends RuntimeException {}
    private Scanner scan;
    private Token currentToken;
    private Token peekToken;
    private StringBuilder xmlOutput = new StringBuilder();
    


    private void nextToken() {
        currentToken = peekToken;
        peekToken = scan.nextToken();
    }

     
    public Parser (byte[] input) {
        scan = new Scanner(input);
        nextToken();
    }

    public void parse () {
        
    }
    
// funções auxiliares
    public String XMLOutput() {
        return xmlOutput.toString();
    }

    private void printNonTerminal(String nterminal) {
        xmlOutput.append(String.format("<%s>\r\n", nterminal));
    }


    boolean peekTokenIs(TokenType type) {
        return peekToken.type == type;
    }

    boolean currentTokenIs(TokenType type) {
        return currentToken.type == type;
    }

    private void expectPeek(TokenType... types) {
        for (TokenType type : types) {
            if (peekToken.type == type) {
                expectPeek(type);
                return;
            }
        }

       throw error(peekToken, "Expected a statement");

    }

    private void expectPeek(TokenType type) {
        if (peekToken.type == type) {
            nextToken();
            xmlOutput.append(String.format("%s\r\n", currentToken.toString()));
        } else {
            throw error(peekToken, "Expected "+type.name());
        }
    }


    private static void report(int line, String where,
        String message) {
            System.err.println(
            "[line " + line + "] Error" + where + ": " + message);
    }


    private ParseError error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
        return new ParseError();
    }




    void expr() {
        number();
        oper();
    }

    void number () {
        System.out.println(currentToken.lexeme);
        match(TokenType.NUMBER);
    }


    //class Main{}
    public void parserClass(){
        printNonTerminal("class");

        expectPeek(TokenType.CLASS);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.LBRACE);
        expectPeek(TokenType.RBRACE);

        printNonTerminal("/class");
    }

    //classVarDec
    public void parseClassVarDec(){
        printNonTerminal("classVarDec");

        expectPeek(TokenType.FIELD);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.SEMICOLON);
    
        printNonTerminal("/classVarDec");

    }

    //SubroutineDec
    public void parseSubroutineDec(){
        printNonTerminal("subroutineDec");

        expectPeek(TokenType.CONSTRUCTOR);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.LPAREN);
        
                
               
        printNonTerminal("parameterList");
        expectPeek(TokenType.INT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.COMMA);
        expectPeek(TokenType.INT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.COMMA);
        expectPeek(TokenType.INT);
        expectPeek(TokenType.IDENT);
        printNonTerminal("/parameterList");

        expectPeek(TokenType.RPAREN);

        printNonTerminal("subroutineBody");
        expectPeek(TokenType.LBRACE);
        printNonTerminal("statements");

        printNonTerminal("letStatement");
        expectPeek(TokenType.LET);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.EQ);
        printNonTerminal("expression");
        printNonTerminal("term");
        expectPeek(TokenType.IDENT);
        printNonTerminal("/term");
        printNonTerminal("/expression");
        expectPeek(TokenType.SEMICOLON);
        printNonTerminal("/letStatement");

        printNonTerminal("letStatement");
        expectPeek(TokenType.LET);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.EQ);
        printNonTerminal("expression");
        printNonTerminal("term");
        expectPeek(TokenType.IDENT);
        printNonTerminal("/term");
        printNonTerminal("/expression");
        expectPeek(TokenType.SEMICOLON);
        printNonTerminal("/letStatement");

        printNonTerminal("letStatement");
        expectPeek(TokenType.LET);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.EQ);
        printNonTerminal("expression");
        printNonTerminal("term");
        expectPeek(TokenType.IDENT);
        printNonTerminal("/term");
        printNonTerminal("/expression");
        expectPeek(TokenType.SEMICOLON);
        printNonTerminal("/letStatement");

        printNonTerminal("doStatement");
        expectPeek(TokenType.DO);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.LPAREN);
        printNonTerminal("expressionList");
        printNonTerminal("/expressionList");
        expectPeek(TokenType.RPAREN);
        expectPeek(TokenType.SEMICOLON);
        printNonTerminal("/doStatement");

        printNonTerminal("returnStatement");
        expectPeek(TokenType.RETURN);
        printNonTerminal("expression");
        printNonTerminal("term");
        expectPeek(TokenType.THIS);
        printNonTerminal("/term");
        printNonTerminal("/expression");
        expectPeek(TokenType.SEMICOLON);
        printNonTerminal("/returnStatement");

        printNonTerminal("/statements");
        expectPeek(TokenType.RBRACE);

        printNonTerminal("/subroutineBody");
        
        printNonTerminal("/subroutineDec");
    }

//ParseDo
    public void parseDo(){
        printNonTerminal("doStatement");

        expectPeek(TokenType.DO);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.DOT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.LPAREN);

        printNonTerminal("expressionList");
        printNonTerminal("expression");
        printNonTerminal("term");

        expectPeek(TokenType.NUMBER);

        printNonTerminal("/term");
        printNonTerminal("/expression");
        printNonTerminal("/expressionList");
        
        expectPeek(TokenType.RPAREN);
        expectPeek(TokenType.SEMICOLON);
        

        printNonTerminal("/doStatement");

    }



    //parseIf
public void parseIf(){
    printNonTerminal("ifStatement");
    expectPeek(TokenType.IF);
    expectPeek(TokenType.LPAREN);

    printNonTerminal("expression");
    printNonTerminal("term");
    expectPeek(TokenType.IDENT);
    printNonTerminal("/term");
    expectPeek(TokenType.EQ);
    printNonTerminal("term");
    expectPeek(TokenType.NUMBER);
    printNonTerminal("/term");
    printNonTerminal("/expression");

    expectPeek(TokenType.RPAREN);
    expectPeek(TokenType.LBRACE);
    
    printNonTerminal("statements");
    printNonTerminal("doStatement");
    expectPeek(TokenType.DO);
    expectPeek(TokenType.IDENT);
    expectPeek(TokenType.DOT);
    expectPeek(TokenType.IDENT);
    expectPeek(TokenType.LPAREN);
    printNonTerminal("expressionList");
    printNonTerminal("/expressionList");
    expectPeek(TokenType.RPAREN);
    expectPeek(TokenType.SEMICOLON);
    printNonTerminal("/doStatement");
    printNonTerminal("/statements");
    
    expectPeek(TokenType.RBRACE);

    printNonTerminal("/ifStatement");

}



    void parseTerm() {
        printNonTerminal("term");
        switch (peekToken.type) {
          case NUMBER:
            expectPeek(TokenType.NUMBER);
            break;
          case STRING:
            expectPeek(TokenType.STRING);
            break;
          case FALSE:
          case NULL:
          case TRUE:
            expectPeek(TokenType.FALSE, TokenType.NULL, TokenType.TRUE);
            break;
          case THIS:
            expectPeek(TokenType.THIS);
            break;
          case IDENT:
            expectPeek(TokenType.IDENT);
            break;
          default:
            throw error(peekToken, "term expected");
        }
    
        printNonTerminal("/term");
      }


    static public boolean isOperator(String op) {
        return "+-*/<>=~&|".contains(op);
   }


   void parseExpression() {
    printNonTerminal("expression");
    parseTerm ();
    while (isOperator(peekToken.lexeme)) {
        expectPeek(peekToken.type);
        parseTerm();
    }
    printNonTerminal("/expression");
}

    //parseLet
public void parseLet() {
    printNonTerminal("letStatement");
    expectPeek(TokenType.LET);
    expectPeek(TokenType.IDENT);
    expectPeek(TokenType.EQ);
    
    printNonTerminal("expression");
    printNonTerminal("term");
    expectPeek(TokenType.IDENT);
    expectPeek(TokenType.DOT);
    expectPeek(TokenType.IDENT);
    expectPeek(TokenType.LPAREN);

    printNonTerminal("expressionList");

    printNonTerminal("expression");
    printNonTerminal("term");
    expectPeek(TokenType.NUMBER);
    printNonTerminal("/term");
    printNonTerminal("/expression");
    expectPeek(TokenType.COMMA);

    printNonTerminal("expression");
    printNonTerminal("term");
    expectPeek(TokenType.NUMBER);
    printNonTerminal("/term");
    printNonTerminal("/expression");
    expectPeek(TokenType.COMMA);

    printNonTerminal("expression");
    printNonTerminal("term");
    expectPeek(TokenType.NUMBER);
    printNonTerminal("/term");
    printNonTerminal("/expression");
    
    printNonTerminal("/expressionList");
    expectPeek(TokenType.RPAREN);

    printNonTerminal("/term");
    printNonTerminal("/expression");
    

    expectPeek(TokenType.SEMICOLON);
    printNonTerminal("/letStatement");
    
}
    


   private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        }else {
            throw new Error("syntax error");
        }
   }

    void oper () {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            number();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            number();
            System.out.println("sub");
            oper();
        } else if (currentToken.type == TokenType.EOF) {
            // vazio
        } else {
            throw new Error("syntax error");
        }
    }

    public String VMOutput() {
        return "";
    }

}

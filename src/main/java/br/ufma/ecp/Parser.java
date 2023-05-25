package br.ufma.ecp;

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
        expr();
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
    public void parseCassVarDec(){
        printNonTerminal("classVarDec");

        expectPeek(TokenType.FIELD);
        expectPeek(TokenType.INT);
        expectPeek(TokenType.IDENT);
        expectPeek(TokenType.SEMICOLON);
    
        printNonTerminal("/classVarDec");

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

void parseLet() {
    printNonTerminal("letStatement");
    expectPeek(TokenType.LET);
    expectPeek(TokenType.IDENTIFIER);

    if (peekTokenIs(TokenType.LBRACKET)) {
        expectPeek(TokenType.LBRACKET);
        parseExpression();
        expectPeek(TokenType.RBRACKET);
    }

    expectPeek(TokenType.EQ);
    parseExpression();
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

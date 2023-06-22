package br.ufma.ecp.token;

import java.util.List;
//import java.util.Map;

public enum TokenType {
    PLUS,MINUS, SLASH,

     // Literals.
     NUMBER,
     STRING,


     IDENTIFIER,
 
     // keywords
     WHILE, CLASS,CONSTRUCTOR,FUNCTION,
     METHOD,FIELD,STATIC,VAR,INT,
     CHAR,BOOLEAN,VOID,TRUE,FALSE,
     NULL,THIS,LET,DO,IF,ELSE, RETURN,
     EOF,


          // symbols
    LPAREN,RPAREN,
    LBRACE, RBRACE,
    LBRACKET,RBRACKET,

    COMMA, SEMICOLON, DOT,
  
    ASTERISK,

    AND, OR, NOT,

    LT, GT, EQ, 
     ILLEGAL;



     

     static public boolean isSymbol (char c) {
        String symbols = "{}()[].,;+-*/&|<>=~";
        return symbols.indexOf(c) > -1;
    }

     static public boolean isOperator(String c) {
        return "+-*/<>=~&|".contains(c);
    }



    static public boolean isKeyword (TokenType type) {
        List<TokenType> keywords  = 
            List.of(
                METHOD,
                WHILE,
                IF,
                CLASS, FIELD, INT, VOID, BOOLEAN,
                CONSTRUCTOR
            );
            return keywords.contains(type);
    }

}

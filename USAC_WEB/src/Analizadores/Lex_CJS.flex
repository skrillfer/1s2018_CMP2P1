package Analizadores;
import Estructuras.*;
import java.util.LinkedList;
import java_cup.runtime.*;

import Errores.*;
%%
 
%{
    StringBuilder string = new StringBuilder();

static LinkedList<Erro_r> listaErrores= new LinkedList();

  public void adderror(int fila, int columna, String valor){
       Erro_r n = new Erro_r(fila,columna,valor,"");
       listaErrores.add(n);
  }

/*public void agregar(String Token,String Lexema,int Fila, int Columna){
  Simbolo simbolo= new Simbolo(Token,Lexema,Fila,Columna);
  TablaSimbolos.add(simbolo);	
}
  

public LinkedList<Simbolo> retornarTablaSimbolos(){
     return AnalizadorLexico.TablaSimbolos;
}*/

public LinkedList<Erro_r> retornarErrores(){       
	return AnalizadorLexico.listaErrores;
}

%}

/* segunda parte: declaramos las directivas y los macros */
%class AL_HTML
%public
%full
%unicode
%line
%column
%char
%cup
%state COMENTARIO
%state STRING

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*


/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]
OctDigit = [0-7]

%%

/* OPERADORES Y SIGNOS */


<YYINITIAL> "+" {return new Symbol(sym.MAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.MENOS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.POR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.DIV, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "^" {return new Symbol(sym.POT, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "%" {return new Symbol(sym.MOD, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "++" {return new Symbol(sym.ADD, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "--" {return new Symbol(sym.SUB, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "--" {return new Symbol(sym.SUB, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<" {return new Symbol(sym.MENQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">" {return new Symbol(sym.MAYQ, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "<=" {return new Symbol(sym.MENIQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">=" {return new Symbol(sym.MAYIQ, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "==" {return new Symbol(sym.IG_IG, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "!=" {return new Symbol(sym.DIF, new token(yycolumn, yyline, yytext()));} 

<YYINITIAL> "&&" {return new Symbol(sym.AND, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "||" {return new Symbol(sym.OR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "!" {return new Symbol(sym.NOT, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> ";" {return new Symbol(sym.PYC, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "(" {return new Symbol(sym.APAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ")" {return new Symbol(sym.CPAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.ALLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.CLLA, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<!"    {yybegin(COMENTARIO);}

<COMENTARIO> [\n]		{System.out.println ("Una linea de comentario");}
<COMENTARIO> "*"		{}
<COMENTARIO> [^"!>"]            {}
<COMENTARIO> "!>"		{yybegin(YYINITIAL);}


<YYINITIAL> "\"" { yybegin(STRING); string.setLength(0); }


<STRING> {
  \"                             { yybegin(YYINITIAL); return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, string.toString())); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
    
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                        				   string.append( val ); }
  
  /* error cases */
  \\.                            { string.append( yytext() ); }/*{ throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }*/
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}


/* PALABRAS RESERVADAS */
//*************************************ELEMENTOS*************************************************************
<YYINITIAL> "DimV" {return new Symbol(sym.DIMV, new token(yycolumn, yyline, yytext()));}

//********************************************************************************************************

/* EXPRESIONES */


<YYINITIAL> {Numero} {return new Symbol(sym.NUM, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Decimal} {return new Symbol(sym.DECIMAL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Id} {return new Symbol(sym.I_D, new token(yycolumn, yyline, yytext()));}

//<YYINITIAL> {String_Literal} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}




{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {adderror(yyline,yycolumn,yytext());}


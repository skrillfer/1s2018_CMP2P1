
package Analizadores;
import Estructuras.*;
import java.util.LinkedList;
import java_cup.runtime.*;

import Errores.*;
%%
 
%{

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
%class AnalizadorLexico
%public
%full
%unicode
%line
%column
%char
%cup
%state COMENTARIO

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*



%%

/* OPERADORES Y SIGNOS */


<YYINITIAL> "+" {return new Symbol(sym.MAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.MENOS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.POR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.DIV, new token(yycolumn, yyline, yytext()));}

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





/* PALABRAS RESERVADAS */
<YYINITIAL> "Void" {return new Symbol(sym.VOID, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "Int" {return new Symbol(sym.INT, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Double" {return new Symbol(sym.DOUBLE, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "String" {return new Symbol(sym.STRING, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Boolean" {return new Symbol(sym.BOOLEAN, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Char" {return new Symbol(sym.CHAR, new token(yycolumn, yyline, yytext()));}

/* EXPRESIONES */


<YYINITIAL> {Numero} {return new Symbol(sym.NUM, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Decimal} {return new Symbol(sym.DECIMAL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Id} {return new Symbol(sym.I_D, new token(yycolumn, yyline, yytext()));}





{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {adderror(yyline,yycolumn,yytext());}



package Analizadores.LenguajeCJS;
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
}

public LinkedList<Erro_r> retornarErrores(){       
	return AnalizadorLexico.listaErrores;
}*/

%}

/* segunda parte: declaramos las directivas y los macros */
%class AL_CJS
%public
%full
%unicode
%line
%column
%char
%cup

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*


cadena = [\"] [^(\")]* [\"]

comm_multilinea = "'/" ~ "/'"


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

<YYINITIAL> "<" {return new Symbol(sym.MENQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<=" {return new Symbol(sym.MENIQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">" {return new Symbol(sym.MAYQ, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> ">=" {return new Symbol(sym.MAYIQ, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "==" {return new Symbol(sym.IG_IG, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "=!" {return new Symbol(sym.DIF, new token(yycolumn, yyline, yytext()));} 


<YYINITIAL> "&&" {return new Symbol(sym.AND, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "||" {return new Symbol(sym.OR, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "!" {return new Symbol(sym.NOT, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> ";" {return new Symbol(sym.PYC, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "," {return new Symbol(sym.COMA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ":" {return new Symbol(sym.DSPTS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "(" {return new Symbol(sym.APAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ")" {return new Symbol(sym.CPAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.ALLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.CLLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "[" {return new Symbol(sym.ACORCH, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "]" {return new Symbol(sym.CCORCH, new token(yycolumn, yyline, yytext()));}

//******************************PROPIEDADES***********************************************
<YYINITIAL> "Conteo" {return new Symbol(sym.CONTEO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "aTexto" {return new Symbol(sym.ATEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Si" {return new Symbol(sym.SI, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Sino" {return new Symbol(sym.SINO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Selecciona" {return new Symbol(sym.SELECCIONA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Caso" {return new Symbol(sym.CASO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Defecto" {return new Symbol(sym.DEFECTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Para" {return new Symbol(sym.PARA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Mientras" {return new Symbol(sym.MIENTRAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Detener" {return new Symbol(sym.DETENER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Imprimir" {return new Symbol(sym.IMPRIMIR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Funcion" {return new Symbol(sym.FUNCION, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Retornar" {return new Symbol(sym.RETORNAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Mensaje" {return new Symbol(sym.MENSAJE, new token(yycolumn, yyline, yytext()));}

//*********************************EVENTOS**************************************************
<YYINITIAL> "Documento" {return new Symbol(sym.DOCUMENTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Obtener" {return new Symbol(sym.OBTENER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Observador" {return new Symbol(sym.OBSERVADOR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "setElemento" {return new Symbol(sym.SETELEMENTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {comm_multilinea} {}
<YYINITIAL> {cadena} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}



/* PALABRAS RESERVADAS */
//*************************************Palabras reservadas****************************************
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

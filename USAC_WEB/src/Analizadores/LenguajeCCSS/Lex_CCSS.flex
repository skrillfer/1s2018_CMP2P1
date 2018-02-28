
package Analizadores.LenguajeCCSS;
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

public LinkedList<Erro_r> retornarErrores(){       
	return null;//AnalizadorLexico.listaErrores;
}

%}

/* segunda parte: declaramos las directivas y los macros */
%class AL_CCSS
%public
%full
%unicode
%line
%column
%char
%ignorecase
%cup

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*


cadena = [\"] [^\"]* [\"]





Comentario1 = "/*" ["*"]* [^*] ~"*/" | "/*" ["*"]* "*/"

Comentario2 = "//" [^\r\n]* [^\r\n]


%%

/* OPERADORES Y SIGNOS */
<YYINITIAL> "+" {return new Symbol(sym.MAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.MENOS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.POR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.DIV, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "(" {return new Symbol(sym.APAR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ")" {return new Symbol(sym.CPAR, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "[" {return new Symbol(sym.ACORCH, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "]" {return new Symbol(sym.CCORCH, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "," {return new Symbol(sym.COMA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ";" {return new Symbol(sym.PYC, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ":" {return new Symbol(sym.DSPTS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL, new token(yycolumn, yyline, yytext()));}


/* propiedades */
<YYINITIAL> "alineado" {return new Symbol(sym.ALINEADO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "izquierda" {return new Symbol(sym.IZQUIERDA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "derecha" {return new Symbol(sym.DERECHA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "centrado" {return new Symbol(sym.CENTRADO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "justificado" {return new Symbol(sym.JUSTIFICADO, new token(yycolumn, yyline, yytext()));}




<YYINITIAL> "texto" {return new Symbol(sym.TEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "formato" {return new Symbol(sym.FORMATO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "mayuscula" {return new Symbol(sym.MAYUSCULA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "minuscula" {return new Symbol(sym.MINUSCULA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "capital-t" {return new Symbol(sym.CAPITAL_T, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "negrilla" {return new Symbol(sym.NEGRILLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "cursiva" {return new Symbol(sym.CURSIVA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "letra" {return new Symbol(sym.LETRA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "tamtex" {return new Symbol(sym.TAMTEXT, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "fondoelemento" {return new Symbol(sym.FONDOELEMENTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "autoredimension" {return new Symbol(sym.AUTOREDIMENSION, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "true" {return new Symbol(sym.TRUE, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "false" {return new Symbol(sym.FALSE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "horizontal" {return new Symbol(sym.HORIZONTAL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "vertical" {return new Symbol(sym.VERTICAL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "visible" {return new Symbol(sym.VISIBLE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "borde" {return new Symbol(sym.BORDE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "opaque" {return new Symbol(sym.OPAQUE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "colortext" {return new Symbol(sym.COLORTEXT, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "grupo" {return new Symbol(sym.GRUPO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "id" {return new Symbol(sym.ID, new token(yycolumn, yyline, yytext()));}




<YYINITIAL> {Comentario1} { System.out.println("Comentario:\n"+ yytext()); }

<YYINITIAL> {Comentario2} { System.out.println("Comentario:\n"+ yytext()); }


/*_____________________________________________________________________________________________________*/

<YYINITIAL> {Numero} {return new Symbol(sym.NUM, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Decimal} {return new Symbol(sym.DECIMAL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Id} {return new Symbol(sym.I_D, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {cadena} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}




{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.out.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); adderror(yyline,yycolumn,yytext());}



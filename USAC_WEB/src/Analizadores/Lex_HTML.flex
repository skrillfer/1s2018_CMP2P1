
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
	return null;//AnalizadorLexico.listaErrores;
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
//%state COMENTARIO
%state STRING

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*


cadena = [\"] [^(\")]* [\"]

comentario_a = "<//-" ~ "-//>"



%%

/* OPERADORES Y SIGNOS */
/*
<STRING> {
      \"                             { yybegin(YYINITIAL); 
                                       return Symbol(sym.STRING_LITERAL,new token(yycolumn, yyline, string.toString()) 
                                       ); }
      [^\n\r\"\\]+                   { string.append( yytext() ); }
      \\t                            { string.append('\t'); }
      \\n                            { string.append('\n'); }

      \\r                            { string.append('\r'); }
      \\\"                           { string.append('\"'); }
      \\                             { string.append('\\'); }
    }
*/
<YYINITIAL> {cadena} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> "+" {return new Symbol(sym.MAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.MENOS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.POR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.DIV, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<" {return new Symbol(sym.MENQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">" {return new Symbol(sym.MAYQ, new token(yycolumn, yyline, yytext()));} 

<YYINITIAL> ";" {return new Symbol(sym.PYC, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.ALLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.CLLA, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> [>]([^]*)[<] {return new Symbol(sym.MGA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {comentario_a} {}



/* PALABRAS RESERVADAS */
//*************************************ELEMENTOS*************************************************************
<YYINITIAL> "CHTML" {return new Symbol(sym.IHTML, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CHTML" {return new Symbol(sym.FHTML, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "ENCABEZADO" {return new Symbol(sym.IENCABEZADO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-ENCABEZADO" {return new Symbol(sym.FENCABEZADO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CJS" {return new Symbol(sym.ICJS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CJS" {return new Symbol(sym.FCJS, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CCSS" {return new Symbol(sym.ICSS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CCSS" {return new Symbol(sym.FCSS, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "CUERPO" {return new Symbol(sym.ICUERPO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CUERPO" {return new Symbol(sym.FCUERPO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "TITULO" {return new Symbol(sym.ITITULO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-TITULO" {return new Symbol(sym.FTITULO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "PANEL" {return new Symbol(sym.IPANEL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-PANEL" {return new Symbol(sym.FPANEL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "TEXTO" {return new Symbol(sym.ITEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-TEXTO" {return new Symbol(sym.FTEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "IMAGEN" {return new Symbol(sym.IIMAGEN, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-IMAGEN" {return new Symbol(sym.FIMAGEN, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "BOTON" {return new Symbol(sym.IBOTON, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-BOTON" {return new Symbol(sym.FBOTON, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "ENLACE" {return new Symbol(sym.IENLACE, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-ENLACE" {return new Symbol(sym.FENLACE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "TABLA" {return new Symbol(sym.ITABLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-TABLA" {return new Symbol(sym.FTABLA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIL_T" {return new Symbol(sym.IFIL_T, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-FIL_T" {return new Symbol(sym.FFIL_T, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CB" {return new Symbol(sym.ICB, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CB" {return new Symbol(sym.FCB, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CT" {return new Symbol(sym.ICT, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CT" {return new Symbol(sym.FCT, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "TEXTO_A" {return new Symbol(sym.ITEXTO_A, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-TEXTO_A" {return new Symbol(sym.FTEXTO_A, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CAJA_TEXTO" {return new Symbol(sym.ICAJA_TEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CAJA_TEXTO" {return new Symbol(sym.FCAJA_TEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "CAJA" {return new Symbol(sym.ICAJA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CAJA" {return new Symbol(sym.FCAJA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "OPCION" {return new Symbol(sym.IOPCION, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-OPCION" {return new Symbol(sym.FOPCION, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "SPINNER" {return new Symbol(sym.ISPINNER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-SPINNER" {return new Symbol(sym.FSPINNER, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "SALTO-FIN" {return new Symbol(sym.ISALTOF, new token(yycolumn, yyline, yytext()));}

//********************************************************************************************************
//*********************************ATRIBUTOS**************************************************************
<YYINITIAL> "fondo" {return new Symbol(sym.FONDO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ruta" {return new Symbol(sym.RUTA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "click" {return new Symbol(sym.CLICK, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "valor" {return new Symbol(sym.VALOR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Id" {return new Symbol(sym.ID, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Grupo" {return new Symbol(sym.GRUPO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Alto" {return new Symbol(sym.ALTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Ancho" {return new Symbol(sym.ANCHO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "Alineado" {return new Symbol(sym.ALINEADO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ccss" {return new Symbol(sym.CCSS, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "izquierda" {return new Symbol(sym.IZQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "derecha" {return new Symbol(sym.DER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "centrado" {return new Symbol(sym.CENT, new token(yycolumn, yyline, yytext()));}

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

//<YYINITIAL> {String_Literal} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}




{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.out.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); adderror(yyline,yycolumn,yytext());}

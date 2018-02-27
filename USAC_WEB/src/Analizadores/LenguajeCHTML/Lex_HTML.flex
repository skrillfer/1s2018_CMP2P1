
package Analizadores.LenguajeCHTML;
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
%ignorecase
%cup
%state STRING

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?

Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*


cadena = [\"] [^\"]* [\"]



//comentario_a = "<//-" ~ "-//>"

Comentario1 = "<//-" "-"* [^-] ~"-//>" | "<//-" "-"* "-//>"



%%

/* OPERADORES Y SIGNOS */


>([^<]*)< {return new Symbol(sym.EXPLICIT, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {cadena} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> "+" {return new Symbol(sym.MAS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.MENOS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.POR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.DIV, new token(yycolumn, yyline, yytext()));}




<YYINITIAL> ";" {return new Symbol(sym.PYC, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.ALLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.CLLA, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> {Comentario1} { System.out.println("Comentario:\n"+ yytext()); }





//********************************************************************************************************
//*********************************ATRIBUTOS**************************************************************
<YYINITIAL> "fondo" {return new Symbol(sym.FONDO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ruta" {return new Symbol(sym.RUTA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "click" {return new Symbol(sym.CLICK, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "valor" {return new Symbol(sym.VALOR, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "id" {return new Symbol(sym.ID, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "grupo" {return new Symbol(sym.GRUPO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alto" {return new Symbol(sym.ALTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ancho" {return new Symbol(sym.ANCHO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alineado" {return new Symbol(sym.ALINEADO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ccss" {return new Symbol(sym.CCSS, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "izquierda" {return new Symbol(sym.IZQ, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "derecha" {return new Symbol(sym.DER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "centrado" {return new Symbol(sym.CENT, new token(yycolumn, yyline, yytext()));}


/* ETIQUETAS PADRE */

<YYINITIAL> "<CHTML>" {return new Symbol(sym.ICHTML, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-CHTML>" {return new Symbol(sym.FCHTML, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<ENCABEZADO>" {return new Symbol(sym.IENCA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-ENCABEZADO>" {return new Symbol(sym.FENCA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<TITULO" {return new Symbol(sym.ITITULO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-TITULO>" {return new Symbol(sym.FTITULO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<CJS" {return new Symbol(sym.ICJS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CJS>" {return new Symbol(sym.FCJS, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<CCSS" {return new Symbol(sym.ICCSS, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CCSS>" {return new Symbol(sym.FCCSS, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<CUERPO" {return new Symbol(sym.ICUERPO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CUERPO>" {return new Symbol(sym.FCUERPO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-CUERPO>" {return new Symbol(sym.FCUERPO2, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<PANEL" {return new Symbol(sym.IPANEL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "PANEL" {return new Symbol(sym.TPANEL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-PANEL>" {return new Symbol(sym.FPANEL, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-PANEL>" {return new Symbol(sym.FPANEL2, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<TEXTO" {return new Symbol(sym.ITEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "TEXTO" {return new Symbol(sym.TTEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-TEXTO>" {return new Symbol(sym.FTEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<IMAGEN" {return new Symbol(sym.IIMAGEN, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "IMAGEN" {return new Symbol(sym.TIMAGEN, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-IMAGEN>" {return new Symbol(sym.FIMAGEN, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<BOTON" {return new Symbol(sym.IBOTON, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "BOTON" {return new Symbol(sym.TBOTON, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-BOTON>" {return new Symbol(sym.FBOTON, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<ENLACE" {return new Symbol(sym.IENLACE, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ENLACE" {return new Symbol(sym.TENLACE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-ENLACE>" {return new Symbol(sym.FENLACE, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<TABLA" {return new Symbol(sym.ITABLA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "TABLA" {return new Symbol(sym.TTABLA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-TABLA>" {return new Symbol(sym.FTABLA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<FIN-TABLA>" {return new Symbol(sym.FTABLA2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIL_T" {return new Symbol(sym.IFIL_T, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<FIL_T" {return new Symbol(sym.IFIL_T2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-FIL_T>" {return new Symbol(sym.FFIL_T, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-FIL_T>" {return new Symbol(sym.FFIL_T2, new token(yycolumn, yyline, yytext()));}

/*_________________________________________________________________________________________________*/
<YYINITIAL> "CB" {return new Symbol(sym.ICB, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<CB" {return new Symbol(sym.ICB2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-CB>" {return new Symbol(sym.FCB, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-CB>" {return new Symbol(sym.FCB2, new token(yycolumn, yyline, yytext()));}
/*_________________________________________________________________________________________________*/
<YYINITIAL> "CT" {return new Symbol(sym.ICT, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<CT" {return new Symbol(sym.ICT2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-CT>" {return new Symbol(sym.FCT, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-CT>" {return new Symbol(sym.FCT2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<TEXTO_A" {return new Symbol(sym.ITEXTO_A, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "TEXTO_A" {return new Symbol(sym.TTEXTO_A, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-TEXTO_A>" {return new Symbol(sym.FTEXTO_A, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<CAJA_TEXTO" {return new Symbol(sym.ICAJA_TEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "CAJA_TEXTO" {return new Symbol(sym.TCAJA_TEXTO, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "FIN-CAJA_TEXTO>" {return new Symbol(sym.FCAJA_TEXTO, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<CAJA" {return new Symbol(sym.ICAJA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "CAJA" {return new Symbol(sym.TCAJA, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-CAJA>" {return new Symbol(sym.FCAJA, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<FIN-CAJA>" {return new Symbol(sym.FCAJA2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "OPCION" {return new Symbol(sym.IOPCION, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<OPCION" {return new Symbol(sym.IOPCION2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-OPCION>" {return new Symbol(sym.FOPCION, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<SPINNER" {return new Symbol(sym.ISPINNER, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "SPINNER" {return new Symbol(sym.TSPINNER, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "FIN-SPINNER>" {return new Symbol(sym.FSPINNER, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<SALTO-FIN>" {return new Symbol(sym.ISALTOF, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "SALTO-FIN>" {return new Symbol(sym.TSALTOF, new token(yycolumn, yyline, yytext()));}

/*_____________________________________________________________________________________________________*/

<YYINITIAL> {Numero} {return new Symbol(sym.NUM, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Decimal} {return new Symbol(sym.DECIMAL, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Id} {return new Symbol(sym.I_D, new token(yycolumn, yyline, yytext()));}

//<YYINITIAL> {String_Literal} {return new Symbol(sym.STRING_LITERAL, new token(yycolumn, yyline, yytext()));}




{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.out.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); adderror(yyline,yycolumn,yytext());}


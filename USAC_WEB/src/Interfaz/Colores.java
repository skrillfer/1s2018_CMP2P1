/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.Color;
import java.util.Hashtable;

/**
 *
 * @author fernando
 */
public class Colores {

    Hashtable<String, RGB> colores = new Hashtable<>();

    public Colores() {
        colores.put("aliceblue", new RGB(240, 248, 255));
        colores.put("antiquewhite", new RGB(250, 235, 215));
        colores.put("aqua", new RGB(0, 255, 255));
        colores.put("aquamarine", new RGB(127, 255, 212));
        colores.put("azure", new RGB(240, 255, 255));
        colores.put("beige", new RGB(245, 245, 220));
        colores.put("bisque", new RGB(255, 228, 196));
        colores.put("black", new RGB(0, 0, 0));
        colores.put("blanchedalmond", new RGB(255, 235, 205));
        colores.put("blue", new RGB(0, 0, 255));
        colores.put("blueviolet", new RGB(138, 43, 226));
        colores.put("brown", new RGB(165, 42, 42));
        colores.put("burlywood", new RGB(222, 184, 135));
        colores.put("cadetblue", new RGB(95, 158, 160));
        colores.put("chartreuse", new RGB(127, 255, 0));
        colores.put("chocolate", new RGB(210, 105, 30));
        colores.put("coral", new RGB(255, 127, 80));
        colores.put("cornflowerblue", new RGB(100, 149, 237));
        colores.put("cornsilk", new RGB(255, 248, 220));
        colores.put("crimson", new RGB(220, 20, 60));
        colores.put("cyan", new RGB(0, 255, 255));
        colores.put("darkblue", new RGB(0, 0, 139));
        colores.put("darkcyan", new RGB(0, 139, 139));
        colores.put("darkgoldenrod", new RGB(184, 134, 11));
        colores.put("darkgray", new RGB(169, 169, 169));
        colores.put("darkgreen", new RGB(0, 100, 0));
        colores.put("darkgrey", new RGB(169, 169, 169));
        colores.put("darkkhaki", new RGB(189, 183, 107));
        colores.put("darkmagenta", new RGB(139, 0, 139));
        colores.put("darkolivegreen", new RGB(85, 107, 47));
        colores.put("darkorange", new RGB(255, 140, 0));
        colores.put("darkorchid", new RGB(153, 50, 204));
        colores.put("darkred", new RGB(139, 0, 0));
        colores.put("darksalmon", new RGB(233, 150, 122));
        colores.put("darkseagreen", new RGB(143, 188, 143));
        colores.put("darkslateblue", new RGB(72, 61, 139));
        colores.put("darkslategray", new RGB(47, 79, 79));
        colores.put("darkslategrey", new RGB(47, 79, 79));
        colores.put("darkturquoise", new RGB(0, 206, 209));
        colores.put("darkviolet", new RGB(148, 0, 211));
        colores.put("deeppink", new RGB(255, 20, 147));
        colores.put("deepskyblue", new RGB(0, 191, 255));
        colores.put("dimgray", new RGB(105, 105, 105));
        colores.put("dimgrey", new RGB(105, 105, 105));
        colores.put("dodgerblue", new RGB(30, 144, 255));
        colores.put("firebrick", new RGB(178, 34, 34));
        colores.put("floralwhite", new RGB(255, 250, 240));
        colores.put("forestgreen", new RGB(34, 139, 34));
        colores.put("fuchsia", new RGB(255, 0, 255));
        colores.put("gainsboro", new RGB(220, 220, 220));
        colores.put("ghostwhite", new RGB(248, 248, 255));
        colores.put("gold", new RGB(255, 215, 0));
        colores.put("goldenrod", new RGB(218, 165, 32));
        colores.put("gray", new RGB(128, 128, 128));
        colores.put("green", new RGB(0, 128, 0));
        colores.put("greenyellow", new RGB(173, 255, 47));
        colores.put("grey", new RGB(128, 128, 128));
        colores.put("honeydew", new RGB(240, 255, 240));
        colores.put("hotpink", new RGB(255, 105, 180));
        colores.put("indianred", new RGB(205, 92, 92));
        colores.put("indigo", new RGB(75, 0, 130));
        colores.put("ivory", new RGB(255, 255, 240));
        colores.put("khaki", new RGB(240, 230, 140));
        colores.put("lavender", new RGB(230, 230, 250));
        colores.put("lavenderblush", new RGB(255, 240, 245));
        colores.put("lawngreen", new RGB(124, 252, 0));
        colores.put("lemonchiffon", new RGB(255, 250, 205));
        colores.put("lightblue", new RGB(173, 216, 230));
        colores.put("lightcoral", new RGB(240, 128, 128));
        colores.put("lightcyan", new RGB(224, 255, 255));
        colores.put("lightgoldenrodyellow", new RGB(250, 250, 210));
        colores.put("lightgray", new RGB(211, 211, 211));
        colores.put("lightgreen", new RGB(144, 238, 144));
        colores.put("lightgrey", new RGB(211, 211, 211));
        colores.put("lightpink", new RGB(255, 182, 193));
        colores.put("lightsalmon", new RGB(255, 160, 122));
        colores.put("lightseagreen", new RGB(32, 178, 170));
        colores.put("lightskyblue", new RGB(135, 206, 250));
        colores.put("lightslategray", new RGB(119, 136, 153));
        colores.put("lightslategrey", new RGB(119, 136, 153));
        colores.put("lightsteelblue", new RGB(176, 196, 222));
        colores.put("lightyellow", new RGB(255, 255, 224));
        colores.put("lime", new RGB(0, 255, 0));
        colores.put("limegreen", new RGB(50, 205, 50));
        colores.put("linen", new RGB(250, 240, 230));
        colores.put("magenta", new RGB(255, 0, 255));
        colores.put("maroon", new RGB(128, 0, 0));
        colores.put("mediumaquamarine", new RGB(102, 205, 170));
        colores.put("mediumblue", new RGB(0, 0, 205));
        colores.put("mediumorchid", new RGB(186, 85, 211));
        colores.put("mediumpurple", new RGB(147, 112, 219));
        colores.put("mediumseagreen", new RGB(60, 179, 113));
        colores.put("mediumslateblue", new RGB(123, 104, 238));
        colores.put("mediumspringgreen", new RGB(0, 250, 154));
        colores.put("mediumturquoise", new RGB(72, 209, 204));
        colores.put("mediumvioletred", new RGB(199, 21, 133));
        colores.put("midnightblue", new RGB(25, 25, 112));
        colores.put("mintcream", new RGB(245, 255, 250));
        colores.put("mistyrose", new RGB(255, 228, 225));
        colores.put("moccasin", new RGB(255, 228, 181));
        colores.put("navajowhite", new RGB(255, 222, 173));
        colores.put("navy", new RGB(0, 0, 128));
        colores.put("oldlace", new RGB(253, 245, 230));
        colores.put("olive", new RGB(128, 128, 0));
        colores.put("olivedrab", new RGB(107, 142, 35));
        colores.put("orange", new RGB(255, 165, 0));
        colores.put("orangered", new RGB(255, 69, 0));
        colores.put("orchid", new RGB(218, 112, 214));
        colores.put("palegoldenrod", new RGB(238, 232, 170));
        colores.put("palegreen", new RGB(152, 251, 152));
        colores.put("paleturquoise", new RGB(175, 238, 238));
        colores.put("palevioletred", new RGB(219, 112, 147));
        colores.put("papayawhip", new RGB(255, 239, 213));
        colores.put("peachpuff", new RGB(255, 218, 185));
        colores.put("peru", new RGB(205, 133, 63));
        colores.put("pink", new RGB(255, 192, 203));
        colores.put("plum", new RGB(221, 160, 221));
        colores.put("powderblue", new RGB(176, 224, 230));
        colores.put("purple", new RGB(128, 0, 128));
        colores.put("red", new RGB(255, 0, 0));
        colores.put("rosybrown", new RGB(188, 143, 143));
        colores.put("royalblue", new RGB(65, 105, 225));
        colores.put("saddlebrown", new RGB(139, 69, 19));
        colores.put("salmon", new RGB(250, 128, 114));
        colores.put("sandybrown", new RGB(244, 164, 96));
        colores.put("seagreen", new RGB(46, 139, 87));
        colores.put("seashell", new RGB(255, 245, 238));
        colores.put("sienna", new RGB(160, 82, 45));
        colores.put("silver", new RGB(192, 192, 192));
        colores.put("skyblue", new RGB(135, 206, 235));
        colores.put("slateblue", new RGB(106, 90, 205));
        colores.put("slategray", new RGB(112, 128, 144));
        colores.put("slategrey", new RGB(112, 128, 144));
        colores.put("snow", new RGB(255, 250, 250));
        colores.put("springgreen", new RGB(0, 255, 127));
        colores.put("steelblue", new RGB(70, 130, 180));
        colores.put("tan", new RGB(210, 180, 140));
        colores.put("teal", new RGB(0, 128, 128));
        colores.put("thistle", new RGB(216, 191, 216));
        colores.put("tomato", new RGB(255, 99, 71));
        colores.put("turquoise", new RGB(64, 224, 208));
        colores.put("violet", new RGB(238, 130, 238));
        colores.put("wheat", new RGB(245, 222, 179));
        colores.put("white", new RGB(255, 255, 255));
        colores.put("whitesmoke", new RGB(245, 245, 245));
        colores.put("yellow", new RGB(255, 255, 0));
        colores.put("yellowgreen", new RGB(154, 205, 50));

    }

    public Color obtenerColor(String nombre) {
        nombre=nombre.toLowerCase();
        Color color = null;
        
        if(colores.containsKey(nombre.trim())){
            RGB rgb=colores.get(nombre.trim());
            color = new Color(rgb.r, rgb.g ,rgb.b);
        }else{
            try {
                color= new Color(
                    Integer.valueOf( nombre.substring( 1, 3 ), 16 ),
                    Integer.valueOf( nombre.substring( 3, 5 ), 16 ),
                    Integer.valueOf( nombre.substring( 5, 7 ), 16 ) );
            } catch (Exception e) {}
        }
        return color;
    }
}

class RGB {

    public int r;
    public int g;
    public int b;

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

}

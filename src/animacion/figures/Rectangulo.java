/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * Autor: Luis Cobian
 * Registro: 20310016
 */
public class Rectangulo extends Figure{
    public Rectangulo(Point startPoint, Point endPoint, BufferedImage buffer, Color color) {
        super(startPoint, endPoint, buffer, color);
    }
    public Rectangulo(Point startPoint, Point endPoint, BufferedImage buffer, Color color, boolean steps) {
        super(startPoint, endPoint, buffer, color, steps);
    }

    public Rectangulo(Point startPoint, Point endPoint, BufferedImage buffer, Color color, int ang) {
        super(startPoint, endPoint, buffer, color, ang);
    }
    

    @Override
    public void draw() {
        drawRec(startPoint, endPoint);
        new FloodFill(getFloodPoint(), color, buffer);
    }
    
    public void drawRec(Point startPoint, Point endPoint){
        for (int i = startPoint.x; i <= endPoint.x; i++) {
            putPixel(i, startPoint.y, color);
            putPixel(i, endPoint.y, color);
        }
        
        for (int i = startPoint.y; i <= endPoint.y; i++) {
            putPixel(startPoint.x, i, color);
            putPixel(endPoint.x, i, color);
        }
    }

}

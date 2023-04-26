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

    public Rectangulo(Point startPoint, Point endPoint, BufferedImage buffer) {
        super(startPoint, endPoint, buffer);
    }

    @Override
    public void draw() {
        drawRec(startPoint, endPoint);
        new FloodFill(new Point((endPoint.x + startPoint.x)/2, (endPoint.y + startPoint.y)/2), Color.orange, buffer);
    }
    
    public void drawRec(Point startPoint, Point endPoint){
        for (int i = startPoint.x; i <= endPoint.x; i++) {
            putPixel(i, startPoint.y, Color.orange);
            putPixel(i, endPoint.y, Color.orange);
        }
        
        for (int i = startPoint.y; i <= endPoint.y; i++) {
            putPixel(startPoint.x, i, Color.orange);
            putPixel(endPoint.x, i, Color.orange);
        }
    }

}

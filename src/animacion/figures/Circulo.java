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
public class Circulo extends Figure{

    public Circulo(Point startPoint, Point endPoint, BufferedImage buffer, Color color) {
        super(startPoint, endPoint, buffer, color);
    }

    @Override
    public void draw() {
        drawEllipse(startPoint,endPoint.x - startPoint.x,endPoint.y - startPoint.y);
        new FloodFill(startPoint, Color.green, buffer);
    }
    
    
    public void drawEllipse(Point centerPoint, int radioX, int radioY){
        double x,y;
        for (double i = 0; i <= 2 * Math.PI; i+=0.01) {
            x = centerPoint.x + radioX*Math.cos(i);
            y = centerPoint.y + radioY*Math.sin(i);
            putPixel((int) x, (int) y, Color.GREEN);
        }
    }
}

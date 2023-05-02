package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Persona extends Figure {
    Color secondColor;
    public Persona(Point startPoint, Point endPoint, BufferedImage buffer, Color color) {
        super(startPoint, endPoint, buffer, color);
        secondColor = color;
    }

    @Override
    public void draw() {

        color = Color.decode("#F0D8AE");
        drawRec(startPoint,new Point(endPoint.x, (int) ((startPoint.y + endPoint.y)/2.05)));
        new FloodFill(new Point(startPoint.x + 1,startPoint.y + 1),color,buffer);
        color = secondColor;
        drawRec(new Point(startPoint.x, (int) ((startPoint.y + endPoint.y)/2.05)), endPoint);
        new FloodFill(new Point(endPoint.x - 1,endPoint.y - 1),color,buffer);
    }
}

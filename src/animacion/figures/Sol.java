package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sol extends Figure{
    public Sol(Point startPoint, BufferedImage buffer) {
        super(startPoint);
        this.buffer = buffer;
    }

    @Override
    public void draw() {
        color = Color.decode("#FFF038");
        drawSol(startPoint);
        new FloodFill(startPoint,color,buffer);
    }
}

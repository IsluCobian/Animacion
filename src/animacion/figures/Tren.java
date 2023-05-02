package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tren extends Figure{
    public Tren(Point startPoint, BufferedImage buffer) {
        super(startPoint);
        bufferMain = buffer;
    }

    @Override
    public void draw() {
        if (buffer == null){
            buffer = new BufferedImage(150, 90, BufferedImage.TYPE_INT_ARGB);
            //Punta Locomotora
            color = Color.decode("#A84747");
            drawRec(new Point(68,40),new Point(130,80));
            new FloodFill(new Point(80, 50), color, buffer);
            //Domos
            drawRec(new Point(80,36),new Point(88,40));
            new FloodFill(new Point(84, 38), color, buffer);
            drawRec(new Point(96,36),new Point(104,40));
            new FloodFill(new Point(100, 38), color, buffer);
            //Caja de Humo
            color = Color.darkGray;
            drawRec(new Point(130,40),new Point(148,80));
            new FloodFill(new Point(144, 50), color, buffer);
            //chimenea
            drawRec(new Point(136,30),new Point(144,40));
            new FloodFill(new Point(140, 36), color, buffer);
            //Humo
            drawHumo(new Point(140,30));
            //Caja Persona
            color = Color.decode("#2E2E2E");
            drawRec(new Point(20,0), new Point(68,80));
            new FloodFill(new Point(44, 24), color, buffer);
            //Cola Locomotora
            drawRec(new Point(8,64), new Point(20,76));
            new FloodFill(new Point(10, 70), color, buffer);
            drawRec(new Point(0,60), new Point(8,80));
            new FloodFill(new Point(2, 70), color, buffer);
            //Ventana
            color = Color.decode("#DACB5D");
            drawRec(new Point(32,12), new Point(56,36));
            new FloodFill(new Point(34, 14), color, buffer);
            //ruedas
            color = Color.darkGray;
            drawCircle(new Point(44,70),20);
            drawCircle(new Point(84,74),16);
            drawCircle(new Point(120,74),16);
            new FloodFill(new Point(44,70), color, buffer);
            new FloodFill(new Point(76,74), color, buffer);
            new FloodFill(new Point(120,74), color, buffer);
            new FloodFill(new Point(40,84), color, buffer);
            new FloodFill(new Point(76,84), color, buffer);
            new FloodFill(new Point(120,84), color, buffer);
            color = Color.gray;
            drawCircle(new Point(44,70),14);
            drawCircle(new Point(84,74),10);
            drawCircle(new Point(120,74),10);
            new FloodFill(new Point(44,70), color, buffer);
            new FloodFill(new Point(76,74), color, buffer);
            new FloodFill(new Point(120,74), color, buffer);
        }
        bufferMain.getGraphics().drawImage(buffer,startPoint.x,startPoint.y,null);
    }
}

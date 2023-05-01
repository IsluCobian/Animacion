package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Vagon extends Figure{
    private BufferedImage bufferMain;
    public Vagon(Point startPoint, BufferedImage buffer) {
        super(startPoint);
        bufferMain = buffer;
    }

    @Override
    public void draw() {
        if (buffer == null){
            buffer = new BufferedImage(120, 90, BufferedImage.TYPE_INT_ARGB);
            //Cola Vagon
            color = Color.decode("#2E2E2E");
            drawRec(new Point(8,64), new Point(20,76));
            new FloodFill(new Point(10, 70), color, buffer);
            drawRec(new Point(0,60), new Point(8,80));
            new FloodFill(new Point(2, 70), color, buffer);
            //Punta Vagon
            drawRec(new Point(100,64), new Point(112,76));
            new FloodFill(new Point(106, 70), color, buffer);
            drawRec(new Point(112,60), new Point(120,80));
            new FloodFill(new Point(116, 70), color, buffer);
            //Cuerpo Vagon
            drawRec(new Point(20,40), new Point(100,80));
            new FloodFill(new Point(50, 70), color, buffer);
            //Refuerzo Cuerpo Vagon
            color = Color.gray;
            drawRec(new Point(20,44), new Point(100,46));
            new FloodFill(new Point(50, 45), color, buffer);
            //Carga
            Point[] points = new Point[]{new Point(24,39), new Point(40,30), new Point(50,34), new Point(70,24), new Point(90,36), new Point(96,39)};
            new Polygon(points,buffer,Color.black).draw();
            new FloodFill(new Point(60, 36), Color.black, buffer);
            //ruedas
            for (int i = 0; i < 60; i+=24){
                color = Color.darkGray;
                drawCircle(new Point(36 + i,80),10);
                new FloodFill(new Point(36 + i,72), color, buffer);
                new FloodFill(new Point(36 + i,88), color, buffer);
                color = Color.gray;
                drawCircle(new Point(36 + i,80),4);
                new FloodFill(new Point(36 + i,80), color, buffer);
            }
        }
        bufferMain.getGraphics().drawImage(buffer,startPoint.x,startPoint.y,null);
    }
}

package animacion.figures;

import animacion.FloodFill;
import strdatos.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Nube extends Figure{
    private BufferedImage bufferMain;
    public Nube(Point startPoint) {
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

        }
        bufferMain.getGraphics().drawImage(buffer,startPoint.x,startPoint.y,null);
    }
}

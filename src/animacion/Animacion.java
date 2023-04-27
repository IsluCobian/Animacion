package animacion;

import strdatos.Point;
import animacion.figures.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Autor: Luis Cobian Registro: 20310016
 */

public class Animacion extends JFrame {

    private static BufferedImage buffer;
    public BufferedImage bufferSec;
    private LinkedList<Figure> figures = new LinkedList<Figure>();
    Rectangulo Rec01, Rec02;
    
    private Graphics2D graphics;

    public Animacion() {
        setTitle("Practica");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        bufferSec = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Rec01 = new Rectangulo(new Point(150, 150),new Point(200,200), bufferSec);
        Rec01.setTimes(0, 10);
        Rec01.setTraslation(300, 0);
        //Rec02 = new Rectangulo(new Point(50, 100),new Point(70,130), bufferSec);
        
        figures.add(Rec01);
        //figures.add(Rec02);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (buffer == null) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = (Graphics2D) buffer.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(Color.red);
            //new Rectangulo(new Point(300,400),new Point(350,450),buffer,50).draw();
            //new Transforms(figuras,dx,dy,sx,sy,ang)
            Thread hilo = new Thread(new Transforms(figures));
            hilo.start();    
        }
        this.getGraphics().drawImage(bufferSec, 0, 0, this);
    }

    public static void main(String[] args) {
        new Animacion();   
    }
    
    public int[] remove(int[] array, int indice) {
        int[] nuevoArray = new int[array.length - 1];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != indice) {
                nuevoArray[j] = array[i];
                j++;
            }
        }
        return nuevoArray;
    }
    
    public double[] remove(double[] array, double indice) {
        double[] nuevoArray = new double[array.length - 1];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != indice) {
                nuevoArray[j] = array[i];
                j++;
            }
        }
        return nuevoArray;
    }
    
    public class Transforms implements Runnable {

        LinkedList<Figure> animate;
        int DELAY = 50;
        Figure figura;
        int[] tiempos;
        int[] dx, dy, ang;
        double[] sx, sy;
        int allInBuffer;

        public Transforms(LinkedList<Figure> animate) {
            this.animate = animate;
        }

        public Transforms(LinkedList<Figure> animate, int[] dx, int[] dy) {
            this.animate = animate;
            this.dx = dx;
            this.dy = dy;
            allInBuffer = 0;
            this.tiempos = new int[]{3, 1, 3, 5};
            this.sx = new double[]{1.03, 1, 1, 1};
            this.sy = new double[]{1.03, 1, 1, 1};
            this.ang = new int[]{0, 720, 0, 820};
        }

        public void transform(int i) {
            int tiempo = (tiempos[i] * 1000 )/ DELAY;
            figura.translate(dx[i] / tiempo, dy[i] / tiempo);
            if (figura.getScaleX() <= sx[i] * 20 || figura.getScaleY() <= sy[i] * 20) {
                figura.scalate(sx[i], sy[i]);
            }
            if (figura.getAng() <= ang[i]) {
                figura.rotate(ang[i] / tiempo);
            }
        }

        public boolean isComplete(int i) {
            if (dx[i] == 0 && dy[i] == 0 && figura.getAng() >= ang[i] && figura.getScaleX() >= sx[i] * 20 && figura.getScaleY() >= sy[i] * 20 && figura.getBuffer() != buffer) {
                return true;
            }

            if (figura.getTX() >= dx[i] && figura.getTY() >= dy[i]) {
                dx[i] = 0;
                dy[i] = 0;
            }

            return false;
        }

        public void removeAnimation(int i) {
//            animate.remove(figura);
//            dx = remove(dx, i);
//            dy = remove(dy, i);
//            ang = remove(ang, i);
//            sx = remove(sx, i);
//            sy = remove(sy, i);
            allInBuffer+=1;
            figura.setBuffer(buffer);
            figura.draw();
        }
        

        @Override
        public void run() {
            int TIME = 0;
            while (animate.size() != allInBuffer) {
                bufferSec.getGraphics().drawImage(buffer, 0, 0, null);
                for (int i = 0; i < animate.size(); i++) {
                    figura = animate.get(i);
                    if (figura.getBuffer() != buffer) {
                        transform(i);
                        figura.draw();
                    }
                    if (isComplete(i)) {
                        removeAnimation(i);
                    }
                }
                repaint();
                try {
                    TIME += DELAY;
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}


}

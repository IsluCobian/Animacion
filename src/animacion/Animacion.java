package animacion;

import strdatos.Point;
import animacion.figures.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 * Autor: Luis Cobian Registro: 20310016
 */

public class Animacion extends JFrame {

    private static BufferedImage buffer;
    public BufferedImage bufferSec;
    private LinkedList<Figure> figures = new LinkedList<Figure>();
    public int TIME = 0;
    Rectangulo Rec01, Rec02;
    Thread thread;
    private Graphics2D graphics;

    public Animacion() {
        setTitle("Practica");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        bufferSec = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Rec01 = new Rectangulo(new Point(150, 150),new Point(200,200), bufferSec, Color.orange, true);
        Rec01.setTimes(3, 7);
        Rec01.setTraslation(400, 0);
        figures.add(Rec01);
        Rec02 = new Rectangulo(new Point(300, 100),new Point(320,130), bufferSec, Color.PINK);
        Rec02.setTimes(0, 5);
        Rec02.setTraslation(0, 200);
        Rec02.setAng(720);
        Rec02.setRotationPoint(new Point(50,50));
        //Rec02.setScalation(2,2);
        figures.add(Rec02);
        Rec01 = new Rectangulo(new Point(550, 150),new Point(600,200), bufferSec, Color.orange, true);
        Rec01.setTimes(7, 10);
        Rec01.setTraslation(0, 200);
        figures.add(Rec01);
        Rec01 = new Rectangulo(new Point(550, 350),new Point(600,400), bufferSec, Color.orange);
        Rec01.setTimes(10, 14);
        Rec01.setTraslation(-400, 0);
        figures.add(Rec01);

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
            Point[] points = new Point[]{new Point(100,100), new Point(200,120), new Point(180,200), new Point(100,200), new Point(50,150)};
            new Polygon(points,buffer,Color.orange).draw();
            thread = new Thread(new Transforms(figures));
            thread.start();
        }
        this.getGraphics().drawImage(bufferSec, 0, 0, this);
    }

    public static void main(String[] args) {
        new Animacion();   
    }
    
    public class Transforms implements Runnable {
        LinkedList<Figure> animate;
        int DELAY = 50;
        Figure figure;
        int c = 0;

        public Transforms(LinkedList<Figure> animate) {
            this.animate = animate;
        }

        public void transform(int i) {
            int tiempo = ( figure.getDuracion() * 1000 )/ DELAY;
            if (figure.translate()){
                figure.translate(tiempo);
            }
            if (figure.scale()){
                figure.scalate(tiempo);
            }
            if (figure.rotate()){
                figure.rotate(tiempo);
            }
        }

        public boolean isComplete() {
            if (figure.getFinishTime() <= (TIME / 1000)) {
                return true;
            }
            return false;
        }

        public boolean startTime(){
            if (figure.getStartTime()*1000 <= TIME) {
                return true;
            }
            return false;
        }

        public void removeAnimation(int i) {
            animate.remove(figure);
            if (!figure.isSteps()) {
                figure.setBuffer(buffer);
                figure.draw();
            }
        }

        @Override
        public void run() {
                while (true) {
                    if (!animate.isEmpty()) {
                        bufferSec.getGraphics().drawImage(buffer, 0, 0, null);
                        for (int i = 0; i < animate.size(); i++) {
                            figure = animate.get(i);
                            if (startTime()) {
                                figure.draw();
                                transform(i);
                                if (isComplete()) {
                                    removeAnimation(i);
                                }
                            }
                        }
                        repaint();
                        try {
                            if (c == (1000 / DELAY)) {
                                System.out.println("" + TIME / 1000);
                                c = 0;
                            }
                            c++;
                            TIME += DELAY;
                            Thread.sleep(DELAY);
                        } catch (InterruptedException e) {e.printStackTrace();}
                    }
                }
        }

}


}

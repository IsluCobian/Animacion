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
        figures.add(Rec01);
        Rec02 = new Rectangulo(new Point(200, 100),new Point(220,130), bufferSec);
        Rec02.setTimes(0, 5);
        Rec02.setTraslation(0, 200);
        Rec02.setAng(720);
        //Rec02.setScalation(2,2);
        figures.add(Rec02);

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
    
    public class Transforms implements Runnable {

        LinkedList<Figure> animate;
        int DELAY = 50;
        Figure figura;
        int TIME = 0;
        int c =0;

        public Transforms(LinkedList<Figure> animate) {
            this.animate = animate;
        }

        public void transform(int i) {
            int tiempo = ( figura.getFinishTime() * 1000 )/ DELAY;
            if (figura.translate()){
                figura.translate(tiempo);
            }
            if (figura.scale()){
                figura.scalate(tiempo);
            }
            if (figura.rotate()){
                figura.rotate(tiempo);
            }

        }

        public boolean isComplete(int i) {
            if (figura.getFinishTime() <= (TIME / 1000)) {
                return true;
            }
            return false;
        }

        public boolean startTime(int i){
            if (figura.getStartTime() >= (TIME / 1000)) {
                return true;
            }
            return false;
        }

        public void removeAnimation(int i) {
            animate.remove(figura);
            figura.setBuffer(buffer);
            figura.draw();
        }
        

        @Override
        public void run() {
            while (!animate.isEmpty()) {
                bufferSec.getGraphics().drawImage(buffer, 0, 0, null);
                for (int i = 0; i < animate.size(); i++) {
                    figura = animate.get(i);
                    //if (startTime(i)){
                        transform(i);
                        figura.draw();
                        if (isComplete(i)){
                            removeAnimation(i);
                        }
                    //}
                }
                repaint();
                try {
                    if (c == 20){
                        System.out.println("" + TIME/1000);
                        c = 0;
                    }
                    c++;
                    TIME += DELAY;
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}


}

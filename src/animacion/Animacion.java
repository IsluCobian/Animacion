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
    private BufferedImage[] escenarios;
    private LinkedList<Figure> figures = new LinkedList<Figure>();
    public int TIME = 0;
    Rectangulo Rect;
    Thread thread;
    private Graphics2D graphics;

    public Animacion() {
        setTitle("Practica");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        bufferSec = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Rect = new Rectangulo(new Point(700, 0),new Point(1400,500), bufferSec, Color.orange);
        Rect.setTimes(13, 18);
        Rect.setTraslation(-1400,0);
        figures.add(Rect);

        Tren tren = new Tren(new Point(-100,354),bufferSec);
        tren.setTimes(0,15);
        tren.setTraslation(1800,0);
        figures.add(tren);


        Vagon vagon = new Vagon(new Point(-222,354),bufferSec);
        vagon.setTimes(0,15);
        vagon.setTraslation(1800,0);
        for (int i = 122; i <= (122)*6 ; i+=122){
            figures.add(vagon);
            vagon = new Vagon(new Point(-222 - i,354),bufferSec);
            vagon.setTimes(0,15);
            vagon.setTraslation(1800,0);
        }

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (buffer == null) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = (Graphics2D) buffer.createGraphics();
            new Rectangulo(new Point(0,0), new Point(750,500),buffer,Color.decode("#61C9E5")).draw();
            new Rectangulo(new Point(0,450), new Point(750,500),buffer,Color.decode("#91E561")).draw();
            for (int i = 0; i < 750; i+=45){
                new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),buffer,Color.decode("#9E8054")).draw();
            }
            new Rectangulo(new Point(0,444), new Point(750,449),buffer,Color.decode("#B5B5B5")).draw();
//            new Tren(new Point(100,354),buffer).draw();
//            new Vagon(new Point(-20,354),buffer).draw();
            thread = new Thread(new Transforms(figures));
//            thread.start();
//            this.getGraphics().drawImage(buffer, 0, 0, this);
        }
        this.getGraphics().drawImage(bufferSec, 0, 0, this);
    }

    private void loadScenes(){

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

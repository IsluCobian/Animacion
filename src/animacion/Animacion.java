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
    private LinkedList<BufferedImage>  escenarios = new LinkedList<BufferedImage>();
    private LinkedList<Figure> figures = new LinkedList<Figure>();
    public int TIME = 0;
    Rectangulo Rect;
    Thread thread;
    private Graphics2D graphics;
    int condicional = 0;

    public Animacion() {
        setTitle("Practica");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Scenes Loader
        for (int i = 0; i < 5; i++) {
            loadScene(i);
        }
        
        bufferSec = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        //Animacion escena 01
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

        Tunel tunel = new Tunel(new Point(740,0),bufferSec);
        tunel.setTimes(12,20);
        tunel.setTraslation(-1620,0);
        figures.add(tunel);

        //Animacion escena 02
        tren = new Tren(new Point(-150,354),bufferSec);
        tren.setTimes(19.7,35);
        tren.setTraslation(1800,0);
        tren.setSteps(true);
        figures.add(tren);

        vagon = new Vagon(new Point(-272,354),bufferSec);
        vagon.setTimes(19.7,35);
        vagon.setTraslation(1800,0);
        vagon.setSteps(true);
        for (int i = 122; i <= (122)*6 ; i+=122){
            figures.add(vagon);
            vagon = new Vagon(new Point(-272 - i,354),bufferSec);
            vagon.setTimes(19.7,35);
            vagon.setTraslation(1800,0);
            vagon.setSteps(true);
        }

        tunel = new Tunel(new Point(720,0),bufferSec);
        tunel.setTimes(32,40);
        tunel.setTraslation(-1600,0);
        tunel.setSteps(true);
        figures.add(tunel);

        //Animacion escena 03
        tren = new Tren(new Point(-150,354),bufferSec);
        tren.setTimes(39.7,55);
        tren.setTraslation(1800,0);
        tren.setSteps(true);
        figures.add(tren);

        vagon = new Vagon(new Point(-272,354),bufferSec);
        vagon.setTimes(39.7,55);
        vagon.setTraslation(1800,0);
        vagon.setSteps(true);
        for (int i = 122; i <= (122)*4 ; i+=122){
            figures.add(vagon);
            vagon = new Vagon(new Point(-272 - i,354),bufferSec);
            vagon.setTimes(39.7,55);
            vagon.setTraslation(1800,0);
            vagon.setSteps(true);
        }

        Rect = new Rectangulo(new Point(720,100), new Point(722,102),bufferSec,Color.black);
        Rect.setTimes(44,47.5);
        Rect.setAng(1080);
        Rect.setTraslation(-270,300);
        Rect.setScalation(1.0001,1.0001);
        Rect.setSteps(true);
        figures.add(Rect);

        tunel = new Tunel(new Point(720,0),bufferSec);
        tunel.setTimes(52,60);
        tunel.setTraslation(-1600,0);
        tunel.setSteps(true);
        figures.add(tunel);

        //Animacion escena 04
        Circulo circulo = new Circulo(new Point(540,315),new Point(545,320),bufferSec,Color.decode("#77624C"));
        circulo.setTimes(65,67);
        circulo.setTraslation(80,150);
        circulo.setScalation(1.000001,1.000001);
        circulo.setSteps(true);
        figures.add(circulo);

        tren = new Tren(new Point(-150,354),bufferSec);
        tren.setTimes(59.7,75);
        tren.setTraslation(1800,0);
        tren.setSteps(true);
        figures.add(tren);


        vagon = new Vagon(new Point(-272,354),bufferSec);
        vagon.setTimes(59.7,75);
        vagon.setTraslation(1800,0);
        vagon.setSteps(true);
        for (int i = 122; i <= (122)*2 ; i+=122){
            figures.add(vagon);
            vagon = new Vagon(new Point(-272 - i,354),bufferSec);
            vagon.setTimes(59.7,75);
            vagon.setTraslation(1800,0);
            vagon.setSteps(true);
        }

        tunel = new Tunel(new Point(720,0),bufferSec);
        tunel.setTimes(70,80);
        tunel.setTraslation(-1600,0);
        tunel.setSteps(true);
        figures.add(tunel);

        //Animacion escena 05
        tren = new Tren(new Point(-150,354),bufferSec);
        tren.setTimes(79.7,85);
        tren.setTraslation(600,0);
        figures.add(tren);

        Persona persona= new Persona(new Point(510,390),new Point(520,449),bufferSec,Color.decode("#7DA1FB"));
        persona.setTimes(87,92);
        persona.setTraslation(190,0);
        figures.add(persona);


        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (buffer == null) {
            buffer = escenarios.get(0);
            thread = new Thread(new Transforms(figures));
            thread.start();
        }
        //Escena 02
        if (TIME == 15700){
            buffer = escenarios.get(1);
        }
        if (TIME >= 30000 && TIME <= 30500){
            Point[] points = new Point[]{new Point(410,200), new Point(400,250), new Point(420,300), new Point(390,450), new Point(440,310), new Point(420,250) , new Point(430,200)};
            new Polygon(points,bufferSec,Color.orange).draw();
            new FloodFill(new Point(420,210), Color.orange,bufferSec);
            if (condicional == 0){
                for (int i = 0; i < 2; i++) {
                    Figure figure = figures.get(5);
                    figures.remove(figure);
                    figure.setBufferMain(buffer);
                    figure.draw();
                }
                condicional++;
            }
        }
        //Escena 03
        if (TIME == 35700){
            buffer = escenarios.get(2);
        }
        if (TIME >= 48000 && TIME <= 48500){
            if (condicional == 1){
                for (int i = 0; i < 2; i++) {
                    Figure figure = figures.get(3);
                    figures.remove(figure);
                    figure.setBufferMain(buffer);
                    figure.draw();
                }
                condicional++;
            }
        }
        //Escena 04
        if (TIME == 55700){
            buffer = escenarios.get(3);
        }
        if (TIME >= 67000 && TIME <= 67500){
            if (condicional == 2){
                for (int i = 0; i < 2; i++) {
                    Figure figure = figures.get(2);
                    figures.remove(figure);
                    figure.setBufferMain(buffer);
                    figure.draw();
                }
                condicional++;
            }
        }
        //Escena 05
        if (TIME == 74700){
            buffer = escenarios.get(4);
        }

        if (TIME == 91950){
            new Rectangulo(new Point(635,410),new Point(660,425),buffer,Color.red,140).draw();
            new Rectangulo(new Point(630,410),new Point(650,425),buffer,Color.red,45).draw();
            new FloodFill(new Point(635,410),Color.red,buffer);
        }

//        this.getGraphics().drawImage(buffer, 0, 0, this);
        this.getGraphics().drawImage(bufferSec, 0, 0, this);
    }

    private void loadScene(int index){
        Point[] points;
        switch (index){
            case 0: //Bosque
                escenarios.add(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB));
                new Rectangulo(new Point(0,0), new Point(750,500),escenarios.get(0),Color.decode("#61C9E5")).draw();
                new Rectangulo(new Point(0,450), new Point(750,500),escenarios.get(0),Color.decode("#91E561")).draw();
                for (int i = 0; i < 750; i+=45){
                    new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),escenarios.get(0),Color.decode("#9E8054")).draw();
                }
                new Rectangulo(new Point(0,444), new Point(750,449),escenarios.get(0),Color.decode("#B5B5B5")).draw();
                //new Rectangulo(new Point(0,0), new Point(30,500),escenarios.get(0),Color.decode("#B5B5B5")).draw();
                new Nube(new Point(50,110),new Point(65,120),escenarios.get(0),Color.white).draw();
                new Nube(new Point(600,100),new Point(615,110),escenarios.get(0),Color.white).draw();
                new Nube(new Point(180,120),new Point(195,130),escenarios.get(0),Color.white).draw();
                new Nube(new Point(340,210),new Point(355,220),escenarios.get(0),Color.white).draw();
                new Nube(new Point(450,260),new Point(465,270),escenarios.get(0),Color.white).draw();
                new Nube(new Point(640,210),new Point(655,220),escenarios.get(0),Color.white).draw();
                new Nube(new Point(150,360),new Point(165,370),escenarios.get(0),Color.white).draw();
            break;
            case 1: //Mar
                escenarios.add(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB));
                new Rectangulo(new Point(0,0), new Point(750,500),escenarios.get(1),Color.decode("#7DB4C3")).draw();
                new Rectangulo(new Point(0,445), new Point(750,500),escenarios.get(1),Color.decode("#447CBA")).draw();
                //Vias
                for (int i = 0; i < 750; i+=45){
                    new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),escenarios.get(1),Color.decode("#9E8054")).draw();
                }
                new Rectangulo(new Point(0,444), new Point(750,449),escenarios.get(1),Color.decode("#B5B5B5")).draw();
                //nubes
                new Nube(new Point(0,80),new Point(70,110),escenarios.get(1),Color.decode("#898989")).draw();
                new Nube(new Point(130,160),new Point(210,185),escenarios.get(1),Color.decode("#B9B9B9")).draw();
                new Nube(new Point(270,90),new Point(350,120),escenarios.get(1),Color.decode("#646262")).draw();
                new Nube(new Point(420,175),new Point(510,200),escenarios.get(1),Color.decode("#B5B5B5")).draw();
                new Nube(new Point(570,90),new Point(650,120),escenarios.get(1),Color.decode("#646262")).draw();
                break;
            case 2: //Desierto
                escenarios.add(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB));
                new Rectangulo(new Point(0,0), new Point(750,500),escenarios.get(2),Color.decode("#7DB4C3")).draw();
                new Rectangulo(new Point(0,445), new Point(750,500),escenarios.get(2),Color.decode("#DAC953")).draw();
                //Piramides
                points = new Point[]{new Point(445,443), new Point(582,300), new Point(720,443)};
                new Polygon(points,escenarios.get(2),Color.decode("#CBBB61")).draw();
                new FloodFill(new Point(582,400), Color.decode("#CBBB61"),escenarios.get(2));
                points = new Point[]{new Point(145,443), new Point(332,200), new Point(520,443)};
                new Polygon(points,escenarios.get(2),Color.decode("#CBBB61")).draw();
                new FloodFill(new Point(332,400), Color.decode("#CBBB61"),escenarios.get(2));
                //Vias
                for (int i = 0; i < 750; i+=45){
                    new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),escenarios.get(2),Color.decode("#9E8054")).draw();
                }
                new Rectangulo(new Point(0,444), new Point(750,449),escenarios.get(2),Color.decode("#B5B5B5")).draw();
                new Sol(new Point(100,150),escenarios.get(2)).draw();
                break;
            case 3: //Jungla
                escenarios.add(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB));
                new Rectangulo(new Point(0,0), new Point(750,500),escenarios.get(3),Color.decode("#E3F3B6")).draw();
                new Rectangulo(new Point(0,445), new Point(750,500),escenarios.get(3),Color.decode("#7C9C23")).draw();
                //Piramide Maya
                    //Escalon 01
                points = new Point[]{new Point(400,443), new Point(420,400), new Point(700,400) ,new Point(720,443)};
                new Polygon(points,escenarios.get(3),Color.gray).draw();
                new FloodFill(new Point(550,420), Color.gray,escenarios.get(3));
                    //Escalon02
                points = new Point[]{new Point(430,400), new Point(450,360), new Point(670,360) ,new Point(690,400)};
                new Polygon(points,escenarios.get(3),Color.gray).draw();
                new FloodFill(new Point(550,380), Color.gray,escenarios.get(3));
                    //Escalon03
                points = new Point[]{new Point(460,360), new Point(480,320), new Point(640,320) ,new Point(660,360)};
                new Polygon(points,escenarios.get(3),Color.gray).draw();
                new FloodFill(new Point(550,340), Color.gray,escenarios.get(3));
                    //Escaleras
                points = new Point[]{new Point(480,443), new Point(540,320), new Point(580,320) ,new Point(640,443)};
                new Polygon(points,escenarios.get(3),Color.lightGray).draw();
                new FloodFill(new Point(550,420), Color.lightGray,escenarios.get(3));
                    //Punta
                new Rectangulo(new Point(500,280),new Point(620,320),escenarios.get(3),Color.gray).draw();
                new Rectangulo(new Point(540,290),new Point(580,320),escenarios.get(3),Color.decode("#CDDBA7")).draw();
                new Rectangulo(new Point(480,270),new Point(640,280),escenarios.get(3),Color.gray).draw();
                new Rectangulo(new Point(485,265),new Point(635,270),escenarios.get(3),Color.gray).draw();
                //Arbol
                new Rectangulo(new Point(20,180),new Point(80,448),escenarios.get(3),Color.decode("#806625")).draw();
                new Rectangulo(new Point(80,280),new Point(180,295),escenarios.get(3),Color.decode("#806625")).draw();
                new Nube(new Point(50,160),new Point(135,180),escenarios.get(3),Color.decode("#54681C")).draw();
                new Nube(new Point(10,110),new Point(75,135),escenarios.get(3),Color.decode("#54681C")).draw();
                new Nube(new Point(200,285),new Point(235,300),escenarios.get(3),Color.decode("#54681C")).draw();
                //Vias
                for (int i = 0; i < 750; i+=45){
                    new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),escenarios.get(3),Color.decode("#806625")).draw();
                }
                new Rectangulo(new Point(0,444), new Point(750,449),escenarios.get(3),Color.decode("#B5B5B5")).draw();
                break;
            case 4:
                escenarios.add(new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB));
                new Rectangulo(new Point(0,0), new Point(750,500),escenarios.get(4),Color.decode("#7DB4C3")).draw();
                new Rectangulo(new Point(0,450), new Point(750,500),escenarios.get(4),Color.decode("#91E561")).draw();
                for (int i = 0; i < 540; i+=45){
                    new Rectangulo(new Point(0 + i,449), new Point(16 + i,452),escenarios.get(4),Color.decode("#9E8054")).draw();
                }
                new Rectangulo(new Point(0,444), new Point(540,449),escenarios.get(4),Color.decode("#B5B5B5")).draw();
                new Rectangulo(new Point(540,430), new Point(550,449),escenarios.get(4),Color.decode("#9E8054")).draw();
                new Rectangulo(new Point(535,420), new Point(555,430),escenarios.get(4),Color.decode("#9E8054")).draw();
                //Arbol
                new Rectangulo(new Point(720,360), new Point(750,449),escenarios.get(4),Color.decode("#9E8054")).draw();
                new Nube(new Point(680,340),new Point(720,320),escenarios.get(4),Color.decode("#6BA046")).draw();
               // new Nube(new Point(680,340),new Point(720,320),escenarios.get(4),Color.decode("#6BA046")).draw();
                new Nube(new Point(695,310),new Point(740,340),escenarios.get(4),Color.decode("#6BA046")).draw();
                new FloodFill(new Point(720,365),Color.decode("#6BA046"),escenarios.get(4));
                new Persona(new Point(680,400),new Point(690,449),escenarios.get(4),Color.decode("#FBF97D")).draw();
                //Nubes
                new Nube(new Point(50,110),new Point(65,120),escenarios.get(4),Color.white).draw();
                new Nube(new Point(100,100),new Point(115,110),escenarios.get(4),Color.white).draw();
                new Nube(new Point(350,210),new Point(365,220),escenarios.get(4),Color.white).draw();
                break;
        }
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
            int tiempo = (int) ( figure.getDuracion() * 1000 )/ DELAY;
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
                if (figure instanceof Tren){
                    figure.setBufferMain(buffer);
                } else {
                    figure.setBuffer(buffer);
                }
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
                                    if (i != animate.size() - 1 && animate.size() != 0){
                                        figure = animate.get(i);
                                        figure.draw();
                                        transform(i);
                                    }
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

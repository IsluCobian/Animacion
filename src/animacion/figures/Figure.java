/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package animacion.figures;

import strdatos.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import strdatos.AnimationFeatures;

/**
 * Autor: Luis Cobian
 * Registro: 20310016
 */

public abstract class Figure {
    Point startPoint, endPoint;
    BufferedImage buffer;
    int ang, tX, tY;
    double scaleX, scaleY;
    AnimationFeatures animationFeatures;
    

    public Figure(Point startPoint, Point endPoint, BufferedImage buffer) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.buffer = buffer;
        animationFeatures = new AnimationFeatures();
        ang = 0;
        tX = 0;
        tY = 0;
        scaleX = 0;
        scaleY = 0;
    }

    public Figure(Point startPoint, Point endPoint, BufferedImage buffer, int ang) {
        this(startPoint, endPoint, buffer);
        this.ang = ang;
    }
    
    public abstract void draw();
    
    public void translate(int tiempo){
        int [] trans = animationFeatures.getTrasValues();
        tX += trans[0]/tiempo;
        tY += trans[1]/tiempo;
        
        double[][] translateMatrix = {{1, 0, trans[0]/tiempo},{0, 1, trans[1]/tiempo},{0, 0, 1}};
    
        Point[] points = {getStart(), getEnd()};
        for (Point p : points) {
            double[] figurePosition = {p.x, p.y, 1};
            double[] newPosition = matrixMultiply(translateMatrix, figurePosition);
            p.x = (int) newPosition[0];
            p.y = (int) newPosition[1];
        }
    }
    
    public void scalate(int tiempo){
        double [] scale = animationFeatures.getScaleValues();
        scaleX += scale[0]/tiempo;
        scaleY += scale[1]/tiempo;
   
        double centerX = (getStart().x + getEnd().x) / 2.0;
        double centerY = (getStart().y + getEnd().y) / 2.0;

        double[][] scalateMatrix = {{scale[0]/tiempo, 0, 0}, {0, scale[1]/tiempo, 0}, {0, 0, 1}};
        double[] start = {getStart().x - centerX, getStart().y - centerY, 1};
        double[] end = {getEnd().x - centerX, getEnd().y - centerY, 1};
        double[] newStart = matrixMultiply(scalateMatrix, start);
        double[] newEnd = matrixMultiply(scalateMatrix, end);

       
        startPoint.x = (int) (newStart[0] + centerX);
        startPoint.y = (int) (newStart[1] + centerY);
        endPoint.x = (int) (newEnd[0] + centerX);
        endPoint.y = (int) (newEnd[1] + centerY);
    }
    
    public void rotate(int tiempo){
        this.ang += animationFeatures.getAng()/tiempo;
    }
    
    public Point rotate(int x, int y){
        double[][] rotateMatrix = {{Math.cos(Math.toRadians(ang)), -Math.sin(Math.toRadians(ang)), 0},{Math.sin(Math.toRadians(ang)), Math.cos(Math.toRadians(ang)), 0},{0, 0, 1}};
        double[] figurePosition = {x, y, 1};
        double[] newPosition = matrixMultiply(rotateMatrix, figurePosition);
        return new Point((int)newPosition[0],(int)newPosition[1]);
    }
    
    public void putPixel(int x, int y, Color c) {
        //calculo centro de la figura
        int centerX = startPoint.x + (endPoint.x - startPoint.x) / 2;
        int centerY = startPoint.y + (endPoint.y - startPoint.y) / 2;
        //se trasladan las coordenadas al centro
        int nx = x - centerX;                
        int ny = y - centerY;
        //se calcula la rotacion
        Point newPoint = rotate(nx,ny);
        //Se regresan las x a su posicion ya con rotacion
        int newX = newPoint.x + centerX;             
        int newY = newPoint.y + centerY;             
        if (newX < 0 || newX >= buffer.getWidth() || newY < 0 || newY >= buffer.getHeight()) {
            return;
        }
        buffer.setRGB(newX, newY, c.getRGB());
    }
        
    public double[] matrixMultiply(double[][] matrix, double[] vector) {
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    public void setBuffer(BufferedImage buffer) {this.buffer = buffer;}

    public BufferedImage getBuffer() {
        return buffer;
    }

    public Point getPoint(){return startPoint;}
    
    public Point getStart() {return startPoint;}

    public Point getEnd() {return endPoint;}
    
    //Set Features
    public void setTimes(int inicio, int fin){
        animationFeatures.setTiempos(new int[]{inicio,fin});
    }
    
    public void setTraslation(int dx, int dy){
        animationFeatures.setTrasValues(new int[]{dx,dy});
    }
    
    public void setScalation(double sx, double sy){
        animationFeatures.setScaleValues(new double[]{sx,sy});
    }
    
    public void setAng(int ang){
        animationFeatures.setAng(ang);
    }

    public int getDuracion(){
        return animationFeatures.getTiempos()[1] - animationFeatures.getTiempos()[0];
    }

    public int getStartTime(){
        return animationFeatures.getTiempos()[0];
    }

    public int getFinishTime(){
        return animationFeatures.getTiempos()[1];
    }

    public boolean translate(){
        if (animationFeatures.getTrasValues() != null){
            return true;
        }
        return false;
    }

    public boolean scale(){
        if (animationFeatures.getScaleValues() != null){
            return true;
        }
        return false;
    }

    public boolean rotate(){
        if (animationFeatures.getAng() != 0){
            return true;
        }
        return false;
    }
    
}

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
    
    public void translate(int dx, int dy){
        tX += dx;
        tY += dy;
        
        double[][] translateMatrix = {{1, 0, dx},{0, 1, dy},{0, 0, 1}};
    
        Point[] points = {getStart(), getEnd()};
        for (Point p : points) {
            double[] figurePosition = {p.x, p.y, 1};
            double[] newPosition = matrixMultiply(translateMatrix, figurePosition);
            p.x = (int) newPosition[0];
            p.y = (int) newPosition[1];
        }
    }
    
    public void scalate(double sx, double sy){
        scaleX += sx;
        scaleY += sy;
   
        double centerX = (getStart().x + getEnd().x) / 2.0;
        double centerY = (getStart().y + getEnd().y) / 2.0;

        double[][] scalateMatrix = {{sx, 0, 0}, {0, sy, 0}, {0, 0, 1}};
        double[] start = {getStart().x - centerX, getStart().y - centerY, 1};
        double[] end = {getEnd().x - centerX, getEnd().y - centerY, 1};
        double[] newStart = matrixMultiply(scalateMatrix, start);
        double[] newEnd = matrixMultiply(scalateMatrix, end);

       
        startPoint.x = (int) (newStart[0] + centerX);
        startPoint.y = (int) (newStart[1] + centerY);
        endPoint.x = (int) (newEnd[0] + centerX);
        endPoint.y = (int) (newEnd[1] + centerY);
    }
    
    public void rotate(int ang){
        this.ang += ang;
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

    public int getAng() {return ang;}

    public double getScaleX() {return scaleX;}
    
    public double getScaleY() {return scaleY;}

    public int getTX() {return tX;}

    public int getTY() {return tY;}
    
    //Setear Features
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
    
}

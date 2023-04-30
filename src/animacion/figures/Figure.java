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
    protected Point startPoint, endPoint, rotationPoint;
    protected BufferedImage buffer;
    private int ang;
    protected Color color;
    private boolean steps;
    private AnimationFeatures animationFeatures;
    

    public Figure(Point startPoint, Point endPoint, BufferedImage buffer, Color color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.buffer = buffer;
        this.color = color;
        animationFeatures = new AnimationFeatures();
        rotationPoint = new Point(0,0);
        ang = 0;
    }
    public Figure(Point startPoint, Point endPoint, BufferedImage buffer, Color color, boolean steps) {
        this(startPoint, endPoint, buffer, color);
        this.steps = steps;
    }

    public Figure(Point startPoint, Point endPoint, BufferedImage buffer, Color color, int ang) {
        this(startPoint, endPoint, buffer, color);
        this.ang = ang;
    }
    
    public abstract void draw();
    
    public void translate(int tiempo){
        int [] trans = animationFeatures.getTrasValues();
        
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
   
        double centerX = (getStart().x + getEnd().x) / 2.0;
        double centerY = (getStart().y + getEnd().y) / 2.0;

        double[][] scalateMatrix = {{1+scale[0]/tiempo, 0, 0}, {0, 1+scale[1]/tiempo, 0}, {0, 0, 1}};
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
    
    private Point rotate(int x, int y){
        double[][] rotateMatrix = {{Math.cos(Math.toRadians(ang)), -Math.sin(Math.toRadians(ang)), 0},{Math.sin(Math.toRadians(ang)), Math.cos(Math.toRadians(ang)), 0},{0, 0, 1}};
        double[] figurePosition = {x, y, 1};
        double[] newPosition = matrixMultiply(rotateMatrix, figurePosition);
        return new Point((int)newPosition[0],(int)newPosition[1]);
    }
    
    protected void putPixel(int x, int y, Color c) {
        //calculo centro de la figura
        int centerX = ((getStart().x + getEnd().x) / 2) + rotationPoint.x;
        int centerY = ((getStart().y + getEnd().y) / 2) + rotationPoint.y;
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
        
    private double[] matrixMultiply(double[][] matrix, double[] vector) {
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

    public Point getStart() {return startPoint;}

    public Point getEnd() {return endPoint;}

    //Sets the middle point to apply floodfill
    protected Point getFloodPoint() {
        int centerX = ((getStart().x + getEnd().x) / 2) + rotationPoint.x;
        int centerY = ((getStart().y + getEnd().y) / 2) + rotationPoint.y;

        int nx = - rotationPoint.x;
        int ny = - rotationPoint.y;

        Point newPoint = rotate(nx,ny);

        int newX = newPoint.x + centerX;
        int newY = newPoint.y + centerY;

        return new Point(newX,newY);
    }

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

    //Traslate the rotation/pivot point
    public void setRotationPoint(Point rotationPoint) {
        this.rotationPoint = rotationPoint;
    }

    //Get Features
    public boolean isSteps() {
        return steps;
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

    //Get active features
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

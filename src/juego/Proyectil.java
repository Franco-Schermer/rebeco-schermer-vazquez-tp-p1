package juego;

import java.awt.Color;
import entorno.Entorno;

public class Proyectil {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double angulo; 

    public Proyectil(double x, double y, double angulo) {
        this.x = x;
        this.y = y;
        this.ancho = 8;
        this.alto = 8;
        this.velocidad = 5.0;
        this.angulo = angulo; 
    }

    public void mover() {
       
        this.x += this.velocidad * Math.cos(this.angulo);
        this.y += this.velocidad * Math.sin(this.angulo);
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, this.ancho, Color.MAGENTA);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
}

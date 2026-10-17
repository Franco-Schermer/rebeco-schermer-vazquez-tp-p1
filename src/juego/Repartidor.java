package juego;

import java.awt.Color;
import entorno.Entorno;

public class Repartidor {
    private double x;
    private double y;
    private double ancho; 
    private double alto;  
    private double velocidad;
    private double angulo; 

    public Repartidor(double x, double y, double ancho, double alto, double velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.angulo = -Math.PI / 2;; 
    }

    public void dibujar(Entorno entorno) {
        
        entorno.dibujarTriangulo(this.x, this.y, (int) this.ancho, (int) this.alto, this.angulo, Color.CYAN);
    }

    public void moverDerecha() {
        this.x += this.velocidad;
        this.angulo = 0 ; 
    }

    public void moverIzquierda() {
        this.x -= this.velocidad;
        this.angulo = -Math.PI; // 
    }

    public void moverAbajo() {
        this.y += this.velocidad;
        this.angulo = Math.PI / 2; 
    }

    public void moverArriba() {
        this.y -= this.velocidad;
        this.angulo = 3 * Math.PI / 2; 
    }

    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

   
    public double getAncho() {
        return this.ancho;
    }

    public double getAlto() {
        return this.alto;
    }
    public double getAngulo() {
        return this.angulo;
    }
}
package juego;

import java.awt.Color;
import entorno.Entorno;

public class Proyectil {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private String direccion; 

    public Proyectil(double x, double y, String direccion) {
        this.x = x;
        this.y = y;
        this.ancho = 8;
        this.alto = 8;
        this.velocidad = 5.0;
        this.direccion = direccion;
    }

    public void mover() {
        if (this.direccion.equals("ARRIBA")) {
            this.y -= this.velocidad;
        } else if (this.direccion.equals("ABAJO")) {
            this.y += this.velocidad;
        } else if (this.direccion.equals("IZQUIERDA")) {
            this.x -= this.velocidad;
        } else if (this.direccion.equals("DERECHA")) {
            this.x += this.velocidad;
        }
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
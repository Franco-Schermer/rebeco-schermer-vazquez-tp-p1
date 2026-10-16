package juego;

import java.awt.Color;

import entorno.Entorno;

// Creacion de la clase para dibujar una CUADRA

public class Cuadra {

	private double x;
	private double y;
	private double alto;
	private double ancho;
	
	
	public Cuadra(double x, double y, double alto, double ancho) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAlto() {
		return alto;
	}

	public double getAncho() {
		return ancho;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.green);
	}
	
}

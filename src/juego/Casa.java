package juego;

import java.awt.Color;

import entorno.Entorno;



public class Casa {
	
	private int x;
	private int y;
	private double alto;
	private double ancho;
	
	public Casa(int x, int y, double alto, double ancho) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getAlto() {
		return alto;
	}

	public double getAncho() {
		return ancho;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.orange);
	}
	
	//Metodo para saber si el mouse esta dentro de las coordenadas de CASA
	public boolean estaAdentro(double px, double py) {
	    return px >= (this.x - this.ancho / 2) && px <= (this.x + this.ancho / 2) &&
	           py >= (this.y - this.alto / 2) && py <= (this.y + this.alto / 2);
	}
	
}

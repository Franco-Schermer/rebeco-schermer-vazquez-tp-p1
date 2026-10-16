package juego;

import java.awt.Color;

import entorno.Entorno;

public class Mapa {
	
private Cuadra[] cuadras;
	
	private int fila = 4;
	private int columna = 4;
	private double anchoCuadra = 145;
	private double altoCuadra = 90;
	private double calle = 45; 
	private double inicioX = 110;
	private double inicioY = 95;
	
	public Mapa() {
		
		this.cuadras = new Cuadra[16];
		int indice = 0;
		
		for(int i = 0; i < this.fila; i++) {
			for(int j = 0; j < this.columna; j++) {
				
				double x = this.inicioX + j * (this.anchoCuadra + this.calle);
				double y = this.inicioY + i * (this.altoCuadra + this.calle);
				
				this.cuadras[indice] = new Cuadra(x, y, altoCuadra, anchoCuadra);
				indice++;
			}
		}
	}
	
	// Método para recorrer el arreglo y dibujar cada cuadra en pantalla
	public void dibujar(Entorno entorno) {
		for (int i = 0; i < this.cuadras.length; i++) {
			if (this.cuadras[i] != null) {
				this.cuadras[i].dibujar(entorno);
			}
		}
	}

	
}
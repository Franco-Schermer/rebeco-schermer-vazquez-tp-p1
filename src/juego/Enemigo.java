package juego;

import java.awt.Color;
import entorno.Entorno;

public class Enemigo {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;
	private int direccion;
	private int intervaloEntreBombas;
	
	public Enemigo(int x, int y, int ancho, int alto, int velociad, int direccion, int intervaloEntreBombas) {
		this.x =  x;
		this.y =  y;
		this.ancho =  ancho;
		this.alto =  alto;
		this.velocidad =  velociad;
		this.direccion =  direccion;
		this.intervaloEntreBombas = intervaloEntreBombas;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
	}
	
	public void eneMovDer() {
		this.x += this.velocidad;
	}
	
	public void eneMovIzq() {
		this.x -= this.velocidad;
	}
	
	public void eneMovArr() {
		this.y -= this.velocidad;
	}
	
	public void eneMovAbaj() {
		this.y += this.velocidad;
	}
	
	public int getXEn() {
		return x;
	}
	
	public int getYEn() {
		return y;
	}
	
	public int getAnchoEn() {
        return this.ancho;
    }

    public int getAltoEn() {
        return this.alto;
    }
    
    public int getDirecEn() {
    	return this.direccion;
    }
    
    public int getVelocidad() {
    	return this.velocidad;
    }
    
    public void setDirecEn(int direccion) {
    	this.direccion = direccion;
    }
    
    public boolean bombaTime() {
    	if (this.intervaloEntreBombas<600) {
    		this.intervaloEntreBombas++;
    		return false;
    	}else {
    		this.intervaloEntreBombas = 0;
    		return true;
    	}
    }

}

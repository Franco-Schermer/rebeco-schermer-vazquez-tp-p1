package juego;

import java.awt.Color;
import entorno.Entorno;

public class Bomba {
	
	private int x;
	private int y;
	private double ancho;
	private double alto;
	private boolean exploto;
	private int mechaBomb;
	private int direccion;
	private int velocidadImplosion;
	private int tiempoDeExplosion;
	
	public Bomba(int x, int y, double ancho, double alto, int direccion) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.exploto = false;
		this.mechaBomb = 180;
		this.direccion = direccion;
		this.velocidadImplosion = 2;
		this.tiempoDeExplosion = 0;
	}
	
	public void cuentaRegresiva() {
		if(this.mechaBomb>0) {
			this.mechaBomb--;
			if(this.mechaBomb==0) {
				this.exploto = true;
			}
		}
	}
	
	public void dibujar (Entorno entorno) {
		if (this.exploto) {
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.YELLOW);
		}else{
			entorno.dibujarCirculo(this.x, this.y, this.alto, Color.DARK_GRAY);
		}
	}
	
	public int getXBomb() {
		return this.x;
	}
	
	public int getYBomb() {
		return this.y;
	}
	
	public double getAltoBomb() {
		return this.alto;
	}
	
	public double getAnchoBomb() {
		return this.ancho;
	}
	
	public void crecerExplosion() {
		if (this.exploto) {
			if (this.direccion == 1 || this.direccion == 2) {
				if (this.ancho<=160) {
					this.ancho+=this.velocidadImplosion;
				}
				if(this.alto<=45) { 
					this.alto+=this.velocidadImplosion;
				}
			}
			if (this.direccion == 3 || this.direccion == 4) {
				if (this.ancho<=56) {
					this.ancho+=this.velocidadImplosion;
				}
				if(this.alto<=150) { 
					this.alto+=this.velocidadImplosion;
				}
			}
		}
	}
	
	public boolean duracionExp() {
	    if (this.exploto) {
	        if (this.tiempoDeExplosion < 120) {
	            this.tiempoDeExplosion++;
	            return false;
	        } else {
	            this.tiempoDeExplosion = 0;
	            return true;
	        }
	    }
	    return false;
	}

}

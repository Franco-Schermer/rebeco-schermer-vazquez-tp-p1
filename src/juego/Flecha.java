package juego;

import java.awt.Color;

import entorno.Entorno;

public class Flecha {
	
	private int x;
    private int y;
    private boolean visible;
	
    public Flecha(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = false;
    }
	
    public void dibujar(Entorno entorno) {
        if (visible) {
          
            entorno.dibujarRectangulo(this.x, this.y + 6, 10, 18, 0, Color.black);

            
            entorno.dibujarTriangulo(this.x, this.y - 8, 20, 18, 3*Math.PI / 2, Color.black);
        }
    }
    
    public void mostrar(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }
    
    public void ocultar() {
        this.visible = false;
    }
	
}

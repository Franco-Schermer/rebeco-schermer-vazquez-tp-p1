package juego;

import entorno.Entorno;

public class Cuadra {
    private int x;
    private int y;
    private double ancho;
    private double alto;
    
    private Casa[] casas;
    private Negocio negocio;

    public Cuadra(int x, int y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        
        this.casas = new Casa[3];
        this.casas[0] = new Casa(x - 20, y - 20, 22, 22); 
        this.casas[1] = new Casa(x + 20, y - 20, 22, 22); 
        this.casas[2] = new Casa(x - 20, y + 20, 22, 22);  
        
        this.negocio = new Negocio(x + 20, y + 20, 22, 22);   
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, java.awt.Color.green);
        
        for (int i = 0; i < this.casas.length; i++) {
            if (this.casas[i] != null) {
                this.casas[i].dibujar(entorno);
            }
        }
        
        if (this.negocio != null) {
            this.negocio.dibujar(entorno);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }

    public Negocio getNegocio() {
        return this.negocio;
    }
    
    public Casa[] getCasas() {
        return this.casas;
    }
    
}

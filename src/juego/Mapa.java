package juego;

import entorno.Entorno;

public class Mapa {
    private Cuadra[] cuadras;

    public Mapa() {
        this.cuadras = new Cuadra[16]; 
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = 130 + j * 180;
                int y = 80 + i * 140;
                this.cuadras[index] = new Cuadra(x, y, 100, 90);
                index++;
            }
        }
    }

    public void dibujar(Entorno entorno) {
        for (int i = 0; i < this.cuadras.length; i++) {
            if (this.cuadras[i] != null) {
                this.cuadras[i].dibujar(entorno);
            }
        }
    }

    public Cuadra[] getCuadras() {
        return this.cuadras;
    }
    
    
    
}
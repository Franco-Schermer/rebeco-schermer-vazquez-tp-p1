package juego;

import entorno.Entorno;
import java.awt.Color;

public class Interfaz {

    public void dibujar(Entorno entorno, int vidas, int pedidosEntregados,int pedidosPerdidos, int enemigosEliminados, String estadoPedido) {
        
        entorno.dibujarRectangulo(400, 20, 800, 40, 0, Color.GRAY);

        
        entorno.cambiarFont("Arial", 14, Color.black);
        int segundos = entorno.tiempo() / 1000;

        entorno.escribirTexto("Tiempo: " + segundos + "s", 15, 25);
        entorno.escribirTexto("Vidas: " + vidas, 120, 25);
        entorno.escribirTexto("Entregados: " + pedidosEntregados, 220, 25);
        entorno.escribirTexto("Pedidos Perdidos: " + pedidosPerdidos,340 , 25);
        entorno.escribirTexto("Eliminados: " + enemigosEliminados, 500, 25);
        
        
        entorno.cambiarFont("Arial", 17, Color.white);
        entorno.escribirTexto("Pedido: " + estadoPedido, 630, 25);
    }
}
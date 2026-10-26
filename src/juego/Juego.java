package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego
{
	private Entorno entorno;
	private Mapa mapa;
	private Repartidor repartidor;
	private Proyectil disparo;
	private Flecha flecha;
	private Interfaz interfaz;
	private boolean juegoTerminado = false;
	private Enemigo[] enemigos;
	private Bomba[] bombas;

	
	// Variables para estadísticas
	private int vidas = 3;
	private int pedidosEntregados = 0;
	private int enemigosEliminados = 0;
	private int pedidosPerdidos = 0;
	
	// Control del ciclo de vida del pedido y temporizadores basados en Ticks
	private String estadoPedidoActual = "REALIZADO";
	private Negocio negocioActual;
	private Casa casaActual;
	
	// Contadores por ticks (asumiendo aprox 60 ticks por segundo)
	private int tiempoActualEspera = 0;
	private int tiempoActualEntrega = 0;
	private int tiempoActualResultado = 0;
	
	// Constantes de tiempo medidas en ticks (ej: 60 ticks = 1 segundo aprox)
	private int TIEMPO_ESPERA_REALIZADO = 120; // 2 segundos (60 * 2)
	private int TIEMPO_LIMITE_ENTREGA = 600;   // 10 segundos (60 * 10)
	private int TIEMPO_MOSTRAR_RESULTADO = 120; // 2 segundos (60 * 2)

	Juego()
	{
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar el mapa, el repartidor y la interfaz
		this.mapa = new Mapa();
		this.repartidor = new Repartidor(400, 570, 20, 25, 3.0);
		this.flecha = new Flecha(0, 0);
		this.interfaz = new Interfaz();
		this.enemigos = new Enemigo[4];
		this.enemigos[0] = new Enemigo(20, 290, 35, 35, 2, 1, 150);
		this.enemigos[1] = new Enemigo(780, 430, 35, 35, 2, 2, 450);
		this.enemigos[2] = new Enemigo(220, 52, 40, 35, 2, 3, 300);
		this.enemigos[3] = new Enemigo(580, 580, 40, 35, 2, 4, 0);
		this.bombas = new Bomba[5];
		
		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	
	// Método auxiliar para seleccionar un negocio y una casa al azar
	private void generarNuevoPedido() {
	    Cuadra[] cuadras = this.mapa.getCuadras();
	    Random random = new Random(); // Creamos el generador

	    // 1. Elegir una cuadra al azar y obtener su negocio
	    int indiceCuadraNegocio = random.nextInt(cuadras.length); 
	    this.negocioActual = cuadras[indiceCuadraNegocio].getNegocio(); 

	    // 2. Elegir una casa de destino distinta
	    int indiceCuadraCasa;
	    do {
	        indiceCuadraCasa = random.nextInt(cuadras.length);
	    } while (indiceCuadraCasa == indiceCuadraNegocio);

	    // Obtenemos el arreglo de casas de esa cuadra elegida
	    Casa[] casasDeCuadra = cuadras[indiceCuadraCasa].getCasas();

	    // Elegimos una casa al azar dentro de ese arreglo
	    int indiceCasaAleatoria = random.nextInt(casasDeCuadra.length);
	    this.casaActual = casasDeCuadra[indiceCasaAleatoria];

	    // 3. Si ambos se obtuvieron correctamente, pasamos al estado ESPERANDO
	    if (this.negocioActual != null && this.casaActual != null) {
	        this.estadoPedidoActual = "ESPERANDO";
	        this.flecha.mostrar(this.negocioActual.getX(), this.negocioActual.getY());
	    }
	}

	
	// Método de colisión
    private boolean hayColisionConCuadras(double posX, double posY, double pAncho, double pAlto) {
        Cuadra[] cuadras = this.mapa.getCuadras();
        
        for (int i = 0; i < cuadras.length; i++) {
            if (cuadras[i] != null) {
                double inicioX = cuadras[i].getX() - (cuadras[i].getAncho() / 2);
                double finX = cuadras[i].getX() + (cuadras[i].getAncho() / 2);
                double inicioY = cuadras[i].getY() - (cuadras[i].getAlto() / 2);
                double finY = cuadras[i].getY() + (cuadras[i].getAlto() / 2);

                if (posX + (pAncho / 2) > inicioX && posX - (pAncho / 2) < finX &&
                    posY + (pAlto / 2) > inicioY && posY - (pAlto / 2) < finY) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hayColisionEnemigoRepartidor(Enemigo enemigo) {
    	double inicioXEn = enemigo.getXEn() - (enemigo.getAnchoEn()/2);
    	double finXEn = enemigo.getXEn() + (enemigo.getAnchoEn()/2);
    	double inicioYEn = enemigo.getYEn() - (enemigo.getAltoEn()/2);
    	double finYEn = enemigo.getYEn() + (enemigo.getAltoEn()/2);
    	
    	double inicioXRep = this.repartidor.getX() - (this.repartidor.getAncho()/2);
    	double finXRep = this.repartidor.getX() + (this.repartidor.getAncho()/2);
    	double inicioYRep = this.repartidor.getY() - (this.repartidor.getAlto()/2);
    	double finYRep = this.repartidor.getY() + (this.repartidor.getAlto()/2);
    	
    	if (inicioXEn>finXRep || finXEn<inicioXRep ||
    			inicioYEn>finYRep || finYEn<inicioYRep) {
    		return false;
    	} else { 
    		return true;
    	}
    }
    
    
    private boolean hayColisionEnemigoProyectil(Enemigo enemigo) {
    	double inicioXEn = enemigo.getXEn() - (enemigo.getAnchoEn()/2);
    	double finXEn = enemigo.getXEn() + (enemigo.getAnchoEn()/2);
    	double inicioYEn = enemigo.getYEn() - (enemigo.getAltoEn()/2);
    	double finYEn = enemigo.getYEn() + (enemigo.getAltoEn()/2);
    	
    	double inicioXPro = this.disparo.getX() - (this.disparo.getAncho()/2);
    	double finXPro = this.disparo.getX() + (this.disparo.getAncho()/2);
    	double inicioYPro = this.disparo.getY() - (this.disparo.getAlto()/2);
    	double finYPro = this.disparo.getY() + (this.disparo.getAlto()/2);
    	
    	if (inicioXEn>finXPro || finXEn<inicioXPro ||
    			inicioYEn>finYPro || finYEn<inicioYPro) {
    		return false;
    	} else { 
    		return true;
    	}
    }
    
    private void generarNuevoEnemigo(int i) {
    	if(i==0) {
    		this.enemigos[i] = new Enemigo(20, 290, 35, 35, 2, 1, 150);
    	}else if(i==1) {
    		this.enemigos[i] = new Enemigo(780, 430, 35, 35, 2, 2, 450);
    	}else if(i==2) {
    		this.enemigos[i] = new Enemigo(220, 52, 40, 35, 2, 3, 300);
    	}else if(i==3) {
    		this.enemigos[i] = new Enemigo(580, 580, 40, 35, 2, 4, 0);
    	}
    }
    
    private boolean hayColisionBombaRepartidor(Bomba bomba){
    	double inicioXBomb = bomba.getXBomb() - (bomba.getAnchoBomb()/2);
    	double finXBomb = bomba.getXBomb() + (bomba.getAnchoBomb()/2);
    	double inicioYBomb = bomba.getYBomb() - (bomba.getAltoBomb()/2);
    	double finYBomb = bomba.getYBomb() + (bomba.getAltoBomb()/2);
    	
    	double inicioXRep = this.repartidor.getX() - (this.repartidor.getAncho()/2);
    	double finXRep = this.repartidor.getX() + (this.repartidor.getAncho()/2);
    	double inicioYRep = this.repartidor.getY() - (this.repartidor.getAlto()/2);
    	double finYRep = this.repartidor.getY() + (this.repartidor.getAlto()/2);
    	
    	if (inicioXBomb>finXRep || finXBomb<inicioXRep ||
    			inicioYBomb>finYRep || finYBomb<inicioYRep) {
    		return false;
    	} else { 
    		return true;
    	}
    }
    
    private void dropBomb(Enemigo enemigo) {
    	for (int i=0; i<this.bombas.length; i++) {
    		if(this.bombas[i]==null) {
    			this.bombas[i]= new Bomba(enemigo.getXEn(), enemigo.getYEn(), 30, 30, enemigo.getDirecEn());
    			return;
    		}
    	}
    }

	public void tick()
	{
		// 1. Dibujar el mapa
		this.mapa.dibujar(entorno);
		
		// 2. MÁQUINA DE ESTADOS DEL PEDIDO
		
		// Estado A: REALIZADO -> Espera un tiempo antes de generar el pedido
		if (this.estadoPedidoActual.equals("REALIZADO")) {
			this.tiempoActualEspera++;
			
			if (this.tiempoActualEspera >= TIEMPO_ESPERA_REALIZADO) {
				generarNuevoPedido();
				this.tiempoActualEspera = 0;
			}
		}
		
		// Estado B: EN_CAMINO
		else if (this.estadoPedidoActual.equals("EN_CAMINO")) {
			this.tiempoActualEntrega++;
			
			if (this.tiempoActualEntrega >= TIEMPO_LIMITE_ENTREGA) {
				// ¡Se agotó el tiempo! Pedido perdido
				this.estadoPedidoActual = "PERDIDO";
				this.flecha.ocultar();
				this.negocioActual = null; // Limpiamos referencias
				this.casaActual = null;
				this.pedidosPerdidos++;
				
				this.tiempoActualResultado = 0; 
			}
		}
		
		// Estado C: ENTREGADO / PERDIDO -> Muestra el resultado durante unos ticks y vuelve a REALIZADO
		else if (this.estadoPedidoActual.equals("ENTREGADO") || this.estadoPedidoActual.equals("PERDIDO")) {
			this.tiempoActualResultado++;
			
			if (this.tiempoActualResultado >= TIEMPO_MOSTRAR_RESULTADO) {
				this.estadoPedidoActual = "REALIZADO";
				this.tiempoActualEspera = 0;
			}
		}
		
		if (this.vidas > 0 && this.pedidosEntregados >= 5) {
		    this.juegoTerminado = true;
		}
		if(this.vidas == 0 || this.pedidosPerdidos >= 5) {
			this.juegoTerminado = true;
			if (this.juegoTerminado) {
		        this.mapa.dibujar(entorno); // Mantiene el mapa de fondo visible
		        entorno.dibujarRectangulo(400, 300, 350, 150, 0, Color.black);
		        entorno.cambiarFont("Arial", 30, Color.white);
		        entorno.escribirTexto("¡PERDISTE EL JUEGO!", 270, 310);
		        return; // Sale del tick y frena cualquier movimiento o actualización
			}
			
		}
		
		// Si el juego ya terminó, mostramos el cartel y detenemos todo acá
	    if (this.juegoTerminado) {
	        this.mapa.dibujar(entorno); // Mantiene el mapa de fondo visible
	        entorno.dibujarRectangulo(400, 300, 350, 150, 0, Color.black);
	        entorno.cambiarFont("Arial", 30, Color.white);
	        entorno.escribirTexto("¡GANASTE EL JUEGO!", 270, 310);
	        return; // Sale del tick y frena cualquier movimiento o actualización
		}
		
	 // Control de interacción con el Clic Derecho
	    if (this.entorno.sePresionoBoton(this.entorno.BOTON_DERECHO)) {
	        double mX = entorno.mouseX();
	        double mY = entorno.mouseY();
	        
	        // Si el pedido está ESPERANDO, chequear cercanía del repartidor Y clic sobre el negocio
	        if (this.estadoPedidoActual.equals("ESPERANDO")) {
	            double distanciaNegocio = Math.hypot(this.repartidor.getX() - this.negocioActual.getX(), 
	                                                this.repartidor.getY() - this.negocioActual.getY());
	            
	            if (distanciaNegocio < 60 && this.negocioActual.estaAdentro(mX, mY)) {
	                this.estadoPedidoActual = "EN_CAMINO";
	                this.flecha.ocultar();
	                this.flecha.mostrar(this.casaActual.getX(), this.casaActual.getY());
	                this.tiempoActualEntrega = 0;
	            }
	        }
	        
	        // Si el pedido está EN_CAMINO, chequear cercanía del repartidor Y clic sobre la casa
	        else if (this.estadoPedidoActual.equals("EN_CAMINO")) {
	            double distanciaCasa = Math.hypot(this.repartidor.getX() - this.casaActual.getX(), 
	                                             this.repartidor.getY() - this.casaActual.getY());
	            
	            if (distanciaCasa < 60 && this.casaActual.estaAdentro(mX, mY)) {
	                this.pedidosEntregados++; 
	                this.flecha.ocultar(); 
	                this.negocioActual = null; 
	                this.casaActual = null;
	                this.estadoPedidoActual = "ENTREGADO"; 
	                this.tiempoActualResultado = 0;
	            }
	        }
	    }
		
		// Obtenemos la posición y dimensiones dinámicamente
		double xActual = this.repartidor.getX();
		double yActual = this.repartidor.getY();
		double anchoRepartidor = this.repartidor.getAncho();
		double altoRepartidor = this.repartidor.getAlto();
		double velocidad = 3.0;
		
		// 3. Control de movimiento
		if (this.entorno.estaPresionada('d') || this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			if (xActual + velocidad < 790 && !this.hayColisionConCuadras(xActual + velocidad, yActual, anchoRepartidor, altoRepartidor)) {
				this.repartidor.moverDerecha();
			}
		}
		if (this.entorno.estaPresionada('a') || this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			if (xActual - velocidad > 18 && !this.hayColisionConCuadras(xActual - velocidad, yActual, anchoRepartidor, altoRepartidor)) {
				this.repartidor.moverIzquierda();
			}
		}
		if (this.entorno.estaPresionada('w') || this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA)) {
			if (yActual - velocidad > 50 && !this.hayColisionConCuadras(xActual, yActual - velocidad, anchoRepartidor, altoRepartidor)) {
				this.repartidor.moverArriba();
			}
		}
		if (this.entorno.estaPresionada('s') || this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
			if (yActual + velocidad < 590 && !this.hayColisionConCuadras(xActual, yActual + velocidad, anchoRepartidor, altoRepartidor)) {
				this.repartidor.moverAbajo();
			}
		}
		
		// 4. Dibujar al repartidor y la flecha
		this.repartidor.dibujar(this.entorno);
		this.flecha.dibujar(this.entorno); 
	
		// 5. Control de disparo
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && this.disparo == null) {
		    this.disparo = this.repartidor.disparar();
		}

		// 6. Mover, dibujar y controlar el disparo
		if (this.disparo != null) {
			this.disparo.mover();
			this.disparo.dibujar(entorno);

			if (this.disparo.getX() < 0 || this.disparo.getX() > 800 || 
				this.disparo.getY() < 0 || this.disparo.getY() > 600 ||
				this.hayColisionConCuadras(this.disparo.getX(), this.disparo.getY(), this.disparo.getAncho(), this.disparo.getAlto())) {
				
				this.disparo = null;
			}
		}
		
		for (int i= 0; i<enemigos.length;i++) {
			if(this.enemigos[i]==null) {
				generarNuevoEnemigo(i);
			}
			if (this.enemigos[i]!=null) {
				this.enemigos[i].dibujar(entorno);
				
				if (this.enemigos[i].getDirecEn() == 1) {
				    if (this.enemigos[i].getXEn() + this.enemigos[i].getVelocidad() < 790) {
				        this.enemigos[i].eneMovDer();
				    } else {
				        this.enemigos[i].setDirecEn(2);
				    }
				}
				
				if (this.enemigos[i].getDirecEn() == 2) {
				    if (this.enemigos[i].getXEn() - this.enemigos[i].getVelocidad() > 16) {
				        this.enemigos[i].eneMovIzq();
				    } else {
				        this.enemigos[i].setDirecEn(1);
				    }
				}
				
				if (this.enemigos[i].getDirecEn() == 3) {
				    if (this.enemigos[i].getYEn() - this.enemigos[i].getVelocidad() > 50) {
				        this.enemigos[i].eneMovArr();
				    } else {
				        this.enemigos[i].setDirecEn(4);
				    }
				}
				
				if (this.enemigos[i].getDirecEn() == 4) {
				    if (this.enemigos[i].getYEn() + this.enemigos[i].getVelocidad() < 590) {
				        this.enemigos[i].eneMovAbaj();
				    } else {
				        this.enemigos[i].setDirecEn(3);
				    }
				}
				if (hayColisionEnemigoRepartidor(this.enemigos[i])) {
					this.vidas--;
					this.repartidor.respawnRep(400, 570);
					    if (this.estadoPedidoActual.equals("EN_CAMINO")) {
					        this.estadoPedidoActual = "PERDIDO";
					        this.flecha.ocultar();
					        this.negocioActual = null;
					        this.casaActual = null;
					        this.pedidosPerdidos++;
					        this.tiempoActualResultado = 0;
					    }
					
				}
				if (this.disparo != null) {
					if (hayColisionEnemigoProyectil(this.enemigos[i])) {
						enemigos[i]=null;
						this.disparo=null;
						this.enemigosEliminados++;
					}
				}
				
				if (enemigos[i] != null) {
				    if (enemigos[i].bombaTime()) {
				        dropBomb(enemigos[i]);
				    }
				}
			}
		}
		
		for (int i=0;i<bombas.length;i++) {
			if (bombas[i]!=null) {
				this.bombas[i].cuentaRegresiva();
				this.bombas[i].crecerExplosion();
				this.bombas[i].dibujar(entorno);
				if (hayColisionBombaRepartidor(this.bombas[i])) {
				    this.vidas = 0;
				}
				if (this.bombas[i].duracionExp()) {
					this.bombas[i] = null;
				}
				
			}
		}

		// 7. Dibujar la interfaz 
		this.interfaz.dibujar(entorno, vidas, pedidosEntregados, pedidosPerdidos, enemigosEliminados, estadoPedidoActual);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
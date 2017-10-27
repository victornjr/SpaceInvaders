
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
//para los soniditos
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyListener;


public class Pantalla extends JPanel implements ActionListener, KeyListener, Runnable{
	private Timer timer;
	private Nave nave;
	private Escudo escudoIzq;
	private EscudoCentro escudoCentro;
	private EscudoDer escudoDer;
	private Alien1[][] aliens;
	private final int 	filaAlien,
	columnaAlien,
	posIXAlien=20,
	posIYAlien=20,
	espacioAlien;
	private Font letras;
	private String nombreG;
	private String[][] highScores;
	private String score, hs;
	private String ganar;
	private String[] tablaS = new String[10];
	private String vJugar;
	private String textoPausa = "Paused";
	private int marcador,
	sobran;
	private boolean fin;
	private boolean escudoDestruidoIzq = false;
	private boolean escudoDestruidoCentro = false;
	private boolean escudoDestruidoDer = false;
	private boolean continuar=true;
	private int nivel=0;
	private int marcadorTotal=0;
	private boolean signivel= true;
	private Thread cambioImg = new Thread(this);





	//para explosion alien
	private File explosionAlien;
	private AudioInputStream ais2;
	private Clip clipExplosion;

	//para explosion tanque
	private File explosionTanque;
	private AudioInputStream ais3;
	private Clip clipExplosionTanque;
	private Thread hilo;

	/*
	try{
  		this.clipStart();
  		this.clipTanque.setFramePosition(0);
  	}
  	catch(Exception ce){
  		ce.printStackTrace();
  	}*/

	public Pantalla(){
		super();
		this.setPreferredSize(new Dimension(500, 600));
		this.setDoubleBuffered(true);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new Listener());
		this.nivel = this.nivel+1;

		this.nave = new Nave();
		// agregar el sonido del disparo tanque

		//Agregar el sonido de la explosion de aliens
		try{
			explosionAlien = new File("invaderkilled.wav");
			ais2 = AudioSystem.getAudioInputStream(explosionAlien);
			clipExplosion = AudioSystem.getClip();
			clipExplosion.open(ais2);
		}
		catch(Exception ea){
			ea.printStackTrace();
		}
		//Agregar el sonido de la explosion del tanque
		try{
			explosionTanque = new File("explosion.wav");
			ais3 = AudioSystem.getAudioInputStream(explosionTanque);
			clipExplosionTanque = AudioSystem.getClip();
			clipExplosionTanque.open(ais3);
		}
		catch(Exception ea){
			ea.printStackTrace();
		}
		for (int i=0; i<10; i++){
			this.tablaS[i] = "";
		}
		
		//Poner Escudos
		this.escudoIzq = new Escudo();
		this.escudoCentro = new EscudoCentro();
		this.escudoDer = new EscudoDer();
		//Poner Aliens
		this.columnaAlien = 11;
		this.filaAlien = 3;
		this.espacioAlien = 1;
		aliens = new Alien1[this.columnaAlien][this.filaAlien];
		for (int i=0; i<this.columnaAlien; i++){
			for (int j=0; j<this.filaAlien; j++){
				this.aliens[i][j] = new Alien1(this.posIXAlien, this.posIYAlien,this.nivel);
				this.aliens[i][j].setPosX(this.posIXAlien + i*(this.aliens[i][j].getAlien().getWidth(null)-30)+ i*this.espacioAlien);
				this.aliens[i][j].setPosY(20+this.posIYAlien + j*(this.aliens[i][j].getAlien().getHeight(null)-30) + j*this.espacioAlien);
			}
		}
		this.sobran = this.filaAlien*this.columnaAlien;

		this.letras = new Font("OCR A Std", Font.PLAIN, 14);
		this.score = "Score: " + this.marcador;
		this.ganar = "";
		this.vJugar = "";
		this.hs = "";
		hilo = new Thread();
		hilo.start();
		this.fin = false;

		this.timer = new Timer(30, this);
		cambioImg.start();
		this.timer.start();	

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//dibujar nave
		g.drawImage(this.nave.getNave(), this.nave.getPosX(), this.nave.getPosY(),64,24, this);

		//la linea de abajo es para verificar el contorno de la nave.
		//g.setColor(Color.blue);
		//g.drawRect(this.nave.getPosX(), this.nave.getPosY(), this.nave.getNave().getWidth(null)-40, this.nave.getNave().getHeight(null)-40);

		//dibujar disparos
		ArrayList<Disparo> disparos = this.nave.getDisparos();
		for (int i=0; i<disparos.size(); i++){
			Disparo d = disparos.get(i);
			g.drawImage(d.getDisparo(), d.getPosX(), d.getPosY(),5,10, this);

			//la linea de abajo verifica el contorno de cada disparo.
			//g.setColor(Color.blue);
			//g.drawRect(d.getPosX(), d.getPosY(), d.getDisparo().getWidth(null)-35, d.getDisparo().getHeight(null)-118);
		}

		//dibujar escudos
		g.drawImage(this.escudoIzq.getEscudo(), this.escudoIzq.getX(), this.escudoIzq.getY(), 100,80, this);
		g.drawImage(this.escudoCentro.getEscudo(), this.escudoCentro.getX()+150, this.escudoCentro.getY(), 100,80, this);
		g.drawImage(this.escudoDer.getEscudo(), this.escudoDer.getX()+300, this.escudoDer.getY(), 100,80, this);
		//verificar contorno de los escudos
		//	g.setColor(Color.CYAN);
		//g.drawRect(this.escudoIzq.getX(), this.escudoIzq.getY(), this.escudoIzq.getEscudo().getWidth(null)-30, this.escudoIzq.getEscudo().getHeight(null));
		//g.drawRect(this.escudoCentro.getX()+150, this.escudoCentro.getY(), this.escudoCentro.getEscudo().getWidth(null)-30, this.escudoCentro.getEscudo().getHeight(null));
		//g.drawRect(this.escudoDer.getX()+300, this.escudoDer.getY(), this.escudoDer.getEscudo().getWidth(null)-30, this.escudoDer.getEscudo().getHeight(null));


		//dibujar aliens
		for (int i=0; i<this.columnaAlien; i++){
			for (int j=0; j<this.filaAlien; j++){
				if (this.aliens[i][j].getVisible()){
					g.drawImage(this.aliens[i][j].getAlien(), this.aliens[i][j].getPosX(), this.aliens[i][j].getPosY(),20,20, this);

					//la linea de abajo verifica el contorno de cada alien
					//g.setColor(Color.blue);
					//g.drawRect(this.aliens[i][j].getPosX(), this.aliens[i][j].getPosY(), this.aliens[i][j].getAlien().getWidth(null)-42, this.aliens[i][j].getAlien().getHeight(null)-42);
				}
				ArrayList<Laser> lasers = this.aliens[i][j].getLaser();
				for (int k=0; k<lasers.size(); k++){
					Laser l = lasers.get(k);
					g.drawImage(l.getDisparo(), l.getPosX(), l.getPosY(),5,10, this);

					//la linea de abajo verifica el contorno de cada laser.
					//g.setColor(Color.blue);
					//g.drawRect(l.getPosX(), l.getPosY(), l.getDisparo().getWidth(null)-35, l.getDisparo().getHeight(null)-118);
				}
			}
		}
		//prueba lineas dano a escudos
		//g.fillRect(this.escudoIzq.getX(), this.escudoIzq.getY(), 10, 10);
		//g.fillRect(this.escudoCentro.getX(), this.escudoCentro.getY(), 10, 10);
		//g.fillRect(this.escudoDer.getX(), this.escudoDer.getY(), 10, 10);

		//poner texto
		g.setColor(Color.WHITE);
		g.setFont(this.letras);
		g.drawString(this.score, 10, 20);
		g.drawString(this.ganar, 100, 350);
		g.drawString(this.vJugar, 80, 370);
		g.drawString(this.hs, 200, 50);
		int yM  = 0;
		for(int i=0; i<10; i++){
			g.drawString(this.tablaS[i], 150, 100 + yM);
			yM += 20;
		}

		Toolkit.getDefaultToolkit().sync();
		
		if(!this.continuar){
			g.drawString(this.textoPausa, (this.getWidth()/2)-10, this.getHeight()/2);
		}

	}

	public void actionPerformed(ActionEvent e) {
		this.nave.limitePantalla();
		ArrayList<Disparo> disparos = this.nave.getDisparos();
		for(int i = 0; i < disparos.size(); i++){
			Disparo d = disparos.get(i);
			if (d.getVisible()){
				if(this.continuar){
					d.mover();
				}
				nave.setRecarga(false);
			}
			else{
				disparos.remove(i);
				nave.setRecarga(true);
			}
			if((d.getContorno().intersects(this.escudoIzq.getContornoEscudo())) && this.escudoIzq.getVisible() && !this.escudoDestruidoIzq){
				d.setVisible(false);
				this.escudoIzq.setVida(this.escudoIzq.getVida()-1); 
				this.escudoIzq.setImgDanado(this.escudoIzq.getVida());
				if(this.escudoIzq.getVida()<=0){
					//System.out.println("He sido destruido");
					this.escudoDestruidoIzq = true;
					this.escudoIzq.setImgDestruido(false);
				}
				//repaint();
			}
			if((d.getContorno().intersects(this.escudoCentro.getContornoEscudo()))&& this.escudoCentro.getVisible() && !this.escudoDestruidoCentro){
				d.setVisible(false);
				this.escudoCentro.setVida(this.escudoCentro.getVida()-1);
				//System.out.println("La vida del escudo del centro es:" + this.escudoCentro.getVida());
				this.escudoCentro.setImgDanado(this.escudoCentro.getVida());
				if(this.escudoCentro.getVida()<=0){
					//System.out.println("He sido destruido");
					this.escudoDestruidoCentro = true;
					this.escudoCentro.setImgDestruido(false);
				}
				//repaint();
			}
			if((d.getContorno().intersects(this.escudoDer.getContornoEscudo()))&& this.escudoDer.getVisible() && !this.escudoDestruidoDer){
				d.setVisible(false);
				this.escudoDer.setVida(this.escudoDer.getVida()-1);  
				this.escudoDer.setImgDanado(this.escudoDer.getVida());
				if(this.escudoDer.getVida()<=0){
					//System.out.println("He sido destruido");
					this.escudoDestruidoDer = true;
					this.escudoDer.setImgDestruido(false);
				}
				//repaint();
			}

		}
		for (int i=0; i<this.columnaAlien; i++){
			for (int j=0; j<this.filaAlien; j++){
				if(this.continuar){
					this.aliens[i][j].moverse();
				}
				if ((this.aliens[i][j].getVisible()&&this.aliens[i][j].getContorno().intersects(this.nave.getContorno()) )|| (this.aliens[i][j].getVisible()&&this.aliens[i][j].getPosY()>=560)){
					this.finDelJuego(0);
					this.signivel = false;
				}
				if(this.aliens[i][j].getContorno().intersects(this.escudoIzq.getContornoEscudo())&&this.aliens[i][j].getVisible() && !this.escudoDestruidoIzq){
					this.escudoIzq.setVida(this.escudoIzq.getVida()-5);
					this.escudoIzq.setImgDanado(this.escudoIzq.getVida());
					if(this.escudoIzq.getVida()<=0){
						this.escudoDestruidoIzq = true;
						this.escudoIzq.setImgDestruido(false);
					}
					this.aliens[i][j].setVisible(false);
					this.marcadorTotal += 10;
					this.sobran--;
					if (this.sobran<=0){
						this.finDelJuego(1);
						this.signivel = true;
					}
					try{
						//System.out.println("Debo sonar");
						clipExplosion.start();
						clipExplosion.setFramePosition(0);

					}
					catch(Exception exs){
						exs.printStackTrace();
					}
				}
				if(this.aliens[i][j].getContorno().intersects(this.escudoCentro.getContornoEscudo())&&this.aliens[i][j].getVisible() &&!this.escudoDestruidoCentro){
					this.escudoCentro.setVida(this.escudoCentro.getVida()-15);
					this.escudoCentro.setImgDanado(this.escudoCentro.getVida());
					if(this.escudoCentro.getVida()<=0){
						this.escudoDestruidoCentro = true;
						this.escudoCentro.setImgDestruido(false);
					}
					this.aliens[i][j].setVisible(false);
					this.marcadorTotal += 10;
					this.sobran--;
					if (this.sobran<=0){
						this.finDelJuego(1);
						this.signivel=true;
					}
					try{
						//System.out.println("Debo sonar");
						clipExplosion.start();
						clipExplosion.setFramePosition(0);

					}
					catch(Exception exs){
						exs.printStackTrace();
					}
				}
				if(this.aliens[i][j].getContorno().intersects(this.escudoDer.getContornoEscudo())&&this.aliens[i][j].getVisible() && !this.escudoDestruidoDer){
					this.escudoDer.setVida(this.escudoDer.getVida()-15);
					this.escudoDer.setImgDanado(this.escudoDer.getVida());
					if(this.escudoDer.getVida()<=0){
						this.escudoDestruidoDer = true;
						this.escudoDer.setImgDestruido(false);
					}
					this.aliens[i][j].setVisible(false);
					this.marcadorTotal += 10;
					this.sobran--;
					if (this.sobran<=0){
						this.finDelJuego(1);
						this.signivel = true;
					}
					try{
						//System.out.println("Debo sonar");
						clipExplosion.start();
						clipExplosion.setFramePosition(0);

					}
					catch(Exception exs){
						exs.printStackTrace();
					}
				}

				ArrayList<Laser> lasers = this.aliens[i][j].getLaser();
				for (int k=0; k<lasers.size(); k++){
					Laser l = lasers.get(k);
					if (l.getVisible()){
						if(this.continuar){
							l.mover();
						}
					}
					else if (!l.getVisible()){
						lasers.remove(k);
					}
					if (l.getContorno().intersects(this.nave.getContorno())){
						l.setVisible(false);
						this.finDelJuego(0);
						this.signivel = false;
					}
					if(l.getContorno().intersects(this.escudoIzq.getContornoEscudo())){
						if(this.escudoIzq.getVida()>0){
							this.escudoIzq.setVida(this.escudoIzq.getVida()-1);
							this.escudoIzq.setImgDanado(this.escudoIzq.getVida());
							lasers.remove(k);
							l.setVisible(false);
							//System.out.println("Las vidas que tiene el escudo 1 son:" + this.escudoIzq.getVida());
						}else{
							this.escudoIzq.setVisible(false);
							if(this.escudoIzq.getVida()<=0){
								this.escudoIzq.setImgDestruido(false);
							}
							//System.out.println("Escudo 1 muerto");
						}
					}
					else if(l.getContorno().intersects(this.escudoCentro.getContornoEscudo())){
						if(this.escudoCentro.getVida()>0){
							this.escudoCentro.setVida(this.escudoCentro.getVida()-1);
							this.escudoCentro.setImgDanado(this.escudoCentro.getVida());
							lasers.remove(k);
							l.setVisible(false);
						}
						else{
							this.escudoCentro.setVisible(false);
							if(this.escudoCentro.getVida()<=0){
								this.escudoCentro.setImgDestruido(false);
							}
						}
					}
					else if(l.getContorno().intersects(this.escudoDer.getContornoEscudo())){
						if(this.escudoDer.getVida()>0){
							this.escudoDer.setVida(this.escudoDer.getVida()-1);
							this.escudoDer.setImgDanado(this.escudoDer.getVida());
							lasers.remove(k);
							l.setVisible(false);
						}
						else{
							this.escudoDer.setVisible(false);
							if(this.escudoDer.getVida()<=0){
								this.escudoDer.setImgDestruido(false);
							}
						}
					}
				}

				for (int m=0; m<disparos.size(); m++){
					Disparo d1 = disparos.get(m);
					if (d1.getContorno().intersects(this.aliens[i][j].getContorno()) && d1.getVisible() && this.aliens[i][j].getVisible()){
						this.aliens[i][j].setVisible(false);
						d1.setVisible(false);
						if (this.aliens[i][j].getPuntosAlien() == 3){
							this.marcadorTotal += 40;
						}
						else if (this.aliens[i][j].getPuntosAlien() == 2){
							this.marcadorTotal += 20;
						}
						else{
						this.marcadorTotal += 10;
						}
						this.sobran--;
						if (this.sobran<=0){
							this.signivel = true;
							this.finDelJuego(1);
						}
						try{
							clipExplosion.start();
							clipExplosion.setFramePosition(0);

						}
						catch(Exception exs){
							exs.printStackTrace();
						}
					}
				}
			}
		}

		if (!fin){

			this.score = "Score: " + this.marcadorTotal;
			this.repaint();

		}

	}

	private class Listener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			nave.darClick(e); //ver porque no funciona con this.nave
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(fin){
					if(!signivel){
						marcadorTotal = 0;
					}
					else if(signivel){
						//System.out.println("El siguiente nivel es:" + signivel);
						marcadorTotal = marcador;
						//System.out.println("El marcador total es:" + marcadorTotal);
						//System.out.println("El marcador es:" + marcador);
					}
					ganar = "";
					vJugar = "";
					for (int i=0; i<10; i++){
						tablaS[i] = "";
					}
					hs = "";
					for (int d=0; d<nave.getDisparos().size(); d++){
						nave.getDisparos().get(d).setVisible(false);
					}
					for(int i = 0; i < columnaAlien; i++){
						for(int j = 0; j < filaAlien; j++){
							aliens[i][j] = null;
							if(!signivel){
								//System.out.println("Se debe reiniciar");
								aliens[i][j] = new Alien1(posIXAlien, posIYAlien,1);
							}
							else{
								//System.out.println("Debe ser nuevo nivel");
								aliens[i][j] = new Alien1(posIXAlien, posIYAlien, nivel+1);
								aliens[i][j].setVel(nivel);
							}
							aliens[i][j].setPosX(posIXAlien + i*(aliens[i][j].getAlien().getWidth(null)-30) + i*espacioAlien);
							aliens[i][j].setPosY(posIYAlien + j*(aliens[i][j].getAlien().getHeight(null)-30) + j*espacioAlien);
						}
					}	
					fin = false;
					sobran = columnaAlien * filaAlien;
					timer.start();
					
					//revivir los escudos
					escudoDestruidoIzq = false;
					escudoDestruidoCentro = false;
					escudoDestruidoDer = false;
					
					//poner de nuevo la imagen original y visible
					escudoIzq.setImgDestruido(true);
					escudoIzq.setVisible(true);
					escudoCentro.setImgDestruido(true);
					escudoCentro.setVisible(true);
					escudoDer.setImgDestruido(true);
					escudoDer.setVisible(true);					
					
					//resetear las vidas a los escudos
					escudoIzq.setVida(10);
					escudoCentro.setVida(10);
					escudoDer.setVida(10);
				}
			}
			if(e.getKeyChar()=='p'){
				//System.out.println("en pausa");
				continuar=false;
				nave.pausarNave(true);
			}
			if(e.getKeyChar()=='c'){
				//System.out.println("continuar");
				continuar=true;
				nave.pausarNave(false);
			}

		}

		public void keyReleased(KeyEvent e){
			nave.soltarTecla(e);
		}
	}
	
	public void highScores(){
		try {
			this.nombreG = JOptionPane.showInputDialog("Ingresa tu nombre");
			this.highScores = new String[10][2];
			BufferedReader leer = new BufferedReader(new FileReader("HighScores.txt"));
			String nombre, score;
			String linea;
			StringTokenizer st;
			for (int i=0; i<10;i++){
				linea = leer.readLine();
				//System.out.println(linea);
				st=new StringTokenizer(linea);
				nombre = st.nextToken();
				score = st.nextToken();
				this.highScores[i][0] = nombre;
				this.highScores[i][1] = score;
			}
			leer.close();
			for (int i=0; i<10;i++){
				if (Integer.parseInt(this.highScores[i][1])<=this.marcadorTotal){
					if (i==9){
						this.highScores[i][0] = this.nombreG;
						this.highScores[i][1] = "" +  this.marcadorTotal;
						break;
					}
					else{
						this.highScores[i+1][0] = this.highScores[i][0];
						this.highScores[i+1][1] = this.highScores[i][1];
						this.highScores[i][0] = this.nombreG;
						//System.out.println(this.highScores[i][0]);
						this.highScores[i][1] = "" +  this.marcadorTotal;
						break;
					}
				}	
			}
			PrintWriter pw = new PrintWriter(new FileWriter("HighScores.txt"));
			for (int i=0; i<10;i++){
				pw.println(this.highScores[i][0] + " " + this.highScores[i][1]);
			}
			pw.close();
		} 
			
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void finDelJuego(int ver){
		if (ver == 0){
			try{
				clipExplosionTanque.start();
				clipExplosionTanque.setFramePosition(0);

			}
			catch(Exception exs){
				exs.printStackTrace();
			}
			this.highScores();
			this.hs = "High Scores";
			for (int i=0; i<10;i++){
				this.tablaS[i] = this.highScores[i][0] + " " +  this.highScores[i][1];
			}
			for (int i =0; i<this.columnaAlien; i++){
				for (int j =0; j<this.filaAlien; j++){
					this.aliens[i][j].setVisible(false);
				}
			}
			this.ganar = "Perdiste :( Suerte a la proxima";
			this.vJugar = "Para volver a jugar presiona ENTER";
			this.nivel =1;
			this.repaint();
		}
		else{
			this.nivel = this.nivel+1;
			this.ganar = "Felicidades! Siguiente nivel:" +this.nivel;
			this.vJugar = "Presiona Enter para comenzar";
			this.marcador = marcadorTotal;
			//System.out.println("El marcador total en fin del juego deberia ser:" + marcadorTotal);
			//System.out.println("El marcador en fin del juego deberia ser:" + marcador);
			this.repaint();
		}
		this.fin = true;
		timer.stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	@Override
	public void keyTyped(KeyEvent e) {
		

	}

	@Override
	public void run() {
		while(true){
			//System.out.println("Debo cambiar");
			try {
				for (int i=0; i<this.columnaAlien; i++){
					for(int j=0; j<this.filaAlien; j++){
						this.aliens[i][j].cambiarImg(this.aliens[i][j].getAlien());
						this.repaint();
					}
				}
				//System.out.println("Cambio");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/*
	# INTENTO DE THREAD
	@Override
	public void run() {
		if(this.pausa){
			try{
				this.hilo.wait();
			}
			catch(Exception e){

			}
		}
		else if(this.continuar){
			try{
				this.hilo.start();
			}
			catch(Exception e){
			}
		}

	}
	 */

}

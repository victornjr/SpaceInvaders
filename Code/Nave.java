
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;


public class Nave {
	private Image nave;
	private int posX,
	posY,
	movDir; //cambiar dx
	private final int mover = 200;
	private ArrayList<Disparo> disparos;
	private boolean disparar,
	recarga;
	//para disparos tanque
	private File disparoTanque;
	private AudioInputStream ais;
	private Clip clipTanque;
	private boolean pausa;

	public Nave(){
		this.nave = new ImageIcon("../Images/Nave.png").getImage();
		posY = 550;
		posX = 250-nave.getWidth(null)/2;
		movDir = 0;
		disparos = new ArrayList<Disparo>();
		disparar = true;
		recarga = true;
	}
	public boolean getRecarga(){
		return this.recarga;
	}
	public void setRecarga(boolean recarga){
		this.recarga = recarga;
	}
	public int getPosX(){
		return this.posX;
	}
	public int getPosY(){
		return this.posY;
	}
	public Image getNave(){
		return this.nave;
	}
	public ArrayList<Disparo> getDisparos(){
		return this.disparos;
	}

	public void limitePantalla(){ // Toda la logica la pondre aca
		if((this.posX>0 && movDir<0) || (this.posX<450 && movDir>0))
			this.posX += movDir;
	}
	public void pausarNave(boolean pausa){
		this.pausa = pausa;
	}

	public void darClick(KeyEvent e){
		int tecla = e.getKeyCode();

		if(tecla == KeyEvent.VK_RIGHT){
			if(!this.pausa){
				this.movDir = mover;
			}
		}
		if(tecla == KeyEvent.VK_LEFT){
			if(!this.pausa){
				this.movDir = mover * -1;
			}

		}
		if(tecla == KeyEvent.VK_SPACE && this.disparar && this.recarga)
		{
			if(!pausa){
				try{
					disparoTanque = new File("../Sound/shoot.wav");
					ais = AudioSystem.getAudioInputStream(disparoTanque);
					clipTanque = AudioSystem.getClip();
					clipTanque.open(ais);
				}
				catch(Exception es){
					es.printStackTrace();
				}
				try{
					clipTanque.start();
					clipTanque.setFramePosition(0);

				}
				catch(Exception exs){
					exs.printStackTrace();
				}
				this.disparos.add(new Disparo(this.posX + (this.nave.getWidth(null)-40)/2, this.posY));
				this.disparar = false;
			}
		}
	}

	public void soltarTecla(KeyEvent e){
		int tecla = e.getKeyCode();

		if(tecla == KeyEvent.VK_LEFT && movDir < 0){
			movDir = 0;
		}
		if(tecla == KeyEvent.VK_RIGHT && movDir > 0){
			movDir = 0;
		}
		if(tecla == KeyEvent.VK_SPACE)
			disparar = true;
	}

	public Rectangle getContorno(){
		return new Rectangle(this.posX, this.posY, this.nave.getWidth(null)-40, this.nave.getHeight(null)-40);
	}

}

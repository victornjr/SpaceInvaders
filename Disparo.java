
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;


public class Disparo {
	protected Image disparo; 
	protected int posX,
	posY;
	private final int vel = 5;
	private boolean visible;


	public Disparo(int x, int y){
		this.posX = x;
		this.posY = y;
		this.disparo= new ImageIcon("Disparo-buenos.png").getImage();
		this.visible = true;
	}

	public int getPosX(){
		return this.posX;
	}
	public int getPosY(){
		return this.posY;
	}
	public Rectangle getContorno(){
		return new Rectangle(this.posX, this.posY, this.disparo.getWidth(null)-35, this.disparo.getHeight(null)-118);
	}
	public boolean getVisible(){
		return this.visible;
	}
	public Image getDisparo(){
		return this.disparo;
	}
	public void setImagenDisparo(Image disparo){
		this.disparo = disparo;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void mover(){
		this.posY -= vel+25;
		if (this.posY < 0){
			this.visible = false;
		}
	}	



}

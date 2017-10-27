
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Aliens {
	private Image alien;
	private int posX,
	posY,
	direc,
	moverX,
	moverY,
	bajo,
	disLaser;
	private final int rango;
	private double vel;
	private Random random;
	private ArrayList<Laser> lasers;
	private boolean visible,
					bajar;
	// Para agregar diferentes aliens poner que reciba int x, int y, int tipo alien
	public Aliens(int x, int y, int nivel){
		if(nivel%2==0){
			//System.out.println("Se uso la primer imagen");
			this.alien = new ImageIcon("Alien2.1.png").getImage();
		}
		else if(nivel%3==0){
			//System.out.println("Se uso la segunda imagen");
			this.alien = new ImageIcon("Alien3.1.png").getImage();
		}
		else{
			//System.out.println("Se uso la tercera imagen");
			this.alien = new ImageIcon("Alien1.1.png").getImage();
		}	
		this.posX = x;
		this.posY = y;
		this.vel = 1;
		this.rango = 100;
		this.moverX =0;
		this.moverY = 0;
		this.direc = 1; // 1 = derecha, -1 = izquierda
		this.visible = true;
		this.bajar = false;
		this.bajo = 0;
		this.random = new Random();
		this.disLaser = 700; //1 de 40
		this.lasers = new ArrayList<Laser>();
	}

	public int getPosX(){
		return this.posX;
	}
	public int getPosY(){
		return this.posY;
	}
	public void setPosX(int x){
		this.posX = x;
	}
	public void setPosY(int y){
		this.posY = y;
	}
	public void setAlien(Image alien){
		this.alien = alien;
	}

	public Image getAlien(){
		return this.alien;
	}

	public double getVel(){
		return this.vel;
	}
	public void setVel(int nivel){
		this.vel = vel+(nivel/3);
	}

	public boolean getVisible(){
		return this.visible;
	}
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public Rectangle getContorno(){
		return new Rectangle(this.posX, this.posY, this.alien.getWidth(null)-42, this.alien.getHeight(null)-42);
	}

	public void moverse(){
		if (this.moverX > this.rango){
			this.moverX = 0;
			this.bajar = true;
			this.direc = this.direc*-1;
		}
		if (this.bajar){
			this.posY++;
			this.moverY+=5;
			if (this.moverY > this.alien.getHeight(null)){
				this.bajar = false;
				this.moverY = 0;
				this.bajo++;
				if (this.bajo%3 == 0){
					this.vel++;
				}
			}
		}
		else{
			this.posX += this.vel * this.direc;
			this.moverX += this.vel;
		}
		if(this.getVisible()){
			if ((this.random.nextInt()%this.disLaser == 1 && this.posY < 400) || (this.random.nextInt()%this.disLaser == 2 && this.posY < 400) || (this.random.nextInt()%this.disLaser == 3 && this.posY < 400)){
				this.lasers.add(new Laser(this.posX + 5,this.posY + 10));
			}
		}
	}

	public ArrayList<Laser> getLaser(){
		return this.lasers;
	}
}

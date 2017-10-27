
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Alien1 extends Aliens{
	protected Image imagen1Alien1,
					imagen2Alien1,
					imagen1Alien2,
					imagen2Alien2,
					imagen1Alien3,
					imagen2Alien3;
	private int queImagenEs;
	
	public Alien1(int x, int y,int nivel) {
		super(x, y,nivel);
		if(nivel%2==0){
			queImagenEs=2;
			this.imagen1Alien2 = new ImageIcon("Alien2.1.png").getImage();
			this.imagen2Alien2 = new ImageIcon("Alien2.2.png").getImage();
		}
		else if(nivel%3==0){
			queImagenEs=3;
			this.imagen1Alien1 = new ImageIcon("Alien1.1.png").getImage();
			this.imagen2Alien1 = new ImageIcon("Alien1.2.png").getImage();
		}
		else{
			queImagenEs=1;
			this.imagen1Alien3 = new ImageIcon("Alien3.1.png").getImage();
			this.imagen2Alien3 = new ImageIcon("Alien3.2.png").getImage();
		}	
	}

	public void cambiarImg(Image alien){
		if(alien == this.imagen1Alien1){
			//System.out.println("Imagen 2 alien 1");
			this.setAlien(this.imagen2Alien1);
		}
		else if(alien == this.imagen2Alien1){
			//System.out.println("Imagen 1 alien 1");
			this.setAlien(this.imagen1Alien1);
		}
	
		if(alien == this.imagen1Alien2){
			//System.out.println("Imagen 2 alien 2");
			this.setAlien(this.imagen2Alien2);
			//System.out.println("Cambio Alien 2 : "+ this.getVisible());
		}
		else if(alien == this.imagen2Alien2){
			//System.out.println("Imagen 1 alien 2");
			this.setAlien(this.imagen1Alien2);
			//System.out.println("Cambio Alien 2 : "+ this.getVisible());
		}
		if(alien == this.imagen1Alien3){
			//System.out.println("Imagen 2 alien 3");
			this.setAlien(this.imagen2Alien3);
			//System.out.println("Cambio Alien 2 : "+ this.getVisible());
		}
		else if(alien == this.imagen2Alien3){
			//System.out.println("Imagen 1 alien 3");
			this.setAlien(this.imagen1Alien3);
			//System.out.println("Cambio Alien 2 : "+ this.getVisible());
		}
		
	}
	public int getPuntosAlien(){
		return this.queImagenEs;
	}
	
}
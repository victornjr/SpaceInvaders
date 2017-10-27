
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Escudo {
	protected Image escudo;
	protected Image imgInicial;
	protected Image destruido;
	protected Image danado;
	private int posX,
				posY;
	private int vida = 20;
	private boolean visible;

	public Escudo(){
		this.imgInicial = new ImageIcon("../Images/Escudo.png").getImage();
		this.escudo = this.imgInicial;
		this.posX = 50;
		this.posY = 410;
		this.visible = true;

	}
	public Image setImgDestruido(boolean visible){
		if(!visible){
			this.destruido = new ImageIcon("../Images/EscudoDestruido.png").getImage();

			this.escudo = this.destruido;
		}
		else if(visible){
			this.escudo = this.imgInicial;
		}
		return this.escudo;
	}
	public Image setImgDanado(int vida){
		//System.out.println("La vida es" + vida);
		if(vida<=4){
			//System.out.println("Mi vida es: " + vida);
			this.danado = new ImageIcon("../Images/Escudo5Destruido.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida <= 7){
			//System.out.println("Mi vida es: " + vida);
			this.danado = new ImageIcon("../Images/Escudo4Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida <=10){
			//System.out.println("Mi vida es: " + vida);
			this.danado = new ImageIcon("../Images/Escudo3Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida <= 14){
			//System.out.println("Mi vida es: " + vida);
			this.danado = new ImageIcon("../Images/Escudo2Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida <= 18){
			//System.out.println("Mi vida es: " + vida);
			this.danado = new ImageIcon("../Images/Escudo1Dano.png").getImage();
			this.escudo = this.danado;
		}
		return this.danado;
	}


	public Image getEscudo(){
		return this.escudo;

	}
	public int getX(){
		return this.posX;
	}
	public int getY(){
		return this.posY;
	}

	public Rectangle getContornoEscudo(){
		return new Rectangle(this.posX, this.posY, this.escudo.getWidth(null)-30, this.escudo.getHeight(null));
	}
	public int getVida(){
		return this.vida;
	}

	public void setVida(int vida){
		this.vida = vida;
	}
	public boolean getVisible(){
		return this.visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

}

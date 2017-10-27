
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class EscudoCentro extends Escudo{

	public Rectangle getContornoEscudo(){
		return new Rectangle(this.getX()+150, this.getY(), this.escudo.getWidth(null)-30, this.escudo.getHeight(null));
	}
	/*
	public Image setImgDanado(int vida){
		if(vida<10){
			this.danado = new ImageIcon("Escudo1Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida < 8){
			this.danado = new ImageIcon("Escudo2Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida < 6){
			this.danado = new ImageIcon("Escudo3Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida < 3){
			this.danado = new ImageIcon("Escudo4Dano.png").getImage();
			this.escudo = this.danado;
		}
		else if(vida<2){
			this.danado = new ImageIcon("Escudo5Destruido.png").getImage();
			this.escudo = this.danado;
		}
		
		return this.escudo;
	}*/
}

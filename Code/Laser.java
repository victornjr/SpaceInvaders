
import javax.swing.ImageIcon;

public class Laser extends Disparo{

	public Laser(int x, int y) {
		super(x, y);
		this.disparo = new ImageIcon("../Images/Disparo-malos.png").getImage();
	}

	public void mover(){
		this.posY += 8;
	}
}

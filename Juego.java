
import javax.swing.JFrame;

public class Juego extends JFrame{
	private int windowSizeLeft;
	private int windowSizeTop;
	public Juego(){
		super("Space Invaders");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 10);
		Pantalla juego = new Pantalla();
		this.add(juego);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		
	}
}


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PantallaLogin extends JPanel implements ActionListener{

	private Image fondo;

	public PantallaLogin(){
		super();
		this.setPreferredSize(new Dimension(500,600));
		this.fondo = new ImageIcon("../Images/PantallaLogin.png").getImage();


	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.fondo, 0, 0, this.getWidth(),this.getHeight(),this);
		repaint();
	}


}

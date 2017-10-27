
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
//para los soniditos
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PruebaLogin extends JFrame implements KeyListener{

	//para disparos tanque
		private File bgMusic;
		private AudioInputStream ais;
		private Clip clip;

	public PruebaLogin(){
		super("Space Invaders!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PantallaLogin pl = new PantallaLogin();
		this.setLocation(300, 10);
		this.setResizable(false);
		this.add(pl);
		try{
			bgMusic = new File("../Sound/Space Invaders OST - Pluto.wav");
			ais = AudioSystem.getAudioInputStream(bgMusic);

			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception es){
			es.printStackTrace();
		}
		try{
			clip.start();
			clip.setFramePosition(0);
		}
		catch(Exception exs){
			exs.printStackTrace();
		}
		this.addKeyListener(this);
		this.pack();
		this.setVisible(true);


	}

	public static void main(String[] args) {
		PruebaLogin pr = new PruebaLogin();

	}


	@Override
	public void keyPressed(KeyEvent e) {


	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("Entra" + e.getKeyChar());
		if(e.getKeyChar() == 'p'){
			this.dispose();
			Juego j = new Juego();
		}

	}
}

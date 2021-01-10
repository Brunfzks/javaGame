package com.abstudios.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.abstudios.entities.Bullet;
import com.abstudios.entities.BulletShoot;
import com.abstudios.entities.Enemy;
import com.abstudios.entities.Entity;
import com.abstudios.entities.Player;
import com.abstudios.graficos.Spritesheet;
import com.abstudios.graficos.Ui;
import com.abstudios.world.Camera;
import com.abstudios.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener  {

	
	private static final long serialVersionUID = 935073154186464789L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public final static int WIDTH = 360;
	public final static int HEIGHT = 220;
	private final int SCALE = 3;
	
	private int CUR_LEVEL = 1, MAX_CUR = 2;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemis;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	public static World world;
	
	public static Random rand; 
	
	public static Player player;
	
	public Ui[] ui;

	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	
	public Game() {
			
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemis = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet32.png");
		elementsUi();
		player = new Player(0, 0, 32, 32, spritesheet.getSprite(192, 0, 32, 32));
		entities.add(player);
		world = new World("/level1.png");
	}
	
	public void initFrame() {
		
		frame = new JFrame("Meu jogo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	public void elementsUi() {
		ui = new Ui[3];
		ui[0] = new Ui(spritesheet.getSprite(192, 96, 16, 16));
		ui[1] = new Ui(spritesheet.getSprite(192, 112, 16, 16));
		ui[2] = new Ui(spritesheet.getSprite(192 + 16, 96, 16, 16));
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gameState == "NORMAL"){
			//Previne do jogar apertar enter e reiniciar o jogo no meio do jogo
			this.restartGame = false;
			for(int i = 0; i < ui.length;i++) {
				ui[i].tick();
			}
			
			for(int i = 0; i < entities.size(); i++) {
				
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				
				bullets.get(i).tick();
			}
			
			if(enemis.size() == 0) {
				
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_CUR) {
					CUR_LEVEL = 1;
				}
				
				String newWorld = "level"+CUR_LEVEL+".png";
				System.out.println(newWorld);
				World.restartGame(newWorld);
			}
		}else if(gameState == "GAME_OVER"){
			this.framesGameOver++;
			if(this.framesGameOver == 30){
				this.framesGameOver = 0;
				if(this.showMessageGameOver){
					this.showMessageGameOver = false;
				}else{
					this.showMessageGameOver = true;
				}
				if(restartGame){
					this.restartGame = false;
					gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level"+CUR_LEVEL+".png";
					//System.out.println(newWorld);
					World.restartGame(newWorld);
				}
			}
		}else if(gameState =="MENU"){
			
		}
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*Renderiza��o do Jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			e.render(g);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			
			bullets.get(i).render(g);
		}
		uiRender(g);
		
		g.dispose();	
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState== "GAME_OVER"){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			renderizaStringPixelada(g, "GAME OVER", (WIDTH * SCALE) /2 -70, (HEIGHT*SCALE) / 2, 50);
			if(showMessageGameOver)
				renderizaStringPixelada(g, ">PRESSIONE ENTER PARA REINICIAR<", (WIDTH * SCALE) /2 -300, (HEIGHT*SCALE) / 2 + 60, 40);
		}
		bs.show();
	}
	
	public void uiRender(Graphics g) {
		
		int i = 1, x = 0, y = 20;
		for(i = 1; i <= (int)Ui.quantidadeCoracao; i++) {
			
			
			ui[0].render(g, i*17, 4);
		}
		
		if(Ui.primeiraDecimal(Ui.quantidadeCoracao) <= 5 && Ui.primeiraDecimal(Ui.quantidadeCoracao) != 0) {
			ui[1].render(g, i*17, 4);
			
		}else if(Ui.primeiraDecimal(Ui.quantidadeCoracao) > 5) {
				ui[0].render(g, i*17, 4);
			}
		
		for(int indice = 0; indice < player.ammo;indice++) {
			
			if( x==10) {
				 x=0;
			}
			
			if(indice >=10) {
				 y = 40;
			}
			if(indice >=20) {
				y = 60;
			}

			ui[2].render(g, x*10, y);
			x++;
		}
	}
	
	public void renderizaStringPixelada(Graphics g, String texto, int x, int y, int tamanhoFont) {
		g.setColor(new Color(191, 181, 35));
		g.setFont(new Font("arial", Font.BOLD, tamanhoFont));
		g.drawString( texto, x, y);
		
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	
	public void keyTyped(KeyEvent e) {
		
	}


	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||  e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			
			System.out.println("Atirando");
			player.shoot = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			restartGame = true;
		}

	}


	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||  e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = false;
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		player.mouseShoot = true;
		player.mx = (e.getX() / 3);
		player.my = (e.getY() / 3);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

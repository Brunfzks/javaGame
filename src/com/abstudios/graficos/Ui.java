package com.abstudios.graficos;

 
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import com.abstudios.main.Game;

	
public class Ui {
	
	public  int x, y;
	public static double quantidadeCoracao;
	public  BufferedImage sprite ;
	
	public static int frames = 0, maxframes = 8, index = 0, maxIndex = 3;
	
	public Ui(BufferedImage sprite) {
		
		this.sprite = sprite;
	}
	
	public void tick() {
		
		//coração
		quantidadeCoracao = Game.player.life/10;
		
	}
	
	public int getX() {
		
		return this.x;
	}
	
	public int getY() {
		
		return this.y;
	}
	
	public static int primeiraDecimal(double valor) {
	    return ((int)(valor * 10)) % 10;
	}
	
	public void render(Graphics g, double x, int y) {
		
		g.drawImage(sprite, (int)x, y, null);
	}
	
	
}


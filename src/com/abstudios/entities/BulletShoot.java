package com.abstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;
import com.abstudios.world.Camera;

public class BulletShoot extends Entity{
	
	private double dx, dy;
	private double speed = 3;
	
	private int life = 50, curentlife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, double speed) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.speed = speed;
	}
	
	public void tick() {
		
		x+=dx*speed;
		y+=dy*speed;
		curentlife ++;
		if(curentlife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}
}

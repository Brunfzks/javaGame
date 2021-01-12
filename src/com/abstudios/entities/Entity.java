package com.abstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;
import com.abstudios.world.Camera;
import com.abstudios.entities.Entity;


public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(192, 0, 32, 32);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(224, 0, 32, 32);
	public static BufferedImage WEAPON_LEFT = Game.spritesheet.getSprite(224, 32, 32, 16);
	public static BufferedImage WEAPON_RIGHT = Game.spritesheet.getSprite(224, 48, 32, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(192, 32, 32, 32);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(64, 128, 32, 32);

	public  int maskx;
	public int masky;
	public  int maskw;
	public  int maskh;
	
	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
	
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
	}
	
	public void tick() {
		
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z){
			return true;
		}
		return false;
	}
	
	public void setX(int x) {
		
		this.x = x;
	}
	
	public void setY(int y) {
		
		this.y = y;
	}
	
	
	public int getX() {
		
		return (int) this.x;
	}
	
	public int getY() {
		
		return (int) this.y;
	}
	
	public int getWidth() {
		
		return this.width;
	}
	
	public int getHeight() {
		
		return this.height;
	}
	
	public void destroySelf() {
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y, null);
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y,maskw, maskh);
	}
}

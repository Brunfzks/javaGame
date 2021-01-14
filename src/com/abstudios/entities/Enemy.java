package com.abstudios.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.abstudios.graficos.Spritesheet;
import com.abstudios.main.Game;
import com.abstudios.world.Camera;
import com.abstudios.world.World;

public class Enemy extends Entity{

	private double speed = 0.8;
	
	private int maskx = 0, masky = 16, maskw = 20, maskh = 20;
	
	public static int frames = 0, maxframes = 120, index = 0, maxIndex = 6;
	
	public int rightDir = 0, leftDir = 1;
	public int dir = rightDir;

	public boolean sawPlayer = false;
	
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	
	private double life = 5;
	
	public Enemy(int x, int y, int width, int height) {
		super(x, y, width, height, null);
		
		rightEnemy = new BufferedImage[6];
		leftEnemy = new BufferedImage[6];
		
		for(int i = 0; i < maxIndex; i++) {
			
			rightEnemy[i] = Game.spritesheet.getSprite(64 + (32*i), 192, 32, 32);
			leftEnemy[i] = Game.spritesheet.getSprite(64 + (32*i), 192+32, 32, 32);
		}
	}
	
	
	public void tick() {
		
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 150)
			this.sawPlayer = true;	

		if(sawPlayer){
			if(isCollidingWithPlayer() == false) {
				if(Game.rand.nextInt(100) < 60) {
					if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
							&& !isColliding((int)(x+speed), this.getY())) {
						
						dir = rightDir;
						x+= speed;
					}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) 
							&& !isColliding((int)(x-speed), this.getY())) {
						
						dir = leftDir;
						x -= speed;
					}
					
					if((int) y > Game.player.getY() && World.isFree(this.getX(), (int)(y - speed))
							&& !isColliding(this.getX(), (int)(y - speed))) {
						
						y-= speed;
					}else if((int) y < Game.player.getY() && World.isFree(this.getX(), (int)(y + speed))
							&& !isColliding(this.getX(), (int)(y + speed))) {
						
						y+= speed;
					}	
					
				}
			}else {
				if(Game.rand.nextInt(100) < 90) {
					if(!Game.player.takeDamage) {
						Game.player.life -= 5;
						Game.player.takeDamage = true;
						System.out.println(Game.player.life);
					}
					if(Game.player.life <= 0) {
						Game.gameState="GAME_OVER";
					}
				}	
			}
		}
		frames++;
		if(frames == maxframes) {
			frames = 0;
			index++;
			if(index >= maxIndex) {
				index = 0;
			}
		}
		
		collidingBullet();
		
		if(life <= 0) {
			destroySelf();
		}
			
	}
	
	public void destroySelf() {
		
		Game.enemis.remove(this);
		Game.entities.remove(this);
		return;
	}
	
	public void collidingBullet() {
		
		for(int i = 0; i < Game.bullets.size(); i++) {
			
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				
				if(Entity.isColliding(e, this)) {
					
					life -= 2.5;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isCollidingWithPlayer() {
		if(this.getZ() == Game.player.z){
			Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
			Rectangle player = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY() + Game.player.masky, Game.player.maskw, Game.player.maskh);	
			
			return enemyCurrent.intersects(player);
		}
		return false;
		
	}
	
	public boolean isColliding(int xnext, int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for(int i = 0; i < Game.enemis.size(); i ++) {
			
			Enemy e = Game.enemis.get(i);
			
			if(e == this) 
				continue;
			
			Rectangle tragetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			
			if(enemyCurrent.intersects(tragetEnemy))
				return true;
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		
		if(dir == rightDir) {
			
			g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == leftDir) {
			
			g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}

}

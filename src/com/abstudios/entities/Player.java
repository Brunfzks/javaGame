package com.abstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;
import com.abstudios.world.Camera;
import com.abstudios.world.World;
import com.abstudios.entities.Entity;

public class Player extends Entity {

	public boolean right = false, left = false, up = false, down = false;
	public int rightDir = 0, leftDir = 1;
	public int dir = rightDir;
	public double speed = 2.5;
	
	public  int maskx = 5;
	public int masky = 14;
	public  int maskw = 20;
	public  int maskh = 20;
	
	public int frames = 0, maxframes = 8;
	public int damageFrame = 0;
	public int index = 0;
	public int maxIndex = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] idleRightPlayer;
	private BufferedImage[] idleLeftPlayer;
	private BufferedImage[] gunRightPlayer;
	private BufferedImage[] gunLeftPlayer;
	
	public double life = 50;
	public int maxLife = 50;
	public int ammo = 0;
	public int maxAmmo = 30;
	public int mx, my;

	public boolean jump = false;
	public int z = 0;
	public int jumpFrames = 50, jumpCur = 0;
	public boolean isJumping = false;
	public int jumpSpeed = 2;
	public boolean jumpUp = false, jumpDown = false;
	
	public boolean takeDamage = false;
	public boolean hasGun = false;
	public double isImortal = 0;
	
	public boolean shoot = false, mouseShoot = false;
	
	private boolean moved = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		idleRightPlayer = new BufferedImage[4];
		idleLeftPlayer = new BufferedImage[4];
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		gunRightPlayer = new BufferedImage[4];
		gunLeftPlayer = new BufferedImage[4];
		
		for(int i = 0; i < maxIndex; i++) {
			
			idleRightPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 0, 32, 32);
			idleLeftPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 32, 32, 32);
			
			rightPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 64, 32, 32);
			leftPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 98, 32, 32);
			
			gunRightPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 128, 32, 32);
			gunLeftPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 128+32, 32, 32);
		}
	}

	public void tick() {

		if(jump){
			if(isJumping == false){
				jump = false;
				jumpUp = true;
				isJumping = true;
			}
		}
		if(isJumping == true){
			if(jumpUp){
				jumpCur+=jumpSpeed;
			}else if(jumpDown){
				jumpCur-=jumpSpeed;
				if(jumpCur <=0){
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			
			z = jumpCur;
			if(jumpCur >= jumpFrames){
				jumpUp = false;
				jumpDown = true;
			}
		}
		
		moved = false;
		if(right && World.isFreePlayer( (int)(x + speed),this.getY())) {
			 
			moved = true;
			dir = rightDir;
			x += speed;
		}else if(left && World.isFreePlayer((int)(x - speed),this.getY())) {
			
			moved = true;
			dir = leftDir;
			x -= speed;
		}
		
		if(up && World.isFreePlayer(this.getX(),(int)(y - speed))) {
			
			moved = true;
			y-= speed;
		}else if(down && World.isFreePlayer(this.getX(),(int)(y + speed))) {
			
			moved = true;
			y+= speed;
		}
		
		
		if(takeDamage) {
			this.damageFrame++;
			this.isImortal++;
			if(this.damageFrame == 50) {
				this.damageFrame = 0;
				takeDamage = false;
				this.isImortal = 0;
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
		
		checkColisionLifePack();
		checkColisioAmmo();
		checkColisionGun();
		
		if(shoot || mouseShoot) {
			
			double angle = Math.atan2(my - (this.getY() + 16 - Camera.y), mx - (this.getX() + 16 - Camera.x));
			if(shoot)
				angle = 0;
			shoot = false;
			mouseShoot = false;
			System.out.println(my);
			
			if(hasGun && ammo > 0) {
				ammo --;
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int px = 16;
				int py = 16;
				double speed = 0;
					
				if(dir == rightDir) {
					if(moved) {
						
						px = 27;
						dx = 1;
						py = 20;
						speed = 4.5;
					}else {
						speed = 3;
						px = 23;
						dx = 1;
						py = 22;
					}
					
				}else {
					
					if(moved) {
						
						px = 5;
						dx = -1;
						py = 18;
						speed = 4.5;
					}else {
						speed = 3;
						px = 5;
						dx = -1;
						py = 22;
					}
				}
			
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, dy, speed); 
				Game.bullets.add(bullet);
			}
			if(Game.player.life  <=0){
				Game.gameState="GAME_OVER";
			}
		}
		Camera.x =   Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y =   Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkColisioAmmo() {
			
			for(int i = 0; i < Game.entities.size(); i++) {
				
				Entity atual = Game.entities.get(i);
				if(atual instanceof Bullet) {
					
					if(Entity.isColliding(this, atual)) {
						ammo += 15;
						Game.entities.remove(atual);
					}
				}
			}
		}
	
	public void checkColisionLifePack() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifePack) {
				
				if(Entity.isColliding(this, atual)) {
					life += 10;
					if(life >= maxLife)
						life = maxLife;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkColisionGun() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				
				if(Entity.isColliding(this, atual)) {
					hasGun = true;
					//System.out.println("Pego arma");
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void moved(Graphics g) {
		if(hasGun) {
			if(dir == rightDir && moved) {
				
				g.drawImage(gunRightPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y - z,null);
				g.drawImage(Entity.WEAPON_RIGHT, this.getX() + 5 - Camera.x, this.getY() + 12 - Camera.y - z, null);
			}else if(dir == leftDir && moved) {
				
				g.drawImage(gunLeftPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
				//arma
				g.drawImage(Entity.WEAPON_LEFT, this.getX() - Camera.x, this.getY() + 16 - Camera.y -z, null);
			}

			if(dir == rightDir && moved == false) {
				
				g.drawImage(idleRightPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
				g.drawImage(Entity.WEAPON_RIGHT, this.getX() - 1 - Camera.x, this.getY() + 14 - Camera.y, null);
			}else if(dir == leftDir && moved == false) {
				
				g.drawImage(idleLeftPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
				g.drawImage(Entity.WEAPON_LEFT, this.getX() + 3 - Camera.x, this.getY() + 20 - Camera.y -z, null);
			}
		}else {
			if(dir == rightDir && moved) {
				
				g.drawImage(rightPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
			}else if(dir == leftDir && moved) {
				
				g.drawImage(leftPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
			}

			if(dir == rightDir && moved == false) {
				
				g.drawImage(idleRightPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
			}else if(dir == leftDir && moved == false) {
				
				g.drawImage(idleLeftPlayer[index], (int)this.getX() - Camera.x, (int)this.getY() - Camera.y -z,null);
			}
		}
		if(isJumping){
			g.setColor(Color.black);
			g.fillOval((int)this.getX() - Camera.x +12, (int)this.getY() - Camera.y + 30, 12, 12);
		}
		
	}
	
	
	
	public void render(Graphics g) {
		
		if(takeDamage && isImortal < 30) {
			
			if(Game.rand.nextInt(100) < 70) {
				moved(g);
			}	
		}else {
			moved(g);
		}
		
		
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
	
}

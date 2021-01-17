package com.abstudios.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.abstudios.entities.Amo;
import com.abstudios.entities.Bullet;
import com.abstudios.entities.Enemy;
import com.abstudios.entities.Entity;
import com.abstudios.entities.LifePack;
import com.abstudios.entities.Player;
import com.abstudios.entities.Weapon;
import com.abstudios.graficos.Spritesheet;
import com.abstudios.main.Game;
import com.abstudios.world.Tile;
import com.abstudios.world.World;

import com.abstudios.world.FloorTile;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	public static final int miniMapWidth = 20;
	public static final int miniMapHeigth = 20;

	public World(String path) {
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			System.out.println(path);
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_COMUM);
					switch (pixels[xx + (yy*map.getWidth())]){
						
						case 0xff000203:
							//Grama Comum
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_COMUM);
							break;
						case 0xff042f02:
							//Grama Horizontal Sup
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_HORIZONTAL_SUPERIOR);
							break;
						case 0xff0e8208:
							//Grama horizontal Inf
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_HORIZONTAL_INFERIOR);
							break;
						case 0xff074b03:
							//Grama Vertical Esquerda
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_VERTICAL_ESQUERDA);
							break;
						case 0xff0b6806:
							//Grama Vertical Direita		
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_VERTICAL_DIREITA);
							break;
						case 0xff725be5:
							//Grama Canto superior esquerdo
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_CANTO_SUPERIOR_ESQUERDO);
							break;	
						case 0xff564b90:
							//Grama Canto superior Direito
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_CANTO_SUPERIOR_DIREITO);
							break;
						case 0xff887dbb:
							//Grama Canto inferior Direito
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_CANTO_INFERIOR_DIREITO);
							break;
						case 0xffa89de3:
							//Grama Canto inferior esquerdo
							
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_CANTO_INFERIOR_ESQUERDO);
							break;
						case 0xffffffff:
							//Barranco Horizontal Sup
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_HORIZONTAL_SUPERIOR);
							break;
						case 0xffffff01:
							//Barranco horizonta inf
								
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_HORIZONTAL_INFERIOR);
							break;
						case 0xff01ffff:
							//Barranco vertical Direita
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_VERTICAL_DIREITA);
							break;
						case 0xffff01ff:
							//Barranco vertical Esquerda
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_VERTICAL_ESQUERDA);
							break;
						case 0xff4b4d4b:
							//Barranco Canto Superior Esquerdo
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_CANTO_SUPERIOR_ESQUERDO);
							break;
						case 0xff444544:
							//Barranco Canto SUPERIOR Direito
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_CANTO_SUPERIOR_DIREITO);
							break;
						case 0xff373937:
							////Barranco Canto inf Direito
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_CANTO_INFERIOR_DIREITO);
							break;
						case 0xff343634:
							//Barranco Canto inf esquerdo
							
								tiles[xx + (yy*WIDTH)] = new WallTileNoJump(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BARRANCO_CANTO_INFERIOR_ESQUERDO);
							break;
						case 0xff9bd5f7:
							// Parede
							
								tiles[xx + (yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PAREDE_HORIZONTAL);
							break;
						case 0xffc20e0e:
							//Arma
								Game.entities.add(new Weapon(xx*TILE_SIZE, yy*TILE_SIZE, WIDTH, HEIGHT, Entity.WEAPON_EN));
							break;
						case 0xff38fc07:
							//Muni�ao
							Bullet ammo = new Bullet(xx*TILE_SIZE, yy*TILE_SIZE, WIDTH, HEIGHT, Bullet.BULLET_EN);
							ammo.setMask(4, 8, 16, 16);
							Game.entities.add(ammo);
							break;
						case 0xfffcf007:
							//Player
							
								Game.player.setX(xx*TILE_SIZE);
								Game.player.setY(yy*TILE_SIZE);
							break;
						case 0xff07fce8:
							//Enemys
								Enemy en = new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, WIDTH, HEIGHT); 
								en.setMask(0, 0, 32, 32);
								Game.entities.add(en);
								Game.enemis.add(en);
							break;
						case 0xffc307fc:
							//Vida
								
								LifePack pack = new LifePack(xx*TILE_SIZE, yy*TILE_SIZE, WIDTH, HEIGHT, LifePack.LIFEPACK_EN);
								pack.setMask(4, 8, 16, 16);
								Game.entities.add(pack);	
							break;
						default:
							//floor
								tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_GRAMA_COMUM);
							break;
					}
	
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext/TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}

	public static boolean isFreePlayer(int xnext, int ynext) {
		
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y2 = ynext/TILE_SIZE;
		
		int x3 = xnext/TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -2)/TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -2)/TILE_SIZE;
		
			 if(!(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile) &&
				
				!(tiles[x1 + (y1*World.WIDTH)] instanceof WallTileNoJump ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTileNoJump  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTileNoJump  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTileNoJump)){
					return true;
				}

				if(Game.player.z > 0 && (tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile  ||
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile  ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)){
					return true;
				}
				return false;
	}
	
	
	

	public static void restartGame(String level) {

		double life = 50;
		int ammo = 0;

		if(Game.CUR_LEVEL == Game.MAX_CUR){
			 life = Game.player.life;
			 ammo = Game.player.ammo;
		}
		
		Game.entities = new ArrayList<Entity>();
		Game.enemis = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet32.png");
		Game.player = new Player(0, 0, 32, 32, Game.spritesheet.getSprite(192, 0, 32, 32), 1);
		Game.player.ammo = ammo;
		Game.player.life = life;
		Game.entities.add(Game.player);
		System.out.println(level);
		Game.world = new World("/"+level);
	}

	public static void renderMiniMap(){
		for(int i = 0; i < Game.miniMapaPixels.length; i++){
			Game.miniMapaPixels[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++){
			for(int yy = 0; yy < HEIGHT; yy++){
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile){
					Game.miniMapaPixels[xx + (yy*WIDTH)] = 0xff0000;
				}else if(tiles[xx + (yy*WIDTH)] instanceof WallTileNoJump){
					Game.miniMapaPixels[xx + (yy*WIDTH)] = 0xffff00;
				}
		}
	}

		int xPlayer = Game.player.getX() / 32;
		int yPlayer = Game.player.getY() / 32;

		Game.miniMapaPixels[xPlayer + (yPlayer*WIDTH)] = 0xffffff;
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x/32;
		int ystart = Camera.y/32;
		
		int xfinal = xstart + (Game.WIDTH/32) + 1;
		int yfinal = ystart + (Game.HEIGHT/32) + 1;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}


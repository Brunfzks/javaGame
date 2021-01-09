package com.abstudios.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.abstudios.main.Game;



public class Tile {
	
	//GRAMA
	public static BufferedImage TILE_GRAMA_COMUM = Game.spritesheet.getSprite(32, 64, 32, 32);
	public static BufferedImage TILE_GRAMA_HORIZONTAL_SUPERIOR = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TILE_GRAMA_HORIZONTAL_INFERIOR = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TILE_GRAMA_VERTICAL_ESQUERDA = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TILE_GRAMA_VERTICAL_DIREITA = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TILE_GRAMA_CANTO_SUPERIOR_ESQUERDO = Game.spritesheet.getSprite(32, 32, 32, 32);
	public static BufferedImage TILE_GRAMA_CANTO_SUPERIOR_DIREITO = Game.spritesheet.getSprite(32, 32, 32, 32);
	public static BufferedImage TILE_GRAMA_CANTO_INFERIOR_ESQUERDO = Game.spritesheet.getSprite(32, 32, 32, 32);
	public static BufferedImage TILE_GRAMA_CANTO_INFERIOR_DIREITO = Game.spritesheet.getSprite(32, 32, 32, 32);
	
	//BARRANCO
	public static BufferedImage TILE_BARRANCO_HORIZONTAL_SUPERIOR = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_BARRANCO_HORIZONTAL_INFERIOR = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_BARRANCO_VERTICAL_ESQUERDA = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_BARRANCO_VERTICAL_DIREITA = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_BARRANCO_CANTO_SUPERIOR_ESQUERDO = Game.spritesheet.getSprite(0, 32, 32, 32);
	public static BufferedImage TILE_BARRANCO_CANTO_SUPERIOR_DIREITO = Game.spritesheet.getSprite(0, 32, 32, 32);
	public static BufferedImage TILE_BARRANCO_CANTO_INFERIOR_ESQUERDO = Game.spritesheet.getSprite(0, 32, 32, 32);
	public static BufferedImage TILE_BARRANCO_CANTO_INFERIOR_DIREITO = Game.spritesheet.getSprite(0, 32, 32, 32);
	
	//Parede
	public static BufferedImage TILE_PAREDE_HORIZONTAL = Game.spritesheet.getSprite(8*32, 0, 32, 32);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public static BufferedImage rotateImage(BufferedImage rotateImage, double angle) {
	    angle %= 360;
	    if (angle < 0) angle += 360;
	    int quadrants = (int) angle / 90;
	    double restAngle = angle % 90;
	    if (restAngle < 0) restAngle += 90;

	    AffineTransform tx = new AffineTransform();
	    tx.rotate(Math.toRadians(restAngle), rotateImage.getWidth() / 2.0, rotateImage.getHeight() / 2.0);

	    double ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
	    double xtrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getX();

	    AffineTransform translationTransform = new AffineTransform();
	    translationTransform.translate(-xtrans, -ytrans);
	    tx.preConcatenate(translationTransform);

	    BufferedImage b2 = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(rotateImage, null);

	    AffineTransform fix = AffineTransform.getQuadrantRotateInstance(
	            quadrants, b2.getWidth() / 2.0, b2.getHeight() / 2.0);
	    return new AffineTransformOp(fix, AffineTransformOp.TYPE_BILINEAR).filter(b2, null);
	}

	
	public void render(Graphics g) {
		
		if(sprite == TILE_BARRANCO_CANTO_INFERIOR_DIREITO) {
			
			g.drawImage(rotateImage(sprite, 180), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_BARRANCO_CANTO_INFERIOR_ESQUERDO) {
			
			g.drawImage(rotateImage(sprite, 270), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_BARRANCO_HORIZONTAL_INFERIOR) {
		
			g.drawImage(rotateImage(sprite, 180), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_BARRANCO_VERTICAL_ESQUERDA){
			
			g.drawImage(rotateImage(sprite, -90), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_BARRANCO_VERTICAL_DIREITA){
			
			g.drawImage(rotateImage(sprite, 90), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_VERTICAL_ESQUERDA) {
			
			g.drawImage(rotateImage(sprite, 270), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_VERTICAL_DIREITA) {
			
			g.drawImage(rotateImage(sprite, 90), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_CANTO_SUPERIOR_ESQUERDO) {
			
			g.drawImage(rotateImage(sprite, -90), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_CANTO_INFERIOR_DIREITO) {
			
			g.drawImage(rotateImage(sprite, 90), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_CANTO_INFERIOR_ESQUERDO) {
			
			g.drawImage(rotateImage(sprite, 180), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_GRAMA_HORIZONTAL_INFERIOR) {
			g.drawImage(rotateImage(sprite, 180), x - Camera.x, y - Camera.y, null);
		}else if(sprite == TILE_BARRANCO_CANTO_SUPERIOR_DIREITO) {
			g.drawImage(rotateImage(sprite, 90), x - Camera.x, y - Camera.y, null);
		}else {
			g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		}
	}
}

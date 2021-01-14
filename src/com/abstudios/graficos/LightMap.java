package com.abstudios.graficos;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.abstudios.main.Game;

import java.awt.image.BufferStrategy;

public class LightMap {
    
    public BufferedImage lightMap;
    public int[] lightMapPixels;

    public LightMap(){
        try {
            lightMap = ImageIO.read(getClass().getResource("/lightMap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lightMapPixels  = new int[lightMap.getWidth() * lightMap.getHeight()];
        lightMap.getRGB(0, 0, lightMap.getWidth(), lightMap.getHeight(), lightMapPixels, 0, lightMap.getWidth());
    }

    public void applyLight(){
        for(int xx = 0; xx < Game.WIDTH; xx++){
            for(int yy = 0; yy < Game.HEIGHT; yy++){
                if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xffffffff){
                    Game.pixels[xx + (yy * Game.WIDTH)] = 0;
                }
            }
        }
    }
}

package com.abstudios.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics2D;

import java.awt.image.BufferStrategy;

// import org.graalvm.compiler.asm.amd64.AMD64Address.Scale;

import java.awt.Font;

public class Menu {

    private String[] options = {"Novo Jogo", "Carregar Jogo", "Sair", "Salvar"};
    private int currentOptions = 0;
    private int maxOption = options.length - 1;
    public boolean up, down, enter;
    
    public boolean pause = false;

    //pisca menu
    private int framesPiscaMenu = 0;
    private boolean pisca = true;

    public int getCurrentOption(){
        return this.currentOptions;
    }
    public void setCurrentOption(int option){
        option =  this.currentOptions;
    }

    public void tick(){
        if(up){
            up = false;
            currentOptions--;
            if(currentOptions < 0){
                currentOptions = maxOption;
            }
        }
        if(down){
            down = false;
            currentOptions++;
            if(currentOptions > maxOption){
                currentOptions = 0;
            }
        }

        if(enter){
            enter = false;
            if(options[currentOptions] == "Novo Jogo" || options[currentOptions] == "Continuar"){
                Game.gameState = "NORMAL";
                if(pause == false){
                    File file = new File("save.txt");
                    file.delete();
                }
                pause = false;
            }else if(options[currentOptions] == "Sair"){
                System.exit(1);
            }else if(options[currentOptions] == "Salvar"){
                // System.out.println("TESTE");
                save.saveGame = true;
                // System.out.println(save.saveGame);
            }else if(options[currentOptions] == "Carregar Jogo"){
                File file = new File("save.txt");
                if(file.exists()){
                    String saver = save.loadGame(10);
                    save.aplySave(saver);
                }
            }
        }

        this.framesPiscaMenu++;
			if(this.framesPiscaMenu == 20){
				this.framesPiscaMenu = 0;
				if(this.pisca){
					this.pisca = false;
				}else{
					this.pisca = true;
                }
            }
    }
    public void render(Graphics g){
        //Fundo
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        //Opçoes
        g.setColor(new Color(191, 181, 35));
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.drawString("Mr.Bullet", (Game.WIDTH * Game.SCALE) / 2 -100, (Game.HEIGHT * Game.SCALE) / 2 - 250);

        g.setFont(new Font("arial", Font.BOLD, 30));
        if(pause == false){
            if(options[currentOptions] != "Novo Jogo")
                g.drawString("Novo Jogo", (Game.WIDTH * Game.SCALE) / 2 -70, (Game.HEIGHT * Game.SCALE) / 2 - 150);
        }else{
            if(options[currentOptions] != "Novo Jogo")
                g.drawString("Resumir", (Game.WIDTH * Game.SCALE) / 2 -60, (Game.HEIGHT * Game.SCALE) / 2 - 150);

            if(options[currentOptions] != "Salvar")
                g.drawString("Salvar",  (Game.WIDTH * Game.SCALE) / 2 -40, (Game.HEIGHT * Game.SCALE) / 2 - 200);
        }
        
            
        if(options[currentOptions] != "Carregar Jogo")
            g.drawString("Carregar", (Game.WIDTH * Game.SCALE) / 2 -80, (Game.HEIGHT * Game.SCALE) / 2 - 110);

        if(options[currentOptions] != "Sair")
            g.drawString("Sair", (Game.WIDTH * Game.SCALE) / 2 -30, (Game.HEIGHT * Game.SCALE) / 2 - 70);

        switch(options[currentOptions]){
            case "Novo Jogo" :
                if(pisca)
                    if(pause == false){
                        g.drawString("> Novo Jogo", (Game.WIDTH * Game.SCALE) / 2 -100, (Game.HEIGHT * Game.SCALE) / 2 - 150);
                    }else{
                        g.drawString("> Resumir", (Game.WIDTH * Game.SCALE) / 2 -70, (Game.HEIGHT * Game.SCALE) / 2 - 150);
                    }
                break;
            case "Carregar Jogo":
                if(pisca)
                    g.drawString("> Carregar", (Game.WIDTH * Game.SCALE) / 2 -90, (Game.HEIGHT * Game.SCALE) / 2 - 110);
                break;
            case "Sair" : 
                if(pisca)
                     g.drawString("> Sair", (Game.WIDTH * Game.SCALE) / 2 -60, (Game.HEIGHT * Game.SCALE) / 2 - 70);
                break;

            case "Salvar" : 
            if(pisca && pause == true)
                    g.drawString("> Salvar",  (Game.WIDTH * Game.SCALE) / 2 -40, (Game.HEIGHT * Game.SCALE) / 2 - 200);
            break;
        }
    }
}

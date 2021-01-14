package com.abstudios.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.abstudios.world.World;

public class save {

    public static boolean pause = false;

    public static boolean saveExist = false,  saveGame = false;

    
    public static void saveGame(String[] val1, int[] val2, int encode){

        BufferedWriter write = null;

        try{

            write = new BufferedWriter(new FileWriter("save.txt"));
            for(int i = 0; i < val1.length; i++){
                String current = val1[i];
                current += ":";
                char[] value = Integer.toString(val2[i]).toCharArray();
                for(int z = 0; z< value.length; z++){
                    value[z]+=encode;
                    current+=value[z];
                }
                try{
                    write.write(current);
                    if(i < val1.length -1)
                        write.newLine();
                }catch(IOException e){

                }
            }
            try{
                write.flush();
                write.close();
            }catch(IOException e){}

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String loadGame(int encode){
        String line = "";
        File file = new File("save.txt");

        if(file.exists()){
            try{
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
                try{
                    while((singleLine = reader.readLine()) != null){
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] = "";
                        for(int i = 0; i < val.length; i++){
                            val[i] -= encode;
                            trans[1] += val[i];
                        }
                        line+=trans[0];
                        line+=":";
                        line+=trans[1];
                        line+="/";
                    }
                }catch(IOException e){}

            }catch(FileNotFoundException e){}
        }

        return line;
    }

    public static void aplySave(String str){

        String[] spl = str.split("/");

        for(int i = 0; i < spl.length; i++){
            String[] spl2 = spl[i].split(":");

            switch(spl2[0]){
                case "level":
                    World.restartGame("level" + spl2[1] + ".png");
                    Game.gameState = "NORMAL";
                     pause = false;
                    break;
                case "vida":
                    Game.player.life = Integer.parseInt(spl2[1]); 
                    break;
                case "ammo":
                    Game.player.ammo = Integer.parseInt(spl2[1]); 
                break;
                case "playerX":
                    Game.player.setX(Integer.parseInt(spl2[1]));  
                break;
                case "playerY":
                    Game.player.setY(Integer.parseInt(spl2[1])); 
                break;
            }
        }
    } 

    public void tick(){
        File file = new File("save.txt");

        if(file.exists()){
            saveExist = true;
        }else{
            saveExist = false;
        }
    }
}







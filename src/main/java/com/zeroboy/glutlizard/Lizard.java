/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;

import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author zero
 */
public class Lizard {
    private int x,y;
    private Image lizard;
    public Lizard(){
        
    }
    
    private void loadImage(){
        var image = new ImageIcon("src/resources/lizard-100.png");
        lizard = image.getImage();
    }
    
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public Image getLizard(){
        return lizard;
    }
    public void setLizard(Image lizard){
        this.lizard = lizard;
    }
}

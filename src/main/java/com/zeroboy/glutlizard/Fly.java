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
public class Fly {
    private int x,y;
    private Image fly;
    public Fly(){
        
    }
    
    private void loadImage(){
        var image = new ImageIcon("src/resources/fly-50.png");
        fly = image.getImage();
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
    public Image getFly(){
        return fly;
    }
    public void setFly(Image fly){
        this.fly = fly;
    }
}

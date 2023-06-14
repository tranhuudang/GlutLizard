
package com.zeroboy.glutlizard;



import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Obstacles {
    private int x;
    private int y;
    private BufferedImage[] images;

    public Obstacles(int x, int y, BufferedImage[] images) {
        this.x = x;
        this.y = y;
        this.images = images;
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
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw(Graphics g) {
        for (BufferedImage image : images)
        g.drawImage(image, x, y, null);
    }
    
}

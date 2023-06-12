/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
/**
 *
 * @author zero
 */
public class Lizard {
    private int x;
    private int y;
    private int CELL_SIZE = 10;
    private BufferedImage image;
    private double rotationAngle;

    public Lizard(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.rotationAngle = 0.0;
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
    
        public void moveLeft() {
        x -= CELL_SIZE;
    }

    public void moveRight() {
        x += CELL_SIZE;
    }

    public void moveUp() {
        y -= CELL_SIZE;
    }

    public void moveDown() {
        y += CELL_SIZE;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void rotateClockwise() {
        rotationAngle += Math.toRadians(20);
    }

    public void rotateCounterClockwise(){
        rotationAngle -= Math.toRadians(20);
    }
    
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        AffineTransform newTransform = (AffineTransform) oldTransform.clone();

        newTransform.rotate(rotationAngle, x + image.getWidth() / 2, y + image.getHeight() / 2);
        g.setTransform(newTransform);

        g.drawImage(image, x, y, null);

        g.setTransform(oldTransform);
    }
}

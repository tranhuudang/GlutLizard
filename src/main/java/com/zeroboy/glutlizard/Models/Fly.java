package com.zeroboy.glutlizard.Models;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author zero
 */
public class Fly {

    private int x;
    private int y;
    private final BufferedImage image;

    public Fly(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    // Check if a point is inside fly image's boundary
    public boolean isPointIntersectingFly(Point point) {
        return point.x >= x && point.x < x + image.getWidth()
                && point.y >= y && point.y < y + image.getHeight();
    }

}

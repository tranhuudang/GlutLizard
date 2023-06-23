
package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Interfaces.IGameObject;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author zero
 */
public class GameObject implements IGameObject {

    private int x;
    private int y;
    private BufferedImage image;

    public GameObject(Point position, BufferedImage image) {
        this.x = position.x;
        this.y = position.y;
        this.image = image;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isPointIntersectingWithObject(Point point) {
        return point.x >= x && point.x < x + image.getWidth()
                && point.y >= y && point.y < y + image.getHeight();
    }

    @Override
    public void setObjectImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage getObjectImage() {
        return image;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getObjectImage(), getX(), getY(), null);
    }

}

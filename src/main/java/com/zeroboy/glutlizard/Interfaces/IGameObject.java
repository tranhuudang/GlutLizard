package com.zeroboy.glutlizard.Interfaces;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author zero
 */
public interface IGameObject {

    // Get current X coodinate of Object
    public int getX();

    // Set X coodinate to Object
    public void setX(int x);

    // Get current Y coodinate of Object
    public int getY();

    // Set Y coodinate to Object
    public void setY(int y);

    // Set X,Y coodinate of Object
    public void setPosition(int x, int y);

    // Check if a point is inside obstacle's boundary
    public boolean isPointIntersectingWithObject(Point point);

    // Get or set Object image
    public void setObjectImage(BufferedImage image);

    public BufferedImage getObjectImage();
    
    // Draw object to current context
    public void draw(Graphics2D g);
}

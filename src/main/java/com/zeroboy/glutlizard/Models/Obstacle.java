package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Properties;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Obstacle {

    private int x;
    private int y;
    private final BufferedImage image;
    private final Random random;

    public Obstacle(BufferedImage image) {
        this.image = image;
        random = new Random();
        generateRandomPosition();
    }

    private void generateRandomPosition() {
        x = random.nextInt(Properties.BOARD_WIDTH);
        y = random.nextInt(Properties.BOARD_HEIGHT);
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
    // Check if a point is inside obstacle's boundary
    public boolean isPointIntersectingObstacle(Point point) {
        return point.x >= x && point.x < x + image.getWidth()
                && point.y >= y && point.y < y + image.getHeight();
    }
}

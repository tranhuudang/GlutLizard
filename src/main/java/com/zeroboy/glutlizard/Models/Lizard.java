/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Properties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Lizard {

    private int x;
    private int y;
    private final BufferedImage image;
    private double rotationAngle;
    private final int tongueLength = 100;
    private final int tongueWidth = 8;

    public Lizard(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.rotationAngle = 0.0;
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

    public void moveLeft() {
        x -= Properties.CELL_SIZE;
    }

    public void moveRight() {
        x += Properties.CELL_SIZE;
    }

    public void moveUp() {
        y -= Properties.CELL_SIZE;
    }

    public void moveDown() {
        y += Properties.CELL_SIZE;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotateClockwise() {
        rotationAngle += Math.toRadians(20);
    }

    public void rotateCounterClockwise() {
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

    public double getRotationAngle() {
        return rotationAngle;
    }

    // Draw the tongue if Space key is pressed
    public void drawLizardTongue(Graphics2D g2d) {
        // Get the current angle of the lizard
        double angle = 70 - getRotationAngle();
        // Calculate the center point of the lizard image
        int centerX = getX() + 100 / 2;
        int centerY = getY() + 100 / 2;
        // Calculate the offset from the center to the starting point of the tongue line
        int offset = 70; // Distance from the center to the starting point
        // Calculate the starting point of the tongue line
        int startX = centerX + (int) (offset * Math.cos(angle));
        int startY = centerY - (int) (offset * Math.sin(angle));
        // Calculate the end point of the tongue line based on the desired angle
        int endX = startX + (int) (tongueLength * Math.cos(angle));
        int endY = startY - (int) (tongueLength * Math.sin(angle));
        // #LIZARD TONGUE COLOR
        // Define the gradient colors for the tongue
        Color startColor = new Color(255, 150, 150); // Lighter color
        Color endColor = new Color(255, 50, 50); // Darker color
        // Create a gradient paint object using the start and end colors
        Paint gradientPaint = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
        // Set the paint and stroke for the tongue line
        g2d.setPaint(gradientPaint);
        // Round edge for lizard tongue
        g2d.setStroke(new BasicStroke(tongueWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // #END
        // Draw the tongue line
        g2d.drawLine(startX, startY, endX, endY);
    }
}

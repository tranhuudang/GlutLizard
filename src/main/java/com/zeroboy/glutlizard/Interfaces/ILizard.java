package com.zeroboy.glutlizard.Interfaces;

import java.awt.Graphics2D;
import java.awt.Point;

public interface ILizard extends IGameObject{
     public int getTongueLength();

    public void setTongueLength(int newTongueLength);

    public Point getEndTonguePosition();

    public void movingAnimation();

    public void moveUp();

    public void moveDown();

    public void rotateClockwise();

    public void rotateCounterClockwise();

    public double getRotationAngle();

    // Draw the tongue if Space key is pressed
    public void drawLizardTongue(Graphics2D g2d);

    public void surprise();
}

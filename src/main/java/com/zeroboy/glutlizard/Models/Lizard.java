package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Constants;
import com.zeroboy.glutlizard.Interfaces.ILizard;
import static com.zeroboy.glutlizard.Properties.BOARD_HEIGHT;
import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Lizard extends GameObject implements ILizard, Constants {

    private Point endTonguePosition = new Point(0, 0);
    private double rotationAngle;
    private int tongueLength = 100;
    private final int tongueWidth = 8;
    private int steps = 0;

    public Lizard(Point position, BufferedImage image) {
        super(position, image);
    }

    @Override
    public int getTongueLength() {
        return tongueLength;
    }

    @Override
    public void setTongueLength(int newTongueLength) {
        this.tongueLength = newTongueLength;
    }

    @Override
    public Point getEndTonguePosition() {
        return endTonguePosition;
    }

    private void movingAnimation() {
        steps = steps + 1;
        try {
            if (steps % 2 == 0) {
                setObjectImage(ImageIO.read(new File(LIZARD_IMAGE_STEP_1)));
            } else {
                setObjectImage(ImageIO.read(new File(LIZARD_IMAGE_STEP_2)));
            }
        } catch (IOException ex) {
            Logger.getLogger(Lizard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleMoveAction() {
        movingAnimation();
        lizardLoopHandler();
    }

    // Check if lizard's axis go out of bound and set it back to the opposite side.
    private void lizardLoopHandler() {
        if (getX() >= BOARD_WIDTH) {
            System.out.println("out X");
            setX(-100);
        } else if (getX() < -100) {
            setX(BOARD_WIDTH);
        }
        // Handle longtitute out of bound 
        if (getY() >= BOARD_HEIGHT) {
            System.out.println("out Y");
            setY(-100);
        } else if (getY() < -100) {
            setY(BOARD_HEIGHT);
        }
    }

    @Override
    public void moveUp() {
        setY((int) (getY() + CELL_SIZE * Math.sin(rotationAngle - 20)));
        setX((int) (getX() + CELL_SIZE * Math.cos(rotationAngle - 20)));
        handleMoveAction();
    }

    @Override
    public void moveDown() {
        setY((int) (getY() - CELL_SIZE * Math.sin(rotationAngle - 20)));
        setX((int) (getX() - CELL_SIZE * Math.cos(rotationAngle - 20)));
        handleMoveAction();
    }

    @Override
    public void rotateClockwise() {
        rotationAngle += Math.toRadians(20);
    }

    @Override
    public void rotateCounterClockwise() {
        rotationAngle -= Math.toRadians(20);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        AffineTransform newTransform = (AffineTransform) oldTransform.clone();
        newTransform.rotate(rotationAngle, getX() + getObjectImage().getWidth() / 2, getY() + getObjectImage().getHeight() / 2);
        g.setTransform(newTransform);
        g.drawImage(getObjectImage(), getX(), getY(), null);
        g.setTransform(oldTransform);
    }

    @Override
    public double getRotationAngle() {
        return rotationAngle;
    }

    // Draw the tongue if Space key is pressed
    @Override
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
        int endTongueX = startX + (int) (tongueLength * Math.cos(angle));
        int endTongueY = startY - (int) (tongueLength * Math.sin(angle));
        endTonguePosition = new Point(endTongueX, endTongueY);
        // #LIZARD TONGUE COLOR
        // Define the gradient colors for the tongue
        Color startColor = new Color(255, 150, 150); // Lighter color
        Color endColor = new Color(255, 50, 50); // Darker color
        // Create a gradient paint object using the start and end colors
        Paint gradientPaint = new GradientPaint(startX, startY, startColor, endTongueX, endTongueY, endColor);
        // Set the paint and stroke for the tongue line
        g2d.setPaint(gradientPaint);
        // Round edge for lizard tongue
        g2d.setStroke(new BasicStroke(tongueWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // #END
        // Draw the tongue line
        g2d.drawLine(startX, startY, endTongueX, endTongueY);
    }

    @Override
    public void surprise() {
        try {
            setObjectImage(ImageIO.read(new File(LIZARD_SURPRISE_IMAGE)));
            Timer stopTimer = new Timer(500, e -> {
                try {
                    setObjectImage(ImageIO.read(new File(LIZARD_IMAGE_FILEPATH)));
                } catch (IOException ex) {
                    Logger.getLogger(Lizard.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            stopTimer.setRepeats(false);
            stopTimer.start();
        } catch (IOException ex) {
            Logger.getLogger(Lizard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

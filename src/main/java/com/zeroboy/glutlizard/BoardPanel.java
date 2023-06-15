package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Models.Obstacle;
import com.zeroboy.glutlizard.Models.Lizard;
import com.zeroboy.glutlizard.Models.Fly;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

class BoardPanel extends JPanel implements KeyListener {

    private Lizard lizard;
    private List<Fly> flies;
    private Random random;
    private List<Obstacle> obstacles;
    private List<BufferedImage> listObstaclesImage;

    // Arrow control key
    private boolean upKeyPressed = false;
    private boolean leftKeyPressed = false;
    private boolean downKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean spaceKeyPressed = false;

    public BoardPanel() {
        // Load the lizard and fly images from the assets
        try {
            BufferedImage lizardImage = ImageIO.read(new File("src/resources/lizard-100.png"));
            BufferedImage flyImage = ImageIO.read(new File("src/resources/fly-50.png"));

            lizard = new Lizard(100, 200, lizardImage);
            flies = new ArrayList<>();
            obstacles = new ArrayList<>();
            listObstaclesImage = new ArrayList<>();
            random = new Random();

            // Create obstacles' image
            listObstaclesImage.add(ImageIO.read(new File("src/resources/stone-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            for (BufferedImage obstacleImage : listObstaclesImage) {
                obstacles.add(new Obstacle(obstacleImage));
            }
            // Create three flies at random positions
            for (int i = 0; i < 15; i++) {
                flies.add(new Fly(400, 300, flyImage));
            }

            // Start a timer to update the flies' positions at regular intervals
            Timer timer = new Timer(50, e -> moveFlies());
            timer.start();
        } catch (IOException e) {
        }
    }

    private void moveFlies() {
        // Update the positions of all flies
        for (Fly fly : flies) {
            // Generate random values to update the fly's position
            int deltaX = random.nextInt(3) - 1; // Random value between -1 and 1
            int deltaY = random.nextInt(3) - 1; // Random value between -1 and 1

            // Calculate the new potential position for the fly
            int newX = fly.getX() + deltaX * Properties.CELL_SIZE;
            int newY = fly.getY() + deltaY * Properties.CELL_SIZE;

            // Check if the potential position is within the board boundaries
            if (newX >= 0 && newX < Properties.NUM_CELLS * Properties.CELL_SIZE && newY >= 0 && newY < Properties.NUM_CELLS * Properties.CELL_SIZE) {
                // Update the fly's position
                fly.setPosition(newX, newY);
            }
        }
        // Repaint the board to show the updated fly positions
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
        // Draw the board grid
        for (int x = 0; x < Properties.NUM_CELLS; x++) {
            for (int y = 0; y < Properties.NUM_CELLS; y++) {
                int cellX = x * Properties.CELL_SIZE;
                int cellY = y * Properties.CELL_SIZE;

                g.setColor(new Color(105, 88, 88));
                g.fillRect(cellX, cellY, Properties.CELL_SIZE, Properties.CELL_SIZE);

//                g.setColor(Color.LIGHT_GRAY);
//                g.drawRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
        // Draw the lizard
        lizard.draw(g2d);

        // Draw the flies
        for (Fly fly : flies) {
            fly.draw(g2d);
        }

        // Draw the tongue if Space key is pressed
        if (spaceKeyPressed) {
            lizard.drawLizardTongue(g2d);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        double currentLizardAngle = lizard.getRotationAngle();
        System.out.println("here");
        System.out.println(currentLizardAngle);
        // Move the lizard based on the arrow keys
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                leftKeyPressed = true;
                if ((currentLizardAngle >= -1.75) && (currentLizardAngle <= 0.70)) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveLeft();
                }
            }
            case KeyEvent.VK_RIGHT -> {
                rightKeyPressed = true;
                if ((currentLizardAngle >= -2.5) && (currentLizardAngle <= 0.35)) {
                    lizard.rotateClockwise();
                } else {
                    lizard.moveRight();
                }
            }
            case KeyEvent.VK_UP -> {
                upKeyPressed = true;
                System.out.println(Math.toDegrees(lizard.getRotationAngle()));
                if ((currentLizardAngle >= -0.35) && (currentLizardAngle <= 2.45)) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveUp();
                }
            }
            case KeyEvent.VK_DOWN -> {
                downKeyPressed = true;
                System.out.println(Math.toDegrees(lizard.getRotationAngle()));
                if ((currentLizardAngle >= 2.45) && (currentLizardAngle <= 5.3)) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveDown();
                }
            }
            case KeyEvent.VK_SPACE ->
                spaceKeyPressed = true;
            default -> {
            }
        }

        if (upKeyPressed && leftKeyPressed) {
            // Rotate the lizard counter-clockwise
            lizard.rotateCounterClockwise();
        } else if (upKeyPressed && rightKeyPressed) {
            lizard.rotateClockwise();
        } else if (downKeyPressed && leftKeyPressed) {
            lizard.rotateClockwise();
        } else if (downKeyPressed && rightKeyPressed) {
            lizard.rotateCounterClockwise();
        }

        // Repaint the board to show the updated lizard position
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release events if needed
        int keyCode = e.getKeyCode();

        // Reset the key states when the corresponding arrow keys are released
        switch (keyCode) {
            case KeyEvent.VK_UP ->
                upKeyPressed = false;
            case KeyEvent.VK_DOWN ->
                downKeyPressed = false;
            case KeyEvent.VK_LEFT ->
                leftKeyPressed = false;
            case KeyEvent.VK_RIGHT ->
                rightKeyPressed = false;
            case KeyEvent.VK_SPACE ->
                spaceKeyPressed = false;
            default -> {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed events if needed
    }
}

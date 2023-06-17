package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Components.Obstacle;
import com.zeroboy.glutlizard.Components.Lizard;
import com.zeroboy.glutlizard.Components.Fly;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private ScoreBoard scoreBoard;
    private Lizard lizard;
    private List<Fly> flies;
    private Random random;
    private List<Obstacle> obstacles;
    private List<BufferedImage> listObstaclesImage;
    private Timer spaceLimitTimer;
    private boolean allowSpaceKey;

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
            scoreBoard = new ScoreBoard();
            obstacles = new ArrayList<>();
            listObstaclesImage = new ArrayList<>();
            random = new Random();
            // Create obstacles' image
            listObstaclesImage.add(ImageIO.read(new File("src/resources/stone-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/bushFlower-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/tinyBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/greenBush-100.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-80.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-80.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-80.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-80-yellow.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50-yellow.png")));
            listObstaclesImage.add(ImageIO.read(new File("src/resources/flower-50-yellow.png")));
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
            // Space press limiter
            spaceLimitTimer = new Timer(500, e -> {
                allowSpaceKey = true;
            });
            spaceLimitTimer.setRepeats(false); // Only fire once
            spaceLimitTimer.start();
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
                g.setColor(Properties.BACKGROUND_COLOR);
                g.fillRect(cellX, cellY, Properties.CELL_SIZE, Properties.CELL_SIZE);
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
            for (Fly fly : flies) {
                if (fly.isPointIntersectingFly(lizard.getEndTonguePosition())) {
                    flies.remove(fly);
                    Properties.SCORE = Properties.SCORE + 1;
                }
            }
            boolean touchedObstacle = false; // Some time tongue touch many obstacles, we want one hit will touch 1 obstacle.
            for (Obstacle obstacle : obstacles) {
                if (obstacle.isPointIntersectingObstacle(lizard.getEndTonguePosition())) {
                    lizard.hitObstacle();
                    touchedObstacle = true;
                }
            }
            if (touchedObstacle) {
                scoreBoard.removeHeart();
            }
        }
        if (Properties.HEART_VALUE <= 0) {
            scoreBoard.gameOver(g2d);
        }
        // Draw the top score board
        scoreBoard.drawTopBoard(g2d);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        double currentLizardAngle = lizard.getRotationAngle();
        // Move the lizard based on the arrow keys
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                leftKeyPressed = true;
                if (currentLizardAngle >= -2.1) {
                    lizard.rotateCounterClockwise();
                } else if (currentLizardAngle <= -2.5) {
                    lizard.rotateClockwise();
                } else {
                    lizard.moveLeft();
                }
            }
            case KeyEvent.VK_RIGHT -> {
                rightKeyPressed = true;
                if (currentLizardAngle <= 0.6) {
                    lizard.rotateClockwise();
                } else if (currentLizardAngle >= 0.7) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveRight();
                }
            }
            case KeyEvent.VK_UP -> {
                upKeyPressed = true;
                if (currentLizardAngle <= -0.7) {
                    lizard.rotateClockwise();
                } else if (currentLizardAngle >= -0.5) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveUp();
                }
            }
            case KeyEvent.VK_DOWN -> {
                downKeyPressed = true;
                if (currentLizardAngle <= 2.4) {
                    lizard.rotateClockwise();
                } else if (currentLizardAngle >= 2.5) {
                    lizard.rotateCounterClockwise();
                } else {
                    lizard.moveDown();
                }
            }
            case KeyEvent.VK_SPACE -> {
                if (allowSpaceKey) {
                    spaceKeyPressed = true;
                    spaceLimitTimer.restart();
                    allowSpaceKey = false;
                }
            }
            default -> {
            }
        }
        // Handle 2 key pressed at the same time
        if (upKeyPressed && leftKeyPressed) {
            lizard.moveUp();
            lizard.moveLeft();
        } else if (upKeyPressed && rightKeyPressed) {
            lizard.moveUp();
            lizard.moveRight();
        } else if (downKeyPressed && leftKeyPressed) {
            lizard.moveDown();
            lizard.moveLeft();
        } else if (downKeyPressed && rightKeyPressed) {
            lizard.moveDown();
            lizard.moveRight();
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

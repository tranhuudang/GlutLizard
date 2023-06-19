package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Models.Obstacle;
import com.zeroboy.glutlizard.Models.Lizard;
import com.zeroboy.glutlizard.Models.Fly;
import static com.zeroboy.glutlizard.Properties.BACKGROUND_COLOR;
import static com.zeroboy.glutlizard.Properties.BOARD_HEIGHT;
import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import static com.zeroboy.glutlizard.Properties.CELL_SIZE;
import static com.zeroboy.glutlizard.Properties.FLY_IMAGE;
import static com.zeroboy.glutlizard.Properties.HEART_VALUE;
import static com.zeroboy.glutlizard.Properties.LIST_OBSTACLE_IMAGES;
import static com.zeroboy.glutlizard.Properties.LIZARD_IMAGE;
import static com.zeroboy.glutlizard.Properties.NUM_CELLS;
import static com.zeroboy.glutlizard.Properties.TIME_LEFT;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class BoardPanel extends JPanel implements KeyListener {

    private final ScoreBoard scoreBoard;
    private final Lizard lizard;
    private final List<Fly> flies;
    private final Random random;
    private final List<Obstacle> obstacles;

    private final Timer spaceLimitTimer;
    private Timer flyMovingTimer;
    private Timer countDownTimer;
    private boolean allowSpaceKey;
    private JButton restartButton;
    private final Properties properties;

    // Arrow control key
    private boolean upKeyPressed = false;
    private boolean leftKeyPressed = false;
    private boolean downKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean spaceKeyPressed = false;

    public BoardPanel() {
        // Default layout will automaticly align button to center, so 
        // set Layout to null in order to be able to set button position to custom value.
        setLayout(null);
        createRestartButton();
        properties = new Properties();
        // Load the lizard and fly images from the assets
        lizard = new Lizard(100, 200, LIZARD_IMAGE);
        flies = new ArrayList<>();
        scoreBoard = new ScoreBoard();
        scoreBoard.resetScore();
        obstacles = new ArrayList<>();
        random = new Random();
        // Create obstacles
        for (BufferedImage obstacleImage : LIST_OBSTACLE_IMAGES) {
            obstacles.add(new Obstacle(obstacleImage));
        }
        // Create three flies at random positions
        for (int i = 0; i < 15; i++) {
            flies.add(new Fly(400, 300, FLY_IMAGE));
        }
        // Start a timer to update the flies' positions at regular intervals
        flyMovingTimer = new Timer(50, e -> moveFlies());
        flyMovingTimer.start();
        // Count down timer 
        countDownTimer = new Timer(1000, f -> {
            TIME_LEFT -= 1;
        });
        countDownTimer.start();
        // Space press limiter
        spaceLimitTimer = new Timer(500, e -> {
            allowSpaceKey = true;
        });
        spaceLimitTimer.setRepeats(false); // Only fire once
        spaceLimitTimer.start();
    }

    private void createRestartButton() {
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButton.setBounds((BOARD_WIDTH - 80) / 2, ((BOARD_HEIGHT - 30) / 2) + 40, 80, 30); // Set the button's position and size
        restartButton.setVisible(false); // Hide the button initially
        add(restartButton); // Add the button to the panel
    }

    private void restartGame() {
        // Reset game state
        resetBoard();
        flyMovingTimer.start();
        countDownTimer.start();
        // Hide the restart button
        restartButton.setVisible(false);
        // Request focus for keyboard input
        requestFocus();
        // Repaint the board
        repaint();
    }

    private void resetBoard() {
        // Reset lizard position
        lizard.setPosition(100, 200);
        // Reset flies
        flies.clear();
        for (int i = 0; i < 15; i++) {
            flies.add(new Fly(400, 300, FLY_IMAGE));
        }
        // Reset score
        scoreBoard.resetScore();
        // Reset any other necessary variables or states
        // Repaint the board
        repaint();
    }

    private void moveFlies() {
        // Update the positions of all flies
        for (Fly fly : flies) {
            // Generate random values to update the fly's position
            int deltaX = random.nextInt(3) - 1; // Random value between -1 and 1
            int deltaY = random.nextInt(3) - 1; // Random value between -1 and 1
            // Calculate the new potential position for the fly
            int newX = fly.getX() + deltaX * CELL_SIZE;
            int newY = fly.getY() + deltaY * CELL_SIZE;
            // Check if the potential position is within the board boundaries
            if (newX >= 0 && newX < NUM_CELLS * CELL_SIZE && newY >= 0 && newY < NUM_CELLS * CELL_SIZE) {
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
        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                int cellX = x * CELL_SIZE;
                int cellY = y * CELL_SIZE;
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
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
                    lizard.surprise();
                    touchedObstacle = true;
                }
            }
            if (touchedObstacle && allowSpaceKey) {
                scoreBoard.removeHeart();
                allowSpaceKey = false;
            }
        }
        if ((HEART_VALUE <= 0) || (TIME_LEFT <= 0)) {
            scoreBoard.gameOver(g2d, (JFrame) SwingUtilities.getWindowAncestor(this));
            flyMovingTimer.stop();
            countDownTimer.stop();
            restartButton.setVisible(true);
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

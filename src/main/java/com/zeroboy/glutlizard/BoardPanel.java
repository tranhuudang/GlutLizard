package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Handlers.LocalStorage;
import com.zeroboy.glutlizard.Models.Obstacle;
import com.zeroboy.glutlizard.Models.Lizard;
import com.zeroboy.glutlizard.Models.Fly;
import static com.zeroboy.glutlizard.Properties.BACKGROUND_COLOR;
import static com.zeroboy.glutlizard.Properties.BOARD_HEIGHT;
import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import static com.zeroboy.glutlizard.Properties.CELL_SIZE;
import static com.zeroboy.glutlizard.Properties.FLY_IMAGE;
import static com.zeroboy.glutlizard.Properties.HEART_VALUE;
import static com.zeroboy.glutlizard.Properties.LEVEL;
import static com.zeroboy.glutlizard.Properties.LIST_OBSTACLE_IMAGES_BACK;
import static com.zeroboy.glutlizard.Properties.LIST_OBSTACLE_IMAGES_FRONT;
import static com.zeroboy.glutlizard.Properties.LIZARD_IMAGE;
import static com.zeroboy.glutlizard.Properties.NUM_CELLS;
import static com.zeroboy.glutlizard.Properties.TIME_LEFT;
import static com.zeroboy.glutlizard.Properties.NUMBER_OF_FLIES;
import static com.zeroboy.glutlizard.Properties.SCORE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class BoardPanel extends JPanel implements KeyListener, ComponentListener {

    private final ScoreBoard scoreBoard;
    private final Lizard lizard;
    private final List<Fly> flies;
    private final Random random;
    private final List<Obstacle> obstaclesBack;
    private final List<Obstacle> obstaclesFront;

    private final Timer spaceLimitTimer;
    private Timer flyMovingTimer;
    private Timer countDownTimer;
    private boolean allowSpaceKey;
    private JButton restartButton;
    private JButton nextLevelButton;
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
        createNextLevelButton();
        properties = new Properties();
        // Load the lizard and fly images from the assets
        lizard = new Lizard((BOARD_WIDTH - LIZARD_IMAGE.getWidth())/2 , (BOARD_HEIGHT - LIZARD_IMAGE.getHeight())/2, LIZARD_IMAGE);
        flies = new ArrayList<>();
        scoreBoard = new ScoreBoard();
        scoreBoard.resetScore();
        obstaclesBack = new ArrayList<>();
        obstaclesFront = new ArrayList<>();
        random = new Random();
        // Create obstacles back
        for (BufferedImage obstacleImage : LIST_OBSTACLE_IMAGES_BACK) {
            obstaclesBack.add(new Obstacle(obstacleImage));
        }
        // Create obstacles back
        for (BufferedImage obstacleImage : LIST_OBSTACLE_IMAGES_FRONT) {
            obstaclesFront.add(new Obstacle(obstacleImage));
        }
        // Create three flies at random positions
        int numberOfFlies = getNumberOfFlies();
        NUMBER_OF_FLIES = numberOfFlies;
        for (int i = 0; i < numberOfFlies; i++) {
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

    // Calculate and return the number of flies based on level
    private int getNumberOfFlies() {
        int numberOfFlies;
        if (LEVEL <= 3) {
            numberOfFlies = 1 * LEVEL;
        } else if (LEVEL > 3 && LEVEL < 5) {
            numberOfFlies = 2 * LEVEL;
        } else {
            numberOfFlies = 3 * LEVEL;
        }
        return numberOfFlies;
    }

    // Create restart button displayed in Game Over board.
    private void createRestartButton() {
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButton.setBounds((BOARD_WIDTH - 80) / 2, ((BOARD_HEIGHT - 30) / 2) + 40, 80, 30); // Set the button's position and size
        restartButton.setBackground(BACKGROUND_COLOR);
        restartButton.setForeground(Color.GREEN);
        // Create a border instance
        Border border = new LineBorder(Color.GREEN, 2); // Red border with a thickness of 2 pixels
        restartButton.setBorder(border);
        restartButton.setVisible(false); // Hide the button initially
        add(restartButton); // Add the button to the panel
    }

    // Create next level button displayed in Victory
    private void createNextLevelButton() {
        nextLevelButton = new JButton("Next Level");
        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGameLevel();
            }
        });
        nextLevelButton.setBounds((BOARD_WIDTH - 80) / 2, ((BOARD_HEIGHT - 30) / 2) + 40, 80, 30); // Set the button's position and size
        nextLevelButton.setBackground(BACKGROUND_COLOR);
        nextLevelButton.setForeground(Color.GREEN);
        // Create a border instance
        Border border = new LineBorder(Color.GREEN, 2); // Red border with a thickness of 2 pixels
        nextLevelButton.setBorder(border);
        nextLevelButton.setVisible(false); // Hide the button initially
        add(nextLevelButton); // Add the button to the panel
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

    private void nextGameLevel() {
        System.out.println("Move to the next level.");
        // Reset game state
        scoreBoard.levelUp();
        // Save level to local storage
        LocalStorage.writeLevel();
        resetBoard();
        flyMovingTimer.start();
        countDownTimer.start();
        // Hide the restart button
        nextLevelButton.setVisible(false);
        // Request focus for keyboard input
        requestFocus();
        // Repaint the board
        repaint();
    }

    private void resetBoard() {
        // Reset lizard position
        lizard.setPosition((BOARD_WIDTH - LIZARD_IMAGE.getWidth())/2 , (BOARD_HEIGHT - LIZARD_IMAGE.getHeight())/2);
        // Reset flies
        flies.clear();
        var numberOfFlies = getNumberOfFlies();
        NUMBER_OF_FLIES = numberOfFlies;
        for (int i = 0; i < numberOfFlies; i++) {
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
                //g.setColor(Color.DARK_GRAY);
                g.drawRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
            }
        }
        // Draw obstacles back
        for (Obstacle obstacle : obstaclesBack) {
            obstacle.draw(g);
        }
        // Draw the lizard
        lizard.draw(g2d);
        // Draw obstacles front
        for (Obstacle obstacle : obstaclesFront) {
            obstacle.draw(g);
        }
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
            for (Obstacle obstacle : obstaclesBack) {
                if (obstacle.isPointIntersectingObstacle(lizard.getEndTonguePosition())) {
                    lizard.surprise();
                    touchedObstacle = true;
                }
            }
            for (Obstacle obstacle : obstaclesFront) {
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
        if ((HEART_VALUE < 0) || (TIME_LEFT <= 0)) {
            scoreBoard.gameOverBoard(g2d, (JFrame) SwingUtilities.getWindowAncestor(this));
            flyMovingTimer.stop();
            countDownTimer.stop();
            restartButton.setVisible(true);
        } else if (SCORE == NUMBER_OF_FLIES) {
            System.out.println("Victory");
            scoreBoard.victoryBoard(g2d, (JFrame) SwingUtilities.getWindowAncestor(this));
            flyMovingTimer.stop();
            countDownTimer.stop();
            nextLevelButton.setVisible(true);
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

    @Override
    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void componentResized(ComponentEvent e) {
        BOARD_WIDTH = getWidth();
        BOARD_HEIGHT = getHeight();
        //
        // Update the position of the restart button when the component (panel) is resized
        int buttonX = (getWidth() - 80) / 2;
        int buttonY = ((getHeight() - 30) / 2) + 40;
        restartButton.setBounds(buttonX, buttonY, 80, 30);
        // Update the position of the next level button when the component (panel) is resized
        nextLevelButton.setBounds(buttonX, buttonY, 80, 30);
        //
        repaint();
    }
}

package com.zeroboy.glutlizard;

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

public class Board extends JFrame {

    private static final int BOARD_SIZE = 800;

    public Board() {
        setTitle("GlutLizard");
        setSize(BOARD_SIZE, BOARD_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel);

        // Add the key listener to the board panel
        boardPanel.addKeyListener(boardPanel);

        // Set the board panel as the focusable component
        boardPanel.setFocusable(true);

        // Request focus for the board panel
        boardPanel.requestFocusInWindow();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Board::new);
    }
}

class BoardPanel extends JPanel implements KeyListener {

    private final int NUM_CELLS = 500;
    private final int CELL_SIZE = 10;

    private Lizard lizard;
    private List<Fly> flies;
    private Random random;

    // Arrow control key
    private boolean upKeyPressed = false;
    private boolean leftKeyPressed = false;
    private boolean downKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean spaceKeyPressed = false;

    private int tongueLength = 100;
    private int tongueWidth = 8;

    public BoardPanel() {
        // Load the lizard and fly images from the assets
        try {
            BufferedImage lizardImage = ImageIO.read(new File("src/resources/lizard-100.png"));
            BufferedImage flyImage = ImageIO.read(new File("src/resources/fly-50.png"));

            lizard = new Lizard(100, 200, lizardImage);
            flies = new ArrayList<>();
            random = new Random();

            // Create three flies at random positions
            for (int i = 0; i < 15; i++) {
                flies.add(new Fly(400, 300, flyImage));
            }

            // Start a timer to update the flies' positions at regular intervals
            Timer timer = new Timer(50, e -> moveFlies());
            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                g.setColor(Color.WHITE);
                g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the lizard
        lizard.draw(g2d);

        // Draw the flies
        for (Fly fly : flies) {
            fly.draw(g2d);
        }

        // Draw the tongue if Space key is pressed
        if (spaceKeyPressed) {
            // Get the current angle of the lizard
            double angle = 70 - lizard.getRotationAngle();

            // Calculate the center point of the lizard image
            int centerX = lizard.getX() + 100 / 2;
            int centerY = lizard.getY() + 100 / 2;

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

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Move the lizard based on the arrow keys
        if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = true;
            lizard.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true;
            lizard.moveRight();
        } else if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = true;
            lizard.moveUp();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = true;
            lizard.moveDown();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            spaceKeyPressed = true;
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
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            spaceKeyPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed events if needed
    }
}

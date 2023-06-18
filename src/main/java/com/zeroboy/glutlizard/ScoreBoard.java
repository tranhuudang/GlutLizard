package com.zeroboy.glutlizard;

import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import static com.zeroboy.glutlizard.Properties.HEART_LIST;
import static com.zeroboy.glutlizard.Properties.HEART_VALUE;
import static com.zeroboy.glutlizard.Properties.IS_GAMEOVER;
import static com.zeroboy.glutlizard.Properties.SCORE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author zero
 */
public class ScoreBoard {

    private BufferedImage heartBlank;

    ScoreBoard() {}

    public void removeHeart() {
        if (!Properties.HEART_LIST.isEmpty()) {
            Properties.HEART_LIST.remove(0);
            try {
                BufferedImage heartBlank1 = ImageIO.read(new File("src/resources/heart.png"));
                heartBlank = heartBlank1;
            } catch (IOException ex) {
                Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            Properties.HEART_LIST.add(heartBlank);
            Properties.HEART_VALUE -= 1;
        }
    }

    public void gameOver(Graphics2D g, JFrame frame) {
        g.setColor(Color.BLACK);
        g.fillRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 180);
        g.setColor(Color.RED);
        g.drawRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 180);
        g.setColor(Color.BLACK);
        String scoreText = "GAME OVER";
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(Color.RED); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                Properties.BOARD_HEIGHT / 2);
        // Create restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(Properties.BOARD_WIDTH / 2 - 60,
                Properties.BOARD_HEIGHT / 2 + 60, 120, 30);
        restartButton.addActionListener(e -> {
            // Restart button click event handler
            //resetGame(); // Call the method to reset the game
            // Other necessary code to restart the game
        });
        // Add the restart button to the frame's content pane
        frame.getContentPane().add(restartButton);
        frame.revalidate(); // Refresh the frame to display the new button
    }

    // Draw the score
    public void drawTopBoard(Graphics2D g) {
        String scoreText = "Score: " + SCORE;
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE); // Set color to white
        g.drawString(scoreText, BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                40);
        int heartX = 40;
        for (BufferedImage heart : Properties.HEART_LIST) {
            g.drawImage(heart, heartX, 20, null);
            heartX += 60;
        }
    }
    
    // Reset Scoreboard
     public void resetScore(){
        SCORE = 0;
        HEART_LIST = new ArrayList();
        IS_GAMEOVER = false;
        HEART_VALUE =3;
        BufferedImage heartFilled;
        try {
            BufferedImage heartFilled1 = ImageIO.read(new File("src/resources/heart-filled.png"));
            heartFilled = heartFilled1;
            for (int i = 0; i < 3; i++) {
                HEART_LIST.add(heartFilled);
            }
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}

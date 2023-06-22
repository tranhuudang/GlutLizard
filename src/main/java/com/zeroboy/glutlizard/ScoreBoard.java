package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Handlers.LocalStorage;
import static com.zeroboy.glutlizard.Properties.BACKGROUND_COLOR;
import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import static com.zeroboy.glutlizard.Properties.HEART_LIST;
import static com.zeroboy.glutlizard.Properties.HEART_VALUE;
import static com.zeroboy.glutlizard.Properties.IS_GAMEOVER;
import static com.zeroboy.glutlizard.Properties.SCORE;
import static com.zeroboy.glutlizard.Properties.TIME_LEFT;
import static com.zeroboy.glutlizard.Properties.LEVEL;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author zero
 */
public class ScoreBoard {

    private BufferedImage heartBlank;

    ScoreBoard() {
    }

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

    public void gameOverBoard(Graphics2D g, JFrame frame) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 170);
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));
        g.drawRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 170);
        g.setColor(Color.BLACK);
        String scoreText = "GAME OVER";
        g.setFont(new Font("Arial", Font.PLAIN, 34));
        g.setColor(Color.GREEN); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                Properties.BOARD_HEIGHT / 2);
        BACKGROUND_COLOR = Color.BLACK;
        frame.revalidate(); // Refresh the frame to display the new button
    }
    
    
    public void victoryBoard(Graphics2D g, JFrame frame) {
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 170);
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));
        g.drawRect(Properties.BOARD_WIDTH / 2 - 170,
                Properties.BOARD_HEIGHT / 2 - 80, 340, 170);
        g.setColor(Color.BLACK);
        String scoreText = "VICTORY";
        g.setFont(new Font("Arial", Font.PLAIN, 34));
        g.setColor(Color.GREEN); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                Properties.BOARD_HEIGHT / 2);
        BACKGROUND_COLOR = Color.BLACK;
        frame.revalidate(); // Refresh the frame to display the new button
        
    }

    
    // Draw the score
    public void drawTopBoard(Graphics2D g) {
        String timeString = int2TimeString(TIME_LEFT);
        String levelText = "Level: " + LEVEL;
        String scoreText = "Score: " + SCORE;
        String timeLeftText = "Time: " + timeString;
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE); // Set color to white
        g.drawString(levelText, BOARD_WIDTH - 180,
                30);
        g.drawString(scoreText, BOARD_WIDTH - 180,
                70);
        g.drawString(timeLeftText, BOARD_WIDTH -180,
                110);
        int heartX = 40;
        for (BufferedImage heart : Properties.HEART_LIST) {
            g.drawImage(heart, heartX, 20, null);
            heartX += 60;
        }
    }

    
    // Convert int value to time string
    private String int2TimeString(int totalSeconds) {
        // Create a Duration object with the given number of seconds
        Duration duration = Duration.ofSeconds(totalSeconds);
        // Extract minutes and seconds from the duration
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        // Convert minutes and seconds to a formatted time string
        String timeString = String.format("%02d:%02d", minutes, seconds);
        return timeString;
    }

    // Reset Scoreboard
    public void resetScore() {
        SCORE = 0;
        HEART_LIST = new ArrayList();
        IS_GAMEOVER = false;
        HEART_VALUE = 3;
        TIME_LEFT = 60;
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
    
    
    public void levelUp(){
        LEVEL = LEVEL +1;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zero
 */
public class ScoreBoard {

    private BufferedImage heartBlank;
    private BufferedImage heartFilled;

    ScoreBoard() {
        try {
            Properties.HEART_LIST = new ArrayList();
            BufferedImage heartBlank1 = ImageIO.read(new File("src/resources/heart.png"));
            BufferedImage heartFilled1 = ImageIO.read(new File("src/resources/heart-filled.png"));
            heartBlank = heartBlank1;
            heartFilled = heartFilled1;
            for (int i = 0; i < 3; i++) {
                Properties.HEART_LIST.add(heartFilled);
            }
        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeHeart()
    {
        Properties.HEART_LIST.remove(0);
        Properties.HEART_LIST.add(heartBlank);
        Properties.HEART_VALUE -=1;
    }
    
    public void gameOver(Graphics2D g)
    {
        String scoreText = "GAME OVER";
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                Properties.BOARD_HEIGHT / 2 );
        
    }
    // Draw the score
    public void drawTopBoard(Graphics2D g) {
        String scoreText = "Score: " + Properties.SCORE;
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
                40);
        int heartX = 40;
        for (BufferedImage heart : Properties.HEART_LIST) {
            g.drawImage(heart, heartX, 20, null);
            heartX += 60;
        }
    }
}

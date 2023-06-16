/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author zero
 */
public class ScoreBoard {
    // Draw the score
    public void draw (Graphics2D g){
        String scoreText = "Score: " + Properties.SCORE;
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE); // Set color to white
        g.drawString(scoreText, Properties.BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(scoreText) / 2,
               40);
    }
}

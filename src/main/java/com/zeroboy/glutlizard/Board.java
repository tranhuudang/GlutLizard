/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;



import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {

    private static final int BOARD_SIZE = 500;

    public Board() {
        setTitle("Board Application");
        setSize(BOARD_SIZE, BOARD_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Board::new);
    }
}

class BoardPanel extends JPanel {

    private static final int CELL_SIZE = 10;
    private static final int NUM_CELLS = 500;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                int cellX = x * CELL_SIZE;
                int cellY = y * CELL_SIZE;

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
            }
        }
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(BOARD_SIZE, BOARD_SIZE);
//    }
}

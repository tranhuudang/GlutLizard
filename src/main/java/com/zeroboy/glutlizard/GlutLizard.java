package com.zeroboy.glutlizard;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class GlutLizard extends JFrame {

    public GlutLizard() {
        initUI();
    }

    private void initUI() {
        setSize(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel);
        // Add the key listener to the board panel
        boardPanel.addKeyListener(boardPanel);
        // Set the board panel as the focusable component
        boardPanel.setFocusable(true);
        // Request focus for the board panel
        boardPanel.requestFocusInWindow();
        setVisible(true);
        setResizable(false);
        setTitle("GlutLizard");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new GlutLizard();
            game.setVisible(true);
        });
    }
}

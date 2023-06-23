package com.zeroboy.glutlizard;

import static com.zeroboy.glutlizard.Properties.BOARD_HEIGHT;
import static com.zeroboy.glutlizard.Properties.BOARD_WIDTH;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class GlutLizard extends JFrame implements Constants {

    public GlutLizard() {
        initUI();
        
    }

    private void initUI() {
        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel);
        // Add the key listener to the board panel
        boardPanel.addKeyListener(boardPanel);
        boardPanel.addComponentListener(boardPanel);
        // Set the board panel as the focusable component
        boardPanel.setFocusable(true);
        // Request focus for the board panel
        boardPanel.requestFocusInWindow();
        setVisible(true);
        setResizable(true);
        setTitle(GAME_TITLE);
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

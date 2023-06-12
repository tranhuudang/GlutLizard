/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zeroboy.glutlizard;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class GlutLizard extends JFrame {

    public GlutLizard() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setResizable(false);
        pack();
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

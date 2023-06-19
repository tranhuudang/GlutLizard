package com.zeroboy.glutlizard;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Properties {

    public static boolean IS_GAMEOVER = false;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;
    public static final int NUM_CELLS = 500;
    public static final int CELL_SIZE = 10;
    public static final Color BACKGROUND_COLOR = new Color(95, 115, 82);
    public static int SCORE = 0;
    public static int TIME_LEFT = 60;
    public static List<BufferedImage> HEART_LIST;
    public static int HEART_VALUE = 3;
    public static BufferedImage LIZARD_IMAGE;
    public static BufferedImage FLY_IMAGE;

    public static List<BufferedImage> LIST_OBSTACLE_IMAGES;

    public Properties() {
        try {
            LIZARD_IMAGE = ImageIO.read(new File("src/resources/lizard-100.png"));
            FLY_IMAGE = ImageIO.read(new File("src/resources/fly-50.png"));
            loadObstacleImages();
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadObstacleImages() {
        LIST_OBSTACLE_IMAGES = new ArrayList<>();
        // Add obstacle image paths to the list
        String[] obstacleImagePaths = {
            "src/resources/stone-100.png",
            "src/resources/bush-100.png",
            "src/resources/bushFlower-100.png",
            "src/resources/bushFlower-100.png",
            "src/resources/bushFlower-100.png",
            "src/resources/tinyBush-100.png",
            "src/resources/greenBush-100.png",
            "src/resources/tinyBush-100.png",
            "src/resources/tinyBush-100.png",
            "src/resources/greenBush-100.png",
            "src/resources/greenBush-100.png",
            "src/resources/flower-80.png",
            "src/resources/flower-80.png",
            "src/resources/flower-80.png",
            "src/resources/flower-50.png",
            "src/resources/flower-50.png",
            "src/resources/flower-50.png",
            "src/resources/flower-50.png",
            "src/resources/flower-50.png",
            "src/resources/flower-80-yellow.png",
            "src/resources/flower-50-yellow.png",
            "src/resources/flower-50-yellow.png"
        };
        try {
            for (String imagePath : obstacleImagePaths) {
                BufferedImage obstacleImage = ImageIO.read(new File(imagePath));
                LIST_OBSTACLE_IMAGES.add(obstacleImage);
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

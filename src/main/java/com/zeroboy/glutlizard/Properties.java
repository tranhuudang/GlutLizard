package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Handlers.LocalStorage;
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
    public static  int BOARD_WIDTH = 1280;
    public static  int BOARD_HEIGHT = 700;
    public static final int NUM_CELLS = 500;
    public static final int CELL_SIZE = 10;
    public static Color BACKGROUND_COLOR = Color.BLACK;
    public static int SCORE = 0;
    public static int LEVEL = 1;
    public static int NUMBER_OF_FLIES = 0;

    public static int TIME_LEFT = 60;
    public static List<BufferedImage> HEART_LIST;
    public static int HEART_VALUE = 3;
    public static BufferedImage LIZARD_IMAGE;
    public static BufferedImage FLY_IMAGE;

    public static List<BufferedImage> LIST_OBSTACLE_IMAGES_BACK;
    public static List<BufferedImage> LIST_OBSTACLE_IMAGES_FRONT;

    public Properties() {
        try {
            LIZARD_IMAGE = ImageIO.read(new File("src/resources/lizard-100.png"));
            FLY_IMAGE = ImageIO.read(new File("src/resources/fly-50.png"));
            loadObstacleImagesBack();
            loadObstacleImagesFront();
            LEVEL = LocalStorage.readLevel(LocalStorage.levelFilePath);
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadObstacleImagesBack() {
        LIST_OBSTACLE_IMAGES_BACK = new ArrayList<>();
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
            "src/resources/greenBush-100.png"
        };
        try {
            for (String imagePath : obstacleImagePaths) {
                BufferedImage obstacleImage = ImageIO.read(new File(imagePath));
                LIST_OBSTACLE_IMAGES_BACK.add(obstacleImage);
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadObstacleImagesFront() {
        LIST_OBSTACLE_IMAGES_FRONT = new ArrayList<>();
        // Add obstacle image paths to the list
        String[] obstacleImagePaths = {
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
                LIST_OBSTACLE_IMAGES_FRONT.add(obstacleImage);
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

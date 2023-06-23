package com.zeroboy.glutlizard;

import com.zeroboy.glutlizard.Handlers.LocalStorage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Properties implements Constants {

    public static int BOARD_WIDTH = 1280;
    public static int BOARD_HEIGHT = 700;
    public static boolean IS_GAMEOVER = false;
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
            LIZARD_IMAGE = ImageIO.read(new File(LIZARD_IMAGE_FILEPATH));
            FLY_IMAGE = ImageIO.read(new File(FLY_IMAGE_FILEPATH));
            loadObstacleImagesBack();
            loadObstacleImagesFront();
            LEVEL = LocalStorage.readLevel(LocalStorage.LEVEL_FILE_PATH);
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, "Error when trying to get Lizard/Fly's image.", ex);
        }
    }

    private void loadObstacleImagesBack() {
        LIST_OBSTACLE_IMAGES_BACK = new ArrayList<>();
        try {
            for (String imagePath : OBSTACLE_IMAGE_BACK_PATHS) {
                BufferedImage obstacleImage = ImageIO.read(new File(imagePath));
                LIST_OBSTACLE_IMAGES_BACK.add(obstacleImage);
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, "Error when trying to get Obstacle-Image-Back's image.", ex);
        }
    }

    private void loadObstacleImagesFront() {
        LIST_OBSTACLE_IMAGES_FRONT = new ArrayList<>();
        try {
            for (String imagePath : OBSTACLE_IMAGE_FRONT_PATHS) {
                BufferedImage obstacleImage = ImageIO.read(new File(imagePath));
                LIST_OBSTACLE_IMAGES_FRONT.add(obstacleImage);
            }
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, "Error when trying to get Obstacle-Image-Front's image.", ex);
        }
    }

}

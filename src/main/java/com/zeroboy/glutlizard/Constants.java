package com.zeroboy.glutlizard;

import java.awt.Color;

public interface Constants {

    int NUM_CELLS = 500;
    int CELL_SIZE = 10;
    Color BACKGROUND_COLOR = Color.BLACK;
    String GAME_TITLE = "GlutLizard";
    String DIRECTORY_PATH = "C:\\Users\\zero\\AppData\\Local\\GlutLizard";
    String LEVEL_FILE_PATH = "C:\\Users\\zero\\AppData\\Local\\GlutLizard\\Level.txt";
    // Add obstacle image paths to the list
    String[] OBSTACLE_IMAGE_BACK_PATHS = {
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
    // Add obstacle image paths to the list
    String[] OBSTACLE_IMAGE_FRONT_PATHS = {
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
    String FLY_IMAGE_FILEPATH = "src/resources/fly-50.png";
    String LIZARD_IMAGE_FILEPATH = "src/resources/lizard-100.png";
    String LIZARD_SURPRISE_IMAGE = "src/resources/lizard-100-hitObstacle.png";
    String LIZARD_IMAGE_STEP_1 = "src/resources/lizard-100-1.png";
    String LIZARD_IMAGE_STEP_2 = "src/resources/lizard-100-2.png";
}

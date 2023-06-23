package com.zeroboy.glutlizard.Handlers;

import com.zeroboy.glutlizard.Constants;
import static com.zeroboy.glutlizard.Properties.LEVEL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author zero
 */
public class LocalStorage implements Constants{

    public static void writeLevel() {
        if (!directoryChecker(DIRECTORY_PATH)) {
            dicrectoryCreator(DIRECTORY_PATH);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEVEL_FILE_PATH))) {
            String text = Integer.toString(LEVEL);
            writer.write(text);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }
    }

    public static int readLevel(String filePath) {
        if (!fileExist(LEVEL_FILE_PATH)) {
            return 1;
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                if (line != null) {
                    int currentLevel = Integer.parseInt(line);
                    System.out.println("Current Level is: "+ currentLevel);
                    return currentLevel;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while getting value from the file.");
            }
        }
        return 1;
    }

    private static boolean directoryChecker(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("The directory exists.");
            return true;
        } else {
            System.out.println("The directory does not exist.");
            return false;
        }
    }

    public static void dicrectoryCreator(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.mkdirs()) {
            System.out.println("Directory created successfully.");
        } else {
            System.out.println("Failed to create the directory.");
        }
    }

    public static boolean fileExist(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("The file exists.");
            return true;
        } else {
            System.out.println("The file does not exist.");
            return false;
        }
    }
}

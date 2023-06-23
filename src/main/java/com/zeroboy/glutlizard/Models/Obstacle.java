package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Interfaces.IObstacle;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Obstacle extends GameObject implements IObstacle {

    public Obstacle(Point position, BufferedImage image) {
        super(position,image);
    }
}

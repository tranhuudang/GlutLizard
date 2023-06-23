package com.zeroboy.glutlizard.Models;

import com.zeroboy.glutlizard.Interfaces.IFly;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Fly extends GameObject implements IFly {

    public Fly(Point position, BufferedImage image) {
        super(position, image);
    }
}

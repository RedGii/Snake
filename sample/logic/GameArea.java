package sample.logic;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import sample.GUI.SnakeFrame;

/**
 *
 * @author gigi
 */
public class GameArea extends Rectangle {

    public static final int DEFAULT_DIMENSION = 10;

    private Color color;
    private BufferedImage image;
    private boolean active;


    public GameArea(int dimension, Color color) {
        this.setRandomPosition();
        this.width = dimension;
        this.height = dimension;
        this.color = color;
        this.image = null;
        this.active = true;
    }

    public GameArea(int x, int y, int dimension, Color color) {
        this.x = x;
        this.y = y;
        this.width = dimension;
        this.height = dimension;
        this.color = color;
        this.image = null;
        this.active = true;
    }

    // Creazione sample e food (Disegnati poi nel gamepanel)
    public void draw(Graphics2D g2) {
        if (this.active) {
            if (this.color != null) {
                g2.setColor(this.color);
                g2.fillRect(this.x, this.y, this.width, this.height);
            } else if (this.image != null) {
                g2.drawImage(this.image, this.x, this.y, this.width, this.height, null);
            }
        }
    }

    // Creazione del serpente in modo random
    // ThreadLocalRandom restituisce un numero random
    public void setRandomPosition() {
        // Prende due coordinate (x e y)
        this.setLocation(GameArea.DEFAULT_DIMENSION * ThreadLocalRandom.current().nextInt(30,
                SnakeFrame.GAME_WIDHT / GameArea.DEFAULT_DIMENSION),
                GameArea.DEFAULT_DIMENSION * ThreadLocalRandom.current().nextInt(30,
                        SnakeFrame.GAME_WIDHT / GameArea.DEFAULT_DIMENSION ));
    }

    // Prende la dimensione dell'area di gioco
    public int getDimension() {
        return this.width;
    }

    public boolean setActive(boolean value) {
        return this.active = value;
    }

    public boolean isActive() {
        return this.active;
    }

}

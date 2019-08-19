package sample.logic;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;

/**
 *
 * @author gigi
 */
public class Food extends GameArea {

    private Boolean enabled;

    public Food(int dimension, Color color) {
        super(dimension, color);
        this.enabled = true;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (this.enabled)
            super.draw(g2);
    }
}

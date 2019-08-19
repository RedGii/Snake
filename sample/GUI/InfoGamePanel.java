package sample.GUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sample.logic.HotArea;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import sample.utils.Resources;

/**
 *
 * @author gigi
 */
public class InfoGamePanel extends JPanel {

    private sample.GUI.SnakeFrame snakeFrame;

    private Image sfondo;
    private Point sfondoPosition;

    private HotArea hotAreaNuovaPartita;

    public InfoGamePanel(sample.GUI.SnakeFrame snakeFrame) {

        this.snakeFrame = snakeFrame;

        this.setSize(800, 600);
        this.setLayout(null);

        // Aggiungo mouselistener per creare azioni a i pulsanti
        this.addMouseListener(new InfoGamePanel.MyMouseListener());

        // Da dove prende l'immagine
        sfondo = Resources.getImage("/sample/GUI/images/InfoGame.png");
        // La posizione
        sfondoPosition = new Point(0, 0);

        // Creo le HotArea e setto i parametri
        hotAreaNuovaPartita = new HotArea();
        hotAreaNuovaPartita.setBounds(515, 515, 240, 55);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Disegna
        g2.drawImage(sfondo, sfondoPosition.x, sfondoPosition.y, null);
    }

    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (hotAreaNuovaPartita.contains(e.getPoint())) {
                snakeFrame.switchPanel(snakeFrame.gamePanel, snakeFrame.infoGamePanel);
                // Al cambio del pannello parte il serpente e il timer
                snakeFrame.gamePanel.startSnake();
                snakeFrame.gamePanel.startTimer();
            }
        }
    }
}

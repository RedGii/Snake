package sample.GUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gigi
 */
public class SnakeFrame extends JFrame {

    public static final int GAME_WIDHT = 600;
    public static final int GAME_HEIGHT = 600;

    sample.GUI.WelcomePanel welcomePanel = new sample.GUI.WelcomePanel(SnakeFrame.this);
    sample.GUI.InfoGamePanel infoGamePanel = new sample.GUI.InfoGamePanel(SnakeFrame.this);
    sample.GUI.GamePanel gamePanel = new sample.GUI.GamePanel(this);
    sample.GUI.FinalPanel finalPanel = new sample.GUI.FinalPanel(this);

    public SnakeFrame() {
        // 815x639
        this.setSize(800, 622);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snake by Gigi");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        welcomePanel.setLocation(0, 0);
        welcomePanel.setVisible(true);
        this.getContentPane().add(welcomePanel);

        infoGamePanel.setLocation(0, 0);
        infoGamePanel.setVisible(false);
        this.getContentPane().add(infoGamePanel);

        gamePanel.setLocation(0, 0);
        gamePanel.setVisible(false);
        this.getContentPane().add(gamePanel);

        finalPanel.setLocation(0, 0);
        finalPanel.setVisible(false);
        this.getContentPane().add(finalPanel);
    }

    // Funzione cambio pannello
    public void switchPanel(JPanel panelEnabled, JPanel panelDisabled) {
        panelDisabled.setVisible(false);
        panelEnabled.setVisible(true);

        panelDisabled.setFocusable(false);
        panelEnabled.setFocusable(true);

        panelEnabled.requestFocus(true);
    }

}

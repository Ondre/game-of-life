package com.ap.game;

import com.ap.entity.Grid;
import com.ap.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/*
Controls:

Mouse:
Left button - make cell alive;
Right button - make cell dead;

Keyboard:
Pause - Space;
Clear entire grid - Backspace;
Speed up - NUM 1;
Speed down - NUM 2;
 */

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int CELL_SIZE = 10;
    public static final String TITLE = "Conway's Game Of Life";

    private static int fps = 10;

    private Graphics g;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private Grid grid;

    public static void main(String[] args) {
        createFrameAndLaunch();
    }

    private static void createFrameAndLaunch() {
        JFrame window = new JFrame();
        window.setSize(new Dimension(WIDTH, HEIGHT));
        window.setResizable(false);
        window.setTitle(TITLE);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.add(game);
        window.pack();
        Input in = new Input(game);
        game.addKeyListener(in);
        game.addMouseListener(in);
        game.addMouseMotionListener(in);
        window.setVisible(true);
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void run() {
        isRunning = true;
        gameLoop();
    }

    /**
     * Main game loop which runs the game.
     */

    private void gameLoop() {
        init();
        long curr;
        long sleepy;
        while (isRunning) {
            curr = System.currentTimeMillis();
            render();
            if (!isPaused) update();
            sleepy = curr + (1000 / fps) - System.currentTimeMillis();
            try {
                Thread.sleep(sleepy > 0 ? sleepy : 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        grid.update();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        g = bs.getDrawGraphics();
        if (isPaused)
            g.setColor(Color.PINK);
        else
            g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        grid.draw(g);
        drawStats();

        g.dispose();
        bs.show();
    }

    private void init() {
        grid = new Grid();
    }

    private void drawStats() {
        String stats = "speed: " + fps + " fps.";
        g.setColor(Color.BLACK);
        Point statsPoint = new Point(Game.WIDTH - 60 - (stats.length() * 5) / 2, 30);
        g.drawString(stats, statsPoint.x, statsPoint.y);
    }

    public void handleKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isPaused = !isPaused;
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            grid.clear();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
            fps += 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            fps = fps - 1 > 0 ? fps - 1 : 1;
        }
    }
}

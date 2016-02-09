package com.ap.entity;

import com.ap.game.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Cell {
    boolean isAlive = false;
    private Color deadCellColor;
    private Point position;
    private Rectangle bounds;

    Cell(Point position) {
        this.position = position;
        deadCellColor = Color.DARK_GRAY;
        bounds = new Rectangle(position.x, position.y, Game.CELL_SIZE, Game.CELL_SIZE);
    }

    void draw(Graphics g) {
        g.setColor(deadCellColor);
        if (isAlive)
            g.fillRect(position.x, position.y, Game.CELL_SIZE, Game.CELL_SIZE);
    }

    void update(MouseEvent e) {
        if (bounds.contains(new Point(e.getX(), e.getY()))) {
            if (e.getButton() == MouseEvent.BUTTON1 || e.getModifiers() == 16)
                isAlive = true;
            else if (e.getButton() == MouseEvent.BUTTON3 || e.getModifiers() == 4)
                isAlive = false;
        }
    }
}

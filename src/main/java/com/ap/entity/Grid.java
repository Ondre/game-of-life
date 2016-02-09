package com.ap.entity;

import com.ap.game.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Grid {

    private Cell[][] field;
    private Point size;
    private boolean[][] nextCellStates;

    public Grid() {
        size = new Point(Game.WIDTH / Game.CELL_SIZE, Game.HEIGHT / Game.CELL_SIZE);

        field = new Cell[size.x][size.y];
        nextCellStates = new boolean[size.x][size.y];

        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                field[i][j] = new Cell(new Point(i * Game.CELL_SIZE, j * Game.CELL_SIZE));
            }
        }

    }

    public void update() {
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                boolean living = field[i][j].isAlive;
                int count = GetLivingNeighbors(i, j);
                boolean result = false;

                if (living && count < 2)
                    result = false;
                if (living && (count == 2 || count == 3))
                    result = true;
                if (living && count > 3)
                    result = false;
                if (!living && count == 3)
                    result = true;

                nextCellStates[i][j] = result;
            }
        }
        setNextState();
    }

    private void setNextState() {
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                field[i][j].isAlive = nextCellStates[i][j];
            }
        }
    }

    private int GetLivingNeighbors(int x, int y) {
        int count = 0;

        if (x != size.x - 1)
            if (field[x + 1][y].isAlive)
                count++;

        if (x != size.x - 1 && y != size.y - 1)
            if (field[x + 1][y + 1].isAlive)
                count++;

        if (y != size.y - 1)
            if (field[x][y + 1].isAlive)
                count++;

        if (x != 0 && y != size.y - 1)
            if (field[x - 1][y + 1].isAlive)
                count++;

        if (x != 0)
            if (field[x - 1][y].isAlive)
                count++;

        if (x != 0 && y != 0)
            if (field[x - 1][y - 1].isAlive)
                count++;

        if (y != 0)
            if (field[x][y - 1].isAlive)
                count++;

        if (x != size.x - 1 && y != 0)
            if (field[x + 1][y - 1].isAlive)
                count++;

        return count;
    }

    public void update(MouseEvent e) {
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                field[i][j].update(e);
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < size.x; i++) {

            for (int j = 0; j < size.y; j++) {
                field[i][j].draw(g);
            }
            g.setColor(Color.lightGray);
            g.drawLine(0, i * Game.CELL_SIZE, Game.WIDTH, i * Game.CELL_SIZE);
            g.drawLine(i * Game.CELL_SIZE, 0, i * Game.CELL_SIZE, Game.HEIGHT);
        }
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                g.drawLine(0, i * Game.CELL_SIZE, Game.WIDTH, i * Game.CELL_SIZE);
                g.drawLine(i * Game.CELL_SIZE, 0, i * Game.CELL_SIZE, Game.HEIGHT);
            }
        }
    }

    public void clear() {
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                field[i][j].isAlive = false;
            }
        }
    }
}
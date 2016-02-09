package com.ap.input;

import com.ap.game.Game;

import java.awt.event.*;

public class Input implements MouseMotionListener, MouseListener, KeyListener {

    private Game game;

    public Input(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.handleKey(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.getGrid().update(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        game.getGrid().update(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

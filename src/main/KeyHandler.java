package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface to handle keyboard inputs.
 * This class is responsible for listening to key events and updating the state of
 * movement-related boolean flags (upPressed, downPressed, leftPressed, rightPressed)
 * based on the keys pressed or released.
 */
public class KeyHandler implements KeyListener {

    /**
     * Indicates whether the 'up' key (typically the 'W' key) is currently pressed.
     * This variable is set to true when the 'W' key is pressed and set to false
     * when the 'W' key is released. It is used to track upward movement in the
     * game's controls.
     */
    public boolean upPressed, /**
     * Indicates whether the down key is currently pressed.
     * Managed by the KeyHandler class to handle key press and release events.
     */
    downPressed, /**
     * Indicates whether the left movement key (typically 'A' key) is pressed.
     * This flag is set to true when the key is pressed and set to false when the key is released.
     */
    leftPressed, /**
     * A boolean flag indicating whether the right arrow key or 'D' key is currently pressed.
     * If true, the key is pressed. If false, the key is released.
     * This flag is used to handle movement in the right direction in keyboard input scenarios.
     */
    rightPressed;

    /**
     * Processes the key typed event. This method is intended to handle
     * character input, including those resulting from pressing 'dead' keys.
     *
     * @param e the KeyEvent associated with the key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Handles the event when a key is pressed. This method updates the movement-related boolean
     * flags (upPressed, downPressed, leftPressed, rightPressed) based on which key is pressed.
     *
     * @param e The KeyEvent object containing information about the key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        if(code== KeyEvent.VK_W){
            upPressed= true;
        }
        if(code== KeyEvent.VK_S){
            downPressed= true;
        }
        if(code== KeyEvent.VK_A){
            leftPressed= true;
        }
        if(code== KeyEvent.VK_D){
            rightPressed= true;
        }
    }

    /**
     * Invoked when a key has been released. This method updates the state of
     * movement-related boolean flags (upPressed, downPressed, leftPressed, rightPressed)
     * based on the key released.
     *
     * @param e the key event corresponding to the key that was released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code= e.getKeyCode();
        if(code== KeyEvent.VK_W){
            upPressed= false;
        }
        if(code== KeyEvent.VK_S){
            downPressed= false;
        }
        if(code== KeyEvent.VK_A){
            leftPressed= false;
        }
        if(code== KeyEvent.VK_D){
            rightPressed= false;
        }
    }
}

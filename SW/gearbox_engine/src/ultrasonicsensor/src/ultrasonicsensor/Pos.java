/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultrasonicsensor;

/**
 *
 * @author András
 */
public class Pos {
    
    private int x;
    private int y;

    public Pos(int posX, int posY) {
        x = posX;
        y = posY;
    }

    public int getPosX() {
        return x;
    }

    public void setPosX(int posX) {
        x = posX;
    }

    public int getPosY() {
        return y;
    }

    public void setPosY(int posY) {
        y = posY;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.nik.project.ultrasonicsensor;

/**
 *
 * @author András
 */
public class ObjPositions {

    Pos basePos;
    Pos[] positions;

    public Pos[] getPositions() {
        return positions;
    }

    public void setPositions(Pos[] positions) {
        this.positions = positions;
    }
    public Pos getBasePos() {
        return basePos;
    }

    public void setBasePos(Pos basePos) {
        this.basePos = basePos;
    }
    public ObjPositions(Pos basePosition,int width,int height) {
        basePos = basePosition;
        positions = new Pos[4];
        positions[0] = basePos;
        positions[1] = new Pos(basePos.getPosX()+width, basePos.getPosY());
        positions[2] = new Pos(basePos.getPosX()+width, basePos.getPosY()-height);
        positions[3] = new Pos(basePos.getPosX(), basePos.getPosY()-height);
    }
    
    
    
}

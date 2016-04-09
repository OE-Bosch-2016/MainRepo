package hu.nik.project.environment.objects;
import hu.nik.project.environment.ScenePoint;

import java.io.Serializable;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for tree
 */
public class Tree extends Misc implements Serializable {

    public enum TreeType {
        TREE_TOP_VIEW
    }

    private TreeType type;

    public Tree(ScenePoint basePosition, int rotation, TreeType type) throws SceneObjectException {
        super(basePosition, rotation);
        this.type = type;
    }

    public TreeType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " TreeType: " + type.toString();
    }
}

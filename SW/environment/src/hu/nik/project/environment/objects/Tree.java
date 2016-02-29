package hu.nik.project.environment.objects;

/**
 * Created by Róbert on 2016.02.27..
 *
 * Class for tree
 */
public class Tree extends Misc {

    public enum TreeType {
        TREE_TOP_VIEW
    }

    private TreeType type;

    public Tree(int positionX, int positionY, double rotation, TreeType type) throws SceneObjectException {
        super(positionX, positionY, rotation);
        this.type = type;
    }

    public TreeType getObjectType() {
        return type;
    }

    public String toString() {
        return super.toString() + " TreeType: " + type.toString();
    }
}

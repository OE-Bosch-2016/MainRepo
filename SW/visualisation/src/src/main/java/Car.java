import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Car {
    private Vector2D _position;
    private Vector2D _rotationVector;
    private float _degree;
    private BufferedImage _image;

    public Car(Vector2D position, BufferedImage image) {
        _position = position;
        _degree = 0;
        _rotationVector = new Vector2D(0,1);
        _image = image;
    }

    public Vector2D getPosition() {
        return _position;
    }

    public void setPosition(Vector2D position) {
        _position = position;
    }

    public BufferedImage getImage() {
        return _image;
    }

    public void setImage(BufferedImage image) {
        _image = image;
    }

    public void rotate(float degree){
        //Origo
        Vector2D origo = new Vector2D(
                _position.get_coordinateX() + _image.getWidth() / 2,
                _position.get_coordinateY() + _image.getHeight() / 2
        );

        //Rotate image
        //http://stackoverflow.com/questions/2257141/problems-rotating-bufferedimage
        BufferedImage copyImage = new BufferedImage(_image.getWidth(), _image.getHeight(), BufferedImage.TYPE_INT_RGB);
        AffineTransform xform = AffineTransform.getRotateInstance(degree,origo.get_coordinateX(), origo.get_coordinateY());
        Graphics2D g = (Graphics2D) copyImage.createGraphics();
        g.drawImage(_image, xform, null);
        g.dispose();
        _image = copyImage;
        copyImage.flush();

        _degree+=degree;

        //Rotating
        double r = Math.sqrt(
                Math.pow(_position.get_coordinateX() - origo.get_coordinateX(),2) +
                Math.pow(_position.get_coordinateY() - origo.get_coordinateY(),2)
        );

        _rotationVector = new Vector2D((int)(r * Math.cos(degree)),(int)(r * Math.sin(degree)));
    }

    public void move(float speed)
    {
        _position = new Vector2D(
                (int)(_position.get_coordinateX() + speed *  _rotationVector.get_coordinateX()),
                (int)(_position.get_coordinateY() + speed * _rotationVector.get_coordinateY())
        );
    }
}


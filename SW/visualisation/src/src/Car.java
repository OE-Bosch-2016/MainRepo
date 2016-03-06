import java.awt.image.BufferedImage;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Car {
    Position _position;
    float _degree;
    BufferedImage _image;

    public Car(Position position, float degree, BufferedImage image) {
        _position = position;
        _degree = degree;
        _image = image;
    }

    public Position getPosition() {
        return _position;
    }

    public void setPosition(Position position) {
        _position = position;
    }

    public BufferedImage getImage() {
        return _image;
    }

    public void setImage(BufferedImage image) {
        _image = image;
    }

    public void RotateCar(float degree){
        _degree+=degree;
    }
}

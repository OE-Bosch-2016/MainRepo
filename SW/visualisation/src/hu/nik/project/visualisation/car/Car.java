package hu.nik.project.visualisation.car;

import hu.nik.project.utils.ImageLoader;
import hu.nik.project.utils.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by secured on 2016. 03. 06..
 */
public abstract class Car {
    private Vector2D _position;
    private Vector2D _rotationVector;
    private double _degree;
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

    public void rotate(double degree){

        degree = Math.toDegrees(degree);
        //Origo
        Vector2D origo = new Vector2D(
                (int)( _position.get_coordinateX() + _image.getWidth() / 2),
                (int)(_position.get_coordinateY() + _image.getHeight() / 2)
        );

        //Rotate image
        //http://stackoverflow.com/questions/2257141/problems-rotating-bufferedimage
//        BufferedImage copyImage = new BufferedImage(_image.getWidth(), _image.getHeight(), BufferedImage.TYPE_INT_RGB);
//        AffineTransform xform = AffineTransform.getRotateInstance(Math.toRadians(degree),origo.get_coordinateX(), origo.get_coordinateY());
//        Graphics2D g = (Graphics2D) copyImage.createGraphics();
//        g.drawRenderedImage(_image, xform);
//        g.dispose();
//        _image = copyImage;
//        copyImage.flush();

        _degree+=degree;

        //Rotating
        double r = Math.sqrt(
                Math.pow(_position.get_coordinateX() - origo.get_coordinateX(),2) +
                Math.pow(_position.get_coordinateY() - origo.get_coordinateY(),2)
        );


        //Eigenvector
        _rotationVector = new Vector2D(
                (int)((r * Math.cos(degree)) / Math.abs(r * Math.cos(degree))),
                (int)((r * Math.sin(degree)) /  Math.abs(r * Math.sin(degree)))
        );

        double sin = Math.abs(Math.sin(degree)), cos = Math.abs(Math.cos(degree));
        int w = _image.getWidth(), h = _image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(degree, w/2, h/2);
        g.drawRenderedImage(_image, null);
        g.dispose();

        _image = result;
    }

    public BufferedImage rotation(double degree){
        _degree = degree;
        degree = Math.toRadians(degree);
        set_image(ImageLoader.getCarImage());
        int w = _image.getWidth(), h = _image.getHeight();
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(_image.getWidth(), _image.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((_image.getWidth()-w)/2, (_image.getHeight()-h)/2);
        g.rotate(degree, w / 2, h / 2);
        g.drawRenderedImage(_image, null);
        g.dispose();

        _image = null;
        _image = result;
        return result;
    }

    private GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public void set_image(BufferedImage _image) {
        this._image = _image;
    }

    public void move(float speed)
    {
        //TODO: We need to clarify this
        // speed is in km/h
        // 1km/h = 1px/render phase

        double moveX = Math.sin(Math.toRadians(_degree)) * speed;
        speed *= -1;
        double moveY = Math.cos(Math.toRadians(_degree)) * speed;

        _position = new Vector2D(
                (float)(_position.get_coordinateX() + moveX),
                (float)(_position.get_coordinateY() + moveY)
        );
    }
}


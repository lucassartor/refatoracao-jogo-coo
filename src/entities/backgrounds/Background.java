package entities.backgrounds;

import game.*;

import java.awt.Color;
import java.util.ArrayList;

public class Background {
    private ArrayList<Double> X;
    private ArrayList<Double> Y;
    private final double speed;
    private double count;
    private final int size;
    private final Color color;
    private final int width;
    private final int height;

    public Background(double speed, double count, int size, Color color, int width, int height){
        this.X = new ArrayList<Double>();
        this.Y = new ArrayList<Double>();
        this.speed = speed;
        this.count = count;
        this.size = size;
        this.height = height;
        this.width = width;
        this.color = color;

        initializeBackground();
    }

    public double getCount() {
        return count;
    }
    private void setCount(double count) {
        this.count = count;
    }
    public int getSize(){
        return size;
    }

    public void draw(){
        GameLib.setColor(color);
        setCount(count + (speed * Game.getDelta()));
        for(int i = 0; i < getX().size(); i++)
            GameLib.fillRect(getX().get(i), (getY().get(i) + getCount()) % GameLib.HEIGHT, width, height);
    }

    public void initializeBackground(){
        for(int i = 0; i < getSize(); i++){
            getX().add(Math.random() * GameLib.WIDTH);
            getY().add(Math.random() * GameLib.HEIGHT);
        }
    }

    public ArrayList<Double> getY() {
        return Y;
    }
    public ArrayList<Double> getX() {
        return X;
    }

}
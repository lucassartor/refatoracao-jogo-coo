package entities;

public interface Animations {
    double getExplosionStart();
    double getExplosionEnd();
    void setExplosionStart(double explosionStart);
    void setExplosionEnd(double explosionEnd);
    void draw();
    void explode();
}

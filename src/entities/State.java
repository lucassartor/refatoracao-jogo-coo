package entities;

public enum State {
    INACTIVE(0),
    ACTIVE(1),
    EXPLODING(2);

    public final int value;

    State(int value) {
        this.value = value;
    }

    public boolean isActive() {
        return this.value == 1;
    }

    public boolean isExploding() {
        return this.value == 2;
    }
}

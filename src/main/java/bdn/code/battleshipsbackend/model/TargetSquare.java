package bdn.code.battleshipsbackend.model;

public enum TargetSquare {

    HIT(3),
    MISS(2);

    private int targetSquare;

    TargetSquare(int i) {

        this.targetSquare = i;
    }

    public int getValue() {

        return targetSquare;
    }
}

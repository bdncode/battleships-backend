package bdn.code.battleshipsbackend.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Data
public class Square {

    private int x;
    private int y;


    public boolean isValid() {

        return isInRange(this.x) && isInRange(this.y);
    }

    private boolean isInRange(int i) {

        return -1 < i && i < 10;
    }

    public Square build() {

        Random random = new Random();
        return new Square() {{
            setX(random.nextInt(10));
            setY(random.nextInt(10));
        }};
    }

    public List<Square> generateSquareList(Square square) {

        List<Square> squareList = new LinkedList<>();

        for (int x = square.getX()-1; x < square.getX()+2; x = x+2) {
            for (int y = square.getY()-1; y < square.getY()+2; y = y+2) {
                if (isInRange(x) && isInRange(y)) {

                    int finalX = x;
                    int finalY = y;
                    Square s = new Square() {{
                        setX(finalX);
                        setY(finalY);
                    }};
                    squareList.add(s);
                }
            }
        }
        return squareList;
    }
}

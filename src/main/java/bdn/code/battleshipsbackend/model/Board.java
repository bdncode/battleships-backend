package bdn.code.battleshipsbackend.model;

import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
public class Board {

    private int[][] board;

    public int[][] build() {

        List<Integer> ships = Arrays
                .asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        Collections.shuffle(ships);
        int split = new Random().nextInt(9) + 1;
        List<Integer> shipsVertical = ships.subList(0,split);
        List<Integer> shipsHorizontal = ships.subList(split,10);
        Supplier<Integer> randomRow = () -> new Random().nextInt(10);
        Function<Integer, Integer> randomColumn = length -> new Random().nextInt(10-length);

        do {

            board = new int[10][10];
            for (int shipSize : shipsVertical) {

                Integer row = randomRow.get();
                Integer start = randomColumn.apply(shipSize);
                for (int i = start; i < start + shipSize; i++) {

                    board[row][i] = 1;
                }
            }

            for (int shipSize : shipsHorizontal) {

                Integer row = randomRow.get();
                Integer start = randomColumn.apply(shipSize);
                for (int i = start; i < start + shipSize; i++) {

                    board[i][row] = 1;
                }
            }
        } while (!isValid());
        return board;
    }

    public int[][] buildEmpty() {

        return new int[10][10];
    }

    public boolean isValid() {

        if (!straightLine(board)) return false;

        List<String> ships = new ArrayList<>(6) {{
            addAll(horizontalLine(board));
            addAll(verticalLine(board));
        }};

        if (ships.size() != 6) {
            return false;
        }

        Iterator<String> shipsIterator = ships.stream()
                .sorted()
                .collect(Collectors.toList())
                .iterator();
        Iterator<String> validIterator = Arrays.asList("11", "11", "11", "111", "111", "1111")
                .iterator();

        while (shipsIterator.hasNext() || validIterator.hasNext()) {
            if (!shipsIterator.next().equals(validIterator.next())) {
                return false;
            }
        }
        return true;
    }

    private boolean straightLine(int[][] field) {
        for (int i = 0; i < field.length - 1; i++) {
            for (int j = 0; j < field[i].length - 1; j++) {
                if (field[i][j] + field[i + 1][j + 1] > 1 ||
                        field[i][j + 1] + field[i + 1][j] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<String> horizontalLine(int[][] field) {

        List<String> linesList = new LinkedList<>();
        List<String> outputList = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                stringBuilder.append(field[i][j]);
            }
            linesList.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        for (String line : linesList) {
            for (String s : line.split("0+")) {
                if (!s.equals("1") && !s.equals("")) {
                    outputList.add(s);
                }
            }
            for (String s : line.split("")) {
                if (s.equals("1")) {
                    counter = counter + 1;
                }
            }
        }
        return counter == 20 ? outputList : new ArrayList<>(0);
    }

    private List<String> verticalLine(int[][] field) {

        List<String> linesList = new LinkedList<>();
        List<String> outputList = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                stringBuilder.append(field[j][i]);
            }
            linesList.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        for (String line : linesList) {
            for (String s : line.split("0+")) {
                if (!s.equals("1") && !s.equals("")) {
                    outputList.add(s);
                }
            }
            for (String s : line.split("")) {
                if (s.equals("1")) {
                    counter = counter + 1;
                }
            }
        }
        return counter == 20 ? outputList : new ArrayList<>(0);
    }

    public void showBattleField() {
        for (int[] rows : board) {
            System.out.print("{");
            for (int column : rows) {
                System.out.print(column + ", ");
            }
            System.out.println("},");
        }
        System.out.println();
    }
}

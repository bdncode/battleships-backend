package bdn.code.battleshipsbackend.service;

import bdn.code.battleshipsbackend.exception.ApiExceptionMessages;
import bdn.code.battleshipsbackend.exception.InvalidBoardException;
import bdn.code.battleshipsbackend.model.Board;
import bdn.code.battleshipsbackend.model.Square;
import bdn.code.battleshipsbackend.model.TargetSquare;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CpuTurnService {

    int[][] playerBoard;
    int playerShipSquares;
    int cpuShots = 0;

    Set<Square> selectedSquaresSet = new HashSet<>();
    List<Square> selectedSquaresList = new LinkedList<>();

    public boolean prepareNewGame() {

        this.cpuShots = 0;
        this.playerShipSquares = 20;
        this.selectedSquaresSet.clear();
        this.selectedSquaresList.clear();
        return true;
    }

    public boolean receivePlayerBoard(Board playerBoard) {

        if (!playerBoard.isValid()) {

            throw new InvalidBoardException(ApiExceptionMessages.INVALID_PLAYER_BOARD.getMessage());
        }
        this.playerBoard = playerBoard.getBoard();
        return true;
    }

    public JSONObject fetchCpuSquare() {

        Square cpuSquare;
        boolean contains;
        do {

            cpuSquare = new Square().build();
            contains = this.selectedSquaresSet.contains(cpuSquare);
        } while (contains);

        this.selectedSquaresSet.add(cpuSquare);
        this.cpuShots = this.cpuShots + 1;
        int x = cpuSquare.getX();
        int y = cpuSquare.getY();
        int i = this.playerBoard[x][y];

        if (i == 1) {

            this.playerShipSquares = this.playerShipSquares - 1;
            if (this.playerShipSquares == 0) {

                return getJsonCpuSquareResponse(x, y, this.cpuShots);
            }

            List<Square> squareList = new Square().generateSquareList(cpuSquare);
            this.selectedSquaresSet.addAll(squareList);
            return getJsonCpuSquareResponse(x, y, TargetSquare.HIT.getValue());
        }
        return getJsonCpuSquareResponse(x, y, TargetSquare.MISS.getValue());
    }

    private JSONObject getJsonCpuSquareResponse(int x, int y, int squareResult) {

        return new JSONObject() {{
            put("x", x);
            put("y", y);
            put("squareResult", squareResult);
        }};
    }
}
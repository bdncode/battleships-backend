package bdn.code.battleshipsbackend.service;

import bdn.code.battleshipsbackend.exception.ApiExceptionMessages;
import bdn.code.battleshipsbackend.model.Board;
import bdn.code.battleshipsbackend.model.Square;
import bdn.code.battleshipsbackend.model.TargetSquare;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PlayerTurnService {

    boolean gameStarted = false;
    int[][] cpuBoard;
    int[][] trackingBoard;
    int cpuShipSquares;
    int playerShots;

    public int[][] fetchTrackingBoard() {

        if (!this.gameStarted) {

            this.cpuBoard = new Board().build();
            this.trackingBoard = new Board().buildEmpty();
            this.gameStarted = true;
            return this.trackingBoard;
        }
        return this.trackingBoard;
    }

    public JSONObject receivePlayerSquare(Square playerSquare) {

        if (!playerSquare.isValid()) {

            throw new RuntimeException(ApiExceptionMessages.INVALID_PLAYER_SQUARE.getMessage());
        }

        this.playerShots = this.playerShots + 1;
        int x = playerSquare.getX();
        int y = playerSquare.getY();
        int i = this.cpuBoard[x][y];

        if (i == 1) {

            this.cpuShipSquares = this.cpuShipSquares - 1;
            int[][] updatedTrackingBoardWithHit = updateTrackingBoard(x, y, TargetSquare.HIT.getValue());
            if (this.cpuShipSquares == 0) {

                return getJsonPlayerSquareResponse(updatedTrackingBoardWithHit, this.playerShots);
            }

            return getJsonPlayerSquareResponse(updatedTrackingBoardWithHit, TargetSquare.HIT.getValue());
        }
        int[][] updateTrackingBoardWithMiss = updateTrackingBoard(x, y, TargetSquare.MISS.getValue());
        return getJsonPlayerSquareResponse(updateTrackingBoardWithMiss, TargetSquare.MISS.getValue());
    }

    private int[][] updateTrackingBoard(int x, int y, int squareResult) {

        this.trackingBoard[x][y] = squareResult;
        return this.trackingBoard;
    }

    private JSONObject getJsonPlayerSquareResponse(int[][] updateTrackingBoard, int squareResult) {

        return new JSONObject() {{
            put("cpuBoard", updateTrackingBoard);
            put("squareResult", squareResult);
        }};
    }

    public boolean prepareNewGame() {

        this.gameStarted = false;
        this.playerShots = 0;
        this.cpuShipSquares = 20;
        return true;
    }
}

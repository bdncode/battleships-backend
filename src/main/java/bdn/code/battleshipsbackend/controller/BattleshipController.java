package bdn.code.battleshipsbackend.controller;

import bdn.code.battleshipsbackend.exception.InvalidBoardException;
import bdn.code.battleshipsbackend.model.Board;
import bdn.code.battleshipsbackend.model.Square;
import bdn.code.battleshipsbackend.service.CpuTurnService;
import bdn.code.battleshipsbackend.service.PlayerTurnService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = bdn.code.battleshipsbackend.controller.BattleshipController.BASE_URL)
public class BattleshipController {

    public static final String BASE_URL = "/api/v1/battle";
    public static final String NEW_GAME = "/new-game";
    public static final String PLAYER_BOARD_URI = "/player-board";
    public static final String CPU_BOARD_URI = "/cpu-board";
    public static final String RECEIVE_GUESS_SQUARE_URI = "/player-square";
    public static final String SEND_GUESS_SQUARE_URI = "/cpu-square";
    @Autowired
    CpuTurnService cpuTurnService;
    @Autowired
    PlayerTurnService playerTurnService;

    @GetMapping(NEW_GAME)
    public ResponseEntity<Boolean>prepareNewGame() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        boolean newGameReady;
        try {

            newGameReady = cpuTurnService.prepareNewGame()
            && playerTurnService.prepareNewGame();
        } catch (Exception defaultException) {

            throw new RuntimeException(defaultException.getMessage());
        }
        return new ResponseEntity<>(newGameReady, headers, HttpStatus.OK);
    }

    @PostMapping(PLAYER_BOARD_URI)
    public ResponseEntity<Boolean> receivePlayerBoard(@RequestBody Board playerBoard) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        boolean validBoard;
        try {

            validBoard = cpuTurnService.receivePlayerBoard(playerBoard);
        } catch (InvalidBoardException invalidBoardException) {

            throw new InvalidBoardException(invalidBoardException.getMessage());
        } catch (Exception defaultException) {

            throw new RuntimeException(defaultException.getMessage());
        }
        return new ResponseEntity<>(validBoard, headers, HttpStatus.OK);
    }

    @GetMapping(CPU_BOARD_URI)
    public ResponseEntity<int[][]> fetchTrackingBoard() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        int[][] cpuBoard;
        try {

            cpuBoard = playerTurnService.fetchTrackingBoard();
        } catch (Exception defaultException) {

            throw new RuntimeException(defaultException.getMessage());
        }
        return new ResponseEntity<>(cpuBoard, headers, HttpStatus.OK);
    }

    @PostMapping(RECEIVE_GUESS_SQUARE_URI)
    public ResponseEntity<JSONObject> receivePlayerSquare(@RequestBody Square playerSquare) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject playerSquareResult;
        try {

            playerSquareResult = playerTurnService.receivePlayerSquare(playerSquare);
        } catch (Exception defaultException) {

            throw new RuntimeException(defaultException.getMessage());
        }
        return new ResponseEntity<>(playerSquareResult, headers, HttpStatus.OK);
    }

    @GetMapping(SEND_GUESS_SQUARE_URI)
    public ResponseEntity<JSONObject> fetchCpuSquare() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject cpuSquareResult;
        try {

            cpuSquareResult = cpuTurnService.fetchCpuSquare();
        } catch (Exception defaultException) {

            throw new RuntimeException(defaultException.getMessage());
        }
        return new ResponseEntity<>(cpuSquareResult, headers, HttpStatus.OK);
    }
}

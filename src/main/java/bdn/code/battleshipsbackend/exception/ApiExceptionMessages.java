package bdn.code.battleshipsbackend.exception;

public enum ApiExceptionMessages {

    INTERNAL_SERVER_ERROR("Oops, something went wrong"),
    INVALID_PLAYER_SQUARE("Square out of board"),
    INVALID_PLAYER_BOARD("The battlefield positions are not valid");

    private final String message;

    ApiExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

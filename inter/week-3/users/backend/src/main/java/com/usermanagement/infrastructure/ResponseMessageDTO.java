package com.usermanagement.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Timur Berezhnoi.
 */
public class ResponseMessageDTO {
    private final String message;

    /**
     * Creates a ResponseMessageDTO detailed message.
     *
     * @param message is a detailed message.
     */
    @JsonCreator
    public ResponseMessageDTO(@JsonProperty("message") final String message) {
        this.message = message;
    }

    /**
     * @return current response message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseMessageDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}

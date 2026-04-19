package ru.cherkesova.exception;

import java.time.Instant;

public record ApiError(
        Instant timestamp,
        int status,
        String message,
        String path
) {
}


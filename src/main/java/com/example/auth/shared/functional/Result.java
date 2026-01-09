package com.example.auth.shared.functional;

import java.util.Optional;
import java.util.function.Function;

/**
 * Tipo Result para manejo funcional de errores.
 * Demuestra programación funcional y manejo de efectos.
 * 
 * @param <T> Tipo del valor exitoso
 * @param <E> Tipo del error
 */
public sealed interface Result<T, E> permits Result.Success, Result.Failure {
    
    record Success<T, E>(T value) implements Result<T, E> {}
    record Failure<T, E>(E error) implements Result<T, E> {}

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default Optional<T> getValue() {
        return switch (this) {
            case Success<T, E>(T value) -> Optional.of(value);
            case Failure<T, E> ignored -> Optional.empty();
        };
    }

    default Optional<E> getError() {
        return switch (this) {
            case Success<T, E> ignored -> Optional.empty();
            case Failure<T, E>(E error) -> Optional.of(error);
        };
    }

    default <U> Result<U, E> map(Function<T, U> mapper) {
        return switch (this) {
            case Success<T, E>(T value) -> Result.success(mapper.apply(value));
            case Failure<T, E>(E error) -> Result.failure(error);
        };
    }

    default <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper) {
        return switch (this) {
            case Success<T, E>(T value) -> mapper.apply(value);
            case Failure<T, E>(E error) -> Result.failure(error);
        };
    }
}


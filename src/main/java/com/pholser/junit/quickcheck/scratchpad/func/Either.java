package com.pholser.junit.quickcheck.scratchpad.func;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Either<L, R> {
    private final Optional<L> left;
    private final Optional<R> right;

    private Either(Optional<L> left, Optional<R> right) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Either<A, B> makeLeft(A left) {
        return new Either<>(Optional.of(left), Optional.empty());
    }

    public static <A, B> Either<A, B> makeRight(B right) {
        return new Either<>(Optional.empty(), Optional.of(right));
    }

    public <T> T map(
        Function<? super L, ? extends T> ifLeft,
        Function<? super R, ? extends T> ifRight) {

        return left.map(ifLeft).orElseGet(() -> right.map(ifRight).get());
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Either<?, ?>))
            return false;

        Either<?, ?> other = (Either<?, ?>) o;
        return left.equals(other.left) && right.equals(other.right);
    }

    @Override public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override public String toString() {
        return map(
            left -> "Left[" + left + ']',
            right -> "Right[" + right + ']');
    }
}

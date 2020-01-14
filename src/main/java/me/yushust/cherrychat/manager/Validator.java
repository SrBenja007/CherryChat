package me.yushust.cherrychat.manager;

public interface Validator<T> {

    boolean isValid(T value);

    boolean anyValid(T value);

}

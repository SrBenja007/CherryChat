package me.yushust.cherrychat.manager;

public interface StringValidator extends Validator<String> {

    String replaceAll(String value, String replaceValue);

}

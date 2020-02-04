package me.yushust.cherrychat.tests;

import me.yushust.cherrychat.util.Texts;
import org.junit.Test;

import java.util.ArrayList;


public class TextsTest {

    @Test
    public void test() {
        String text = "Hellooooooooooooooooooooooooooooooooo";
        System.out.println(Texts.decreaseAlphaNumerics(text, 5));
    }

    @Test
    public void capsTest() {
        String text = "HELLO there";
        System.out.println(Texts.capitalizeFirst(Texts.toLowerCase(text, 5)));
    }

    @Test
    public void classNameTest() {
        String canonicalName = String.class.getCanonicalName();
        String simpleName = String.class.getSimpleName();
        String typeName = String.class.getTypeName();
        String name = String.class.getName();
        System.out.println(String.format("Canonical: %s, Simple: %s, Type: %s, Name: %s", canonicalName, simpleName, typeName, name));
    }

    @Test
    public void messUpTest() {
        String text = "Yushu";
        System.out.println(Texts.messUp(text));
    }

}

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

}

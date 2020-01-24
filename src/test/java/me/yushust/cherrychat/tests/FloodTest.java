package me.yushust.cherrychat.tests;

import me.yushust.cherrychat.util.Texts;
import org.junit.Test;

public class FloodTest {

    @Test
    public void test() {
        String text = "Hellooooooooooooooooooooooooooooooooo";
        System.out.println(Texts.decreaseAlphaNumerics(text, 5));
    }

}

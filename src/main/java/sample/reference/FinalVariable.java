package sample.reference;

import java.util.ArrayList;
import java.util.List;

public class FinalVariable {

    public static void main(String[] args) {
        Integer actual = new Integer(1);
        List<Integer> finals = new ArrayList<>();
        while (actual <=4) {
            final Integer finalActual = actual;
            finals.add(finalActual);
            actual = actual + 1;
        }
        System.out.println("finals: " + finals);
    }
}

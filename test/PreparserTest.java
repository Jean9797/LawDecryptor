import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreparserTest {
    @Test
    void preparse() {
        Preparser preparser = new Preparser();
        List<String> input = new LinkedList<>();
        input.add("©Kancelaria Sejmu s. 4/73");
        input.add("2017-06-28");
        input.add("e) prawo do całego albo do części mienia innego przedsiębiorcy");
        input.add("Art. 2. 1. Ustawa nie narusza praw przysługujących na podstawie przepisów");
        input.add("wydane przed dniem wejścia w życie Konstytucji, stają się uchwałami albo za-");
        input.add("rządzeniami w rozumieniu art. 93 Konstytucji.");
        input.add(".");
        input.add("a");
        input.add("");
        input.add("6) (uchylony)");
        input.add("Art. 115–129. (pominięte)");

        List<String> properOutput = new LinkedList<>();
        properOutput.add("e) prawo do całego albo do części mienia innego przedsiębiorcy");
        properOutput.add("Art. 2.");
        properOutput.add("1. Ustawa nie narusza praw przysługujących na podstawie przepisów");
        properOutput.add("wydane przed dniem wejścia w życie Konstytucji, stają się uchwałami albo");
        properOutput.add("zarządzeniami w rozumieniu art. 93 Konstytucji.");

        List<String> output = preparser.preparse(input);

        assertEquals(properOutput.size(), output.size());
        for (int i = 0; i < properOutput.size(); i++){

        }

        Iterator<String> outputIterator = output.iterator();
        Iterator<String> properOutputIterator = properOutput.iterator();
        while(properOutputIterator.hasNext() && outputIterator.hasNext()){
            assertTrue(properOutputIterator.next().equals(outputIterator.next()));
        }
    }

}
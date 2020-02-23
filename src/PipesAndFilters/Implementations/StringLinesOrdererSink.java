package PipesAndFilters.Implementations;

import PipesAndFilters.Base.FilterBehaviour;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.TreeSet;

public class StringLinesOrdererSink extends FilterBehaviour {

    TreeSet<String> shifts = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    public StringLinesOrdererSink() {
        Reader = new PipedReader();
        Writer = new PipedWriter();
    }

    @Override
    public void run() {
        try {
            StringBuilder strBldr = new StringBuilder();
            int i;
            while ((i = Reader.read()) != -1) {
                char character = (char) i;
                if (character == '\n') {
                    shifts.add(strBldr.toString());
                    strBldr = new StringBuilder();
                } else {
                    strBldr.append((char) i);
                }
            }
            Reader.close();
            shifts.forEach(s -> {
                try {
                    Writer.write(s + '\n');
                } catch (Exception e) {
                }
            });
            Writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

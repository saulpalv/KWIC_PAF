package PipesAndFilters.Piped.Implementations;

import PipesAndFilters.Piped.Foundation.FilterBehaviourReaderWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringLineCircularShifterSinkPiped extends FilterBehaviourReaderWriter {

    public StringLineCircularShifterSinkPiped() {
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
                    writeShifts(strBldr.toString());
                    strBldr = new StringBuilder();
                } else {
                    strBldr.append((char) i);
                }
            }
            Reader.close();
            Writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeShifts(String strLine) throws IOException {
        List<String> shift = new ArrayList(Arrays.asList(strLine.split(" ")));
        for (int i = 0; i < shift.size(); i++) {
            //Concat elements of list in a single line
            StringBuilder strBldr = new StringBuilder();
            shift.forEach(word -> strBldr.append(word + " "));
            String shiftedLine = strBldr.toString();

            //write data to next filter
            Writer.write(shiftedLine + '\n');

            //shift one word
            String shiftedWord = shift.remove(0);
            shift.add(shiftedWord);
        }
    }
}


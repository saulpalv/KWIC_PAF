import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PipedWriter pw1 = new PipedWriter();
        PipedReader pr1 = new PipedReader(pw1);
        PipedWriter pw2 = new PipedWriter();
        PipedReader pr2 = new PipedReader(pw2);
        PipedWriter pw3 = new PipedWriter();
        PipedReader pr3 = new PipedReader(pw3);

        File file = new File("./src/input_iMDB.txt");

        Thread readerPipe = new Thread(() -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    pw1.write(strLine + '\n');
                }
                pw1.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Thread shifterPipe = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder strBldr = new StringBuilder();
                    int i;
                    while ((i = pr1.read()) != -1) {
                        char character = (char) i;
                        if (character == '\n') {
                            writeShifts(strBldr.toString());
                            strBldr = new StringBuilder();
                        } else {
                            strBldr.append((char) i);
                        }
                    }
                    pr1.close();
                    pw2.close();
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
                    pw2.write(shiftedLine + '\n');

                    //shift one word
                    String shiftedWord = shift.remove(0);
                    shift.add(shiftedWord);
                }
            }
        });

        Thread orderingPipe = new Thread(new Runnable() {
            List<String> shifts = new ArrayList<>();

            @Override
            public void run() {
                try {
                    StringBuilder strBldr = new StringBuilder();
                    int i;
                    while ((i = pr2.read()) != -1) {
                        char character = (char) i;
                        if (character == '\n') {
                            saveShift(strBldr.toString());
                            strBldr = new StringBuilder();
                        } else {
                            strBldr.append((char) i);
                        }
                    }
                    pr2.close();
                    Collections.sort(shifts);
                    shifts.forEach(s -> {
                        try {
                            pw3.write(s + '\n');
                        } catch (Exception e) {
                        }
                    });
                    pw3.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            private void saveShift(String line) {
                shifts.add(line);
            }
        });

        Thread writerPipe = new Thread(() -> {
            File filename = new File("./src/output.txt");
            try {
                // Reiniciar archivo de salida.
                BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(filename));
                bufferWritter.write("");
                bufferWritter.close();

                int i;

                FileWriter fileWritter = new FileWriter(filename, true);
                bufferWritter = new BufferedWriter(fileWritter);

                while ((i = pr3.read()) != -1) {
                    bufferWritter.append((char) i);
                }
                bufferWritter.close();
                fileWritter.close();
                pr3.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        readerPipe.start();
        shifterPipe.start();
        orderingPipe.start();
        writerPipe.start();
    }
}
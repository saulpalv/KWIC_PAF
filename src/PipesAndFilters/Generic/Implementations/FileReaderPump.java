package PipesAndFilters.Generic.Implementations;

import PipesAndFilters.Generic.Foundations.FilterBehaviour;

import java.io.*;

public class FileReaderPump extends FilterBehaviour<BufferedReader, PipedWriter> {
    String filePath;

    public FileReaderPump(String path) {
        this.filePath = path;
        try {
            Reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer = new PipedWriter();
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = Reader;
        try {
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                Writer.write(strLine + '\n');
            }
            Reader.close();
            Writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package PipesAndFilters.Generic.Implementations;

import PipesAndFilters.Generic.Foundations.FilterBehaviour;

import java.io.*;

public class FileWriterSink extends FilterBehaviour<PipedReader, BufferedWriter> {
    String filePath;

    public FileWriterSink(String path){
        this.filePath = path;
        try {
            Writer = new BufferedWriter(new FileWriter(path)); //true
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            int i;
            while ((i = Reader.read()) != -1) {
                Writer.append((char) i);
            }
            Reader.close();
            Writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
package PipesAndFilters.Implementations;

import PipesAndFilters.Base.FilterBehaviour;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedReader;

public class FileWriterSink extends FilterBehaviour {
    String filePath;

    public FileWriterSink(String fileName){
        this.filePath = "./src/" + fileName;
        Reader = new PipedReader();
        try {
            Writer = new BufferedWriter(new FileWriter(fileName)); //true
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
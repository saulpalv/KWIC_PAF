package PipesAndFilters.Piped.Implementations;

import PipesAndFilters.Piped.Foundation.FilterBehaviourReaderWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedReader;

public class FileWriterSinkPiped extends FilterBehaviourReaderWriter {
    String filePath;

    public FileWriterSinkPiped(String fileName){
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
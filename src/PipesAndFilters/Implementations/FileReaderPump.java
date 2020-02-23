package PipesAndFilters.Implementations;

import PipesAndFilters.Base.FilterBehaviour;

import java.io.*;

public class FileReaderPump extends FilterBehaviour {
    String filePath;

    public FileReaderPump(String fileName) {
        this.filePath = "./src/" + fileName;
        try {
            Reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer = new PipedWriter();
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = (BufferedReader)Reader;
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

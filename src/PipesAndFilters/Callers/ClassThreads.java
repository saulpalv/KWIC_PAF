package PipesAndFilters.Callers;

import PipesAndFilters.Base.ThreadFilter;
import PipesAndFilters.Implementations.FileReaderPump;
import PipesAndFilters.Implementations.FileWriterSink;
import PipesAndFilters.Implementations.StringLineCircularShifterSink;
import PipesAndFilters.Implementations.StringLinesOrdererSink;

import java.io.*;
import java.util.*;

public class ClassThreads {
    public static void run() throws IOException {

        String input;
        String output;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese nombre de archivo de entrada: ");
        input = scanner.nextLine();
        System.out.println("Ingrese nombre de archivo de salida: ");
        output = scanner.nextLine();

        ThreadFilter reader = new ThreadFilter(new FileReaderPump(input));
        ThreadFilter shifter = new ThreadFilter(new StringLineCircularShifterSink(), reader);
        ThreadFilter orderer = new ThreadFilter(new StringLinesOrdererSink(), shifter);
        ThreadFilter writer = new ThreadFilter(new FileWriterSink(output),orderer);

        reader.start();
        shifter.start();
        orderer.start();
        writer.start();

    }
}
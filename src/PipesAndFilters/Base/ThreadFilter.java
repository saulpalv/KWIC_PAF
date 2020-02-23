package PipesAndFilters.Base;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.List;

public class ThreadFilter extends Thread {

    public static List<ThreadFilter> Filters = new ArrayList<>();

    public FilterBehaviour Behaviour;

    public static ThreadFilter getFilter(FilterBehaviour pump){
        return new ThreadFilter(pump);
    }

    public static ThreadFilter getFilter(FilterBehaviour sink, ThreadFilter previous){
       return new ThreadFilter(sink, previous);
    }

    public ThreadFilter(FilterBehaviour behaviour) {
        super(behaviour);
        this.Behaviour = behaviour;
        Filters.add(this);
    }

    public ThreadFilter(FilterBehaviour behaviour, ThreadFilter previous) {
        this(behaviour);
        connectPump(previous);
    }

    public boolean connectPump(ThreadFilter pump){
        boolean done = false;
        try {
            Behaviour.Reader = new PipedReader((PipedWriter) pump.Behaviour.Writer);
            done = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return done;
    }

    public boolean connectSink(ThreadFilter sink){
        boolean done = false;
        try {
            sink.Behaviour.Reader = new PipedReader((PipedWriter) Behaviour.Writer);
            done = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return done;
    }

    public static void starFilters(){
        Filters.forEach(ThreadFilter::run);
    }
}

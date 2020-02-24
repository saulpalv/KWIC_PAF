package PipesAndFilters.Generic.Foundations;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 * Generic Threaded Filter Class For Pipes and Filters
 * @param <R> please use a type from java.io namespace
 * @param <W> please use a type from java.io namespace
 */
public class ThreadFilter<R, W> extends Thread {

    public static List<ThreadFilter> Filters = new ArrayList<>();

    public FilterBehaviour<R, W> Behaviour;

    public Type wType;
    public Type rType;
    public Class<R> rClass;
    public Class<W> wClass;

    public ThreadFilter(FilterBehaviour<R, W> behaviour) {
        super(behaviour);
        this.Behaviour = behaviour;
        Filters.add(this);

        ParameterizedType bbClass = (ParameterizedType) Behaviour.getClass().getGenericSuperclass();
        rType = bbClass.getActualTypeArguments()[0];
        wType = bbClass.getActualTypeArguments()[1]; //or better : wType = behaviour.Writer.getClass();
        wClass = (Class<W>) wType;
        rClass = (Class<R>) rType;
            /*Field field = bbClass.getDeclaredField("Reader");
            var type = (Class<R>)field.getType();
            rClass = (Class<R>) rType;
            Class<R> rClass = (Class<R>)field.getType(); // Class object for java.lang.String
            */
    }

    public ThreadFilter(FilterBehaviour<R, W> behaviour, ThreadFilter previous) {
        this(behaviour);
        connectPump(previous);
    }

    @Override
    public String toString() {
        String behaviourTypeName = Behaviour.getClass().getSimpleName();
        String readerTypeName = rClass.getSimpleName();
        String writerTypeNmae = wClass.getSimpleName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Filter with " + behaviourTypeName + " behaviour :");
        stringBuilder.append("\n");
        stringBuilder.append("Reader : " + readerTypeName);
        stringBuilder.append("\n");
        stringBuilder.append("Writter : " + writerTypeNmae);
        return stringBuilder.toString();
    }

    public boolean connectPump(ThreadFilter pump) {

        boolean done = false;
        try {
            Constructor<R> rConstructor = rClass.getDeclaredConstructor(pump.wClass);
            System.out.println(rConstructor);
            Behaviour.Reader = rConstructor.newInstance(pump.Behaviour.Writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        done = true;
        return done;
    }

    public boolean connectSink(ThreadFilter sink) {
        boolean done = false;
        try {
            sink.Behaviour.Reader = new PipedReader((PipedWriter) Behaviour.Writer);
            done = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return done;
    }

    public static List<ThreadFilter> buildNetWork(FilterBehaviour... behaviours) {
        List<ThreadFilter> filters = new ArrayList<>();
        ThreadFilter previousFilter = null;
        for (FilterBehaviour fb : behaviours) {
            ThreadFilter actualFilter;
            if (previousFilter == null) {
                actualFilter = new ThreadFilter(fb);
            }
            else {
                actualFilter = new ThreadFilter(fb, previousFilter);
            }
            filters.add(actualFilter);
            previousFilter = actualFilter;
        }
        return filters;
    }

    public static void starFilters() {
 /*       for(ThreadFilter tf : Filters){
            tf.start();
        }*/
        Filters.forEach(ThreadFilter::start);
    }
}

package PipesAndFilters.Generic.Foundations;

/**
 * @author Palazuelos Alvarado Saul Alonso
 * @version 1.0
 * Generic Behaviour Class For Pipes and Filters
 * @param <R> please use a type from java.io namespace
 * @param <W> please use a type from java.io namespace
 */
public abstract class FilterBehaviour<R,W> implements IFilterBehaviour<R,W> {
    public R Reader;
    public W Writer;

    @Override
    public R getReader() {
        return Reader;
    }

    @Override
    public W getWriter() {
        return Writer;
    }

    public ThreadFilter<R,W> getFilter(){
        return new ThreadFilter<R,W>(this);
    }

}

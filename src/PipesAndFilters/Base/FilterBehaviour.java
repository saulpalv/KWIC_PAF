package PipesAndFilters.Base;

public abstract class FilterBehaviour implements IFilterBehaviour {
    public java.io.Writer Writer;
    public java.io.Reader Reader;

    @Override
    public java.io.Writer getWriter() {
        return Writer;
    }

    @Override
    public java.io.Reader getReader() {
        return Reader;
    }
}

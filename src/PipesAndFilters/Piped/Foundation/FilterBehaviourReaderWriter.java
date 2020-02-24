package PipesAndFilters.Piped.Foundation;

public abstract class FilterBehaviourReaderWriter implements IFilterBehaviourReaderWriter {
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

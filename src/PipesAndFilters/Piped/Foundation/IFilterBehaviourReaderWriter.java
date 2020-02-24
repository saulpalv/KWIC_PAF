package PipesAndFilters.Piped.Foundation;

public interface IFilterBehaviourReaderWriter extends Runnable {
    java.io.Writer getWriter();
    java.io.Reader getReader();
}

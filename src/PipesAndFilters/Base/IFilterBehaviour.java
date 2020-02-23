package PipesAndFilters.Base;

public interface IFilterBehaviour extends Runnable {
    java.io.Writer getWriter();
    java.io.Reader getReader();
}

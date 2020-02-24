package PipesAndFilters.Generic.Foundations;

public interface IFilterBehaviour<R,W> extends Runnable {
    R getReader();
    W getWriter();
}

package huedev.org.ui.base.activity;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}

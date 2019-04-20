package huedev.org.ui.base.fragment;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}

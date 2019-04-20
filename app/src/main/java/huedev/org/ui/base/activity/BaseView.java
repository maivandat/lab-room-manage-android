package huedev.org.ui.base.activity;

public interface BaseView {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showLoginError(Throwable throwable);
}

package huedev.org.ui.base;

public interface BaseView {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showLoginError(Throwable throwable);
}

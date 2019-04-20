package huedev.org.ui.base.fragment;

public interface BaseView {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showLoginError(Throwable throwable);
}

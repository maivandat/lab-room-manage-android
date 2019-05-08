package huedev.org.ui.base.activity;

import android.app.Dialog;

public interface BaseView {
    void showLoadingIndicator(Dialog dialog);

    void hideLoadingIndicator(Dialog dialog);

    void showLoginError(Throwable throwable);
}

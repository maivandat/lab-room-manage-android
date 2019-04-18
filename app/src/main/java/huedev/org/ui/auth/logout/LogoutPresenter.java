package huedev.org.ui.auth.logout;

import android.content.Context;

import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;

public class LogoutPresenter implements LogoutContact.Presenter {
    Context mContext;
    LogoutContact.View mView;

    public LogoutPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void logout() {
        AppPrefs.getInstance(mContext).putApiToken(AppConstants.API_TOKEN_DEFAULT);
        AppPrefs.getInstance(mContext).putIdUser(AppConstants.ID_USER_DEFAULT);
        AppPrefs.getInstance(mContext).putUserNameUser(AppConstants.USERNAME_DEFAULT);
        AppPrefs.getInstance(mContext).putPasswordUser(AppConstants.PASSWORD_DEFAULT);
        AppPrefs.getInstance(mContext).putNameUser(AppConstants.NAME_DEFAULT);
        AppPrefs.getInstance(mContext).putEmailUser(AppConstants.EMAIL_DEFAULT);
        AppPrefs.getInstance(mContext).putRole(AppConstants.ROLE_DEFAULT);
        mView.logout();
    }

    @Override
    public void setView(LogoutContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

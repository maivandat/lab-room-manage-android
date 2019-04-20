package huedev.org.ui.user.edit;

import android.content.Context;
import android.util.Log;

import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class UEditPresenter implements UEditContact.Presenter {

    private Context mContext;
    private UEditContact.View mView;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public UEditPresenter(Context Context, UserRepository UserRepository, BaseSchedulerProvider BaseSchedulerProvider) {
        this.mContext = Context;
        this.mUserRepository = UserRepository;
        this.mBaseSchedulerProvider = BaseSchedulerProvider;
    }

    @Override
    public void updateUser(String name, String email, String oldPassword, String newPassword, String cofirmNewPassword) {
        if (name.isEmpty() || email.isEmpty()){
            mView.logicFaild();
        }else {
            if (oldPassword.isEmpty() && newPassword.isEmpty() && cofirmNewPassword.isEmpty()){
                mView.showLoadingIndicator();
                String id = AppPrefs.getInstance(mContext).getIdUser();
                String username = AppPrefs.getInstance(mContext).getUserNameUser();
                String password = AppPrefs.getInstance(mContext).getPasswordUser();
                int role = AppPrefs.getInstance(mContext).getRole();
                mUserRepository.update(id, username, password, name, email, role)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(updateUserReponse -> handleUdpateUserSuccess(updateUserReponse)
                                , err -> handleUpdateUserFailed(err));
            }else if (oldPassword.isEmpty() || newPassword.isEmpty() || cofirmNewPassword.isEmpty()) {
                mView.logicFaild();
            } else {
                if (oldPassword.equals(AppPrefs.getInstance(mContext).getPasswordUser())){
                    if (newPassword.equals(cofirmNewPassword)) {
                        mView.showLoadingIndicator();
                        String id = AppPrefs.getInstance(mContext).getIdUser();
                        String username = AppPrefs.getInstance(mContext).getUserNameUser();
                        int role = AppPrefs.getInstance(mContext).getRole();
                        mUserRepository.update(id, username, cofirmNewPassword, name, email, role)
                                .subscribeOn(mBaseSchedulerProvider.io())
                                .observeOn(mBaseSchedulerProvider.ui())
                                .subscribe(updateUserReponse -> handleUdpateUserSuccess(updateUserReponse)
                                        , err -> handleUpdateUserFailed(err));
                    }else {
                        mView.newPasswordFail();
                    }
                }else {
                    mView.oldPasswordFail();
                }
            }
        }
    }

    public void handleUdpateUserSuccess(UpdateUserReponse updateUserReponse){
        mView.hideLoadingIndicator();
        mView.logicSuccess();
        mView.user(updateUserReponse.user);
    }

    public void handleUpdateUserFailed(Throwable err){
        mView.showLoginError(err);
    }

    @Override
    public void setView(UEditContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

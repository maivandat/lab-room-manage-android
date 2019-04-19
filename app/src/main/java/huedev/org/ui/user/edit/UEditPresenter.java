package huedev.org.ui.user.edit;

import android.content.Context;

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
    public void updateUser(String name, String email) {
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
    }

    @Override
    public void updateUser(String oldPassword, String newPassword, String cofirmNewPassword) {
        if (oldPassword.isEmpty() || newPassword.isEmpty() || cofirmNewPassword.isEmpty()){
            mView.logicFaild();
        }else {
            if (oldPassword.equals(AppPrefs.getInstance(mContext).getPasswordUser()) && newPassword.equals(cofirmNewPassword)){
                mView.showLoadingIndicator();
                String id = AppPrefs.getInstance(mContext).getIdUser();
                String username = AppPrefs.getInstance(mContext).getUserNameUser();
                String name = AppPrefs.getInstance(mContext).getNameUser();
                String email = AppPrefs.getInstance(mContext).getEmailUser();
                int role = AppPrefs.getInstance(mContext).getRole();
                mUserRepository.update(id, username, cofirmNewPassword, name, email, role)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(updateUserReponse -> handleUdpateUserSuccess(updateUserReponse)
                                , err -> handleUpdateUserFailed(err));
            }

        }

    }

    public void handleUdpateUserSuccess(UpdateUserReponse updateUserReponse){
        mView.hideLoadingIndicator();
        mView.logicSuccess();
        mView.user(updateUserReponse.user);
    }

    public void handleUpdateUserFailed(Throwable err){

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

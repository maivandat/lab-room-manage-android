package huedev.org.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class UserPresenter implements UserContact.Presenter {
    Context mContext;
    UserRepository mUserRepository;
    UserContact.View mView;
    BaseSchedulerProvider mBaseSchedulerProvider;

    public UserPresenter(Context context, UserRepository userRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.mContext = context;
        this.mUserRepository = userRepository;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
    }

    // show list user
    @Override
    public void users() {

        mUserRepository.users()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(listUserResponse -> handlerSuccessListUser(listUserResponse),
                        erro -> handlerFailListUser(erro));
    }

    private void handlerSuccessListUser(ListUserResponse listUserResponse) {

        mView.usersList(listUserResponse.userList);
    }

    private void handlerFailListUser(Throwable erro) {
        mView.showLoginError(erro);
    }
    //

    // update
    // update information user
    @Override
    public void updateUser(String name, String email, String oldPassword, String newPassword, String confirmNewPassword) {
        if (name.isEmpty() || email.isEmpty()){
            mView.logicFaild();
        }else {
            if (oldPassword.isEmpty() && newPassword.isEmpty() && confirmNewPassword.isEmpty()){

                String id = AppPrefs.getInstance(mContext).getIdUser();
                String username = AppPrefs.getInstance(mContext).getUserNameUser();
                String password = AppPrefs.getInstance(mContext).getPasswordUser();
                int role = AppPrefs.getInstance(mContext).getRole();
                mUserRepository.update(id, username, password, name, email, role)
                        .subscribeOn(mBaseSchedulerProvider.io())
                        .observeOn(mBaseSchedulerProvider.ui())
                        .subscribe(updateUserReponse -> handleUdpateUserSuccess(updateUserReponse)
                                , err -> handleUpdateUserFailed(err));
            }else if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                mView.logicFaild();
            } else {
                if (oldPassword.equals(AppPrefs.getInstance(mContext).getPasswordUser())){
                    if (newPassword.equals(confirmNewPassword)) {

                        String id = AppPrefs.getInstance(mContext).getIdUser();
                        String username = AppPrefs.getInstance(mContext).getUserNameUser();
                        int role = AppPrefs.getInstance(mContext).getRole();
                        mUserRepository.update(id, username, confirmNewPassword, name, email, role)
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

    // manager update user
    @Override
    public void updateUser(String id, String username, String password, String name, String email, int role, Dialog dialog) {
        if (name.isEmpty() || email.isEmpty()){
            mView.logicFaild();
        }else {
            mUserRepository.update(id, username, password, name, email, role)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(updateUserReponse -> handleUdpateUserSuccess(updateUserReponse, dialog)
                            , err -> handleUpdateUserFailed(err));
        }

    }

    private void handleUdpateUserSuccess(UpdateUserReponse updateUserReponse, Dialog dialog) {

        mView.user(updateUserReponse.user, dialog);
    }

    private void handleUpdateUserFailed(Throwable err) {
        mView.EditFaild(err);
    }

    private void handleUdpateUserSuccess(UpdateUserReponse updateUserReponse) {

        mView.logicSuccess();
        mView.user(updateUserReponse.user);
    }
    //
    //

    // delete user
    @Override
    public void deleteUser(String id, DialogInterface dialogInterface) {
        mUserRepository.delete(id)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(nope ->handleDelUserSuccess(dialogInterface)
                        , err -> handleDelUserFailed(err));
    }

    private void handleDelUserFailed(Throwable err) {
        mView.DelFaild(err);
    }

    private void handleDelUserSuccess(DialogInterface dialogInterface) {
        mView.delUserSuccess(dialogInterface);
    }
    //
    @Override
    public void setView(UserContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

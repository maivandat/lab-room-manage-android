package huedev.org.ui.activity.auth.register;

import android.app.Dialog;
import android.content.Context;

import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.remote.response.user.CreateUserReponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class RegisterPresenter implements RegisterContact.Presenter {
    private Context mContext;
    private RegisterContact.View mView;
    private UserRepository mUserRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public RegisterPresenter(Context Context, UserRepository UserRepository, BaseSchedulerProvider BaseSchedulerProvider) {
        this.mContext = Context;
        this.mUserRepository = UserRepository;
        this.mBaseSchedulerProvider = BaseSchedulerProvider;
    }

    @Override
    public void register(String content_type, String name, String username, String password, String cfPassword, String email) {

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || cfPassword.isEmpty() || email.isEmpty()){
            mView.logicFaild();
        }else {
            mUserRepository.userItem(content_type, name, username, password, cfPassword, email)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(createUserReponse -> handleCreateUserSuccess(createUserReponse),
                            err -> handleCreateUserFaild(err));
        }

    }

    @Override
    public void register(String name, String username, String password, String cfPassword, String email, int role, Dialog dialog) {

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || cfPassword.isEmpty() || email.isEmpty()){
            mView.logicFaild();
        }else {

            mUserRepository.userItem(name, username, password, cfPassword, email, role)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(createUserReponse -> handleCreateUserSuccess(createUserReponse, dialog),
                            err -> handleCreateUserFaild(err));
        }
    }

    private void handleCreateUserFaild(Throwable err) {
        mView.showLoginError(err);
    }

    private void handleCreateUserSuccess(CreateUserReponse createUserReponse, Dialog dialog) {
        mView.logicSuccess(createUserReponse.userItem, dialog);

    }
    private void handleCreateUserSuccess(CreateUserReponse createUserReponse) {
        mView.logicSuccess(createUserReponse.userItem);

    }

    @Override
    public void setView(RegisterContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

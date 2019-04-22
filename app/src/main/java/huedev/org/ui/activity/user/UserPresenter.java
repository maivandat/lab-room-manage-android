package huedev.org.ui.activity.user;

import android.content.Context;

import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.remote.response.user.ListUserResponse;
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


    @Override
    public void users() {
        mView.showLoadingIndicator();
        mUserRepository.users()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(listUserResponse -> handlerSuccessListUser(listUserResponse),
                        erro -> handlerFailListUser(erro));
    }

    private void handlerSuccessListUser(ListUserResponse listUserResponse) {
        mView.hideLoadingIndicator();
        mView.usersList(listUserResponse.userList);
    }

    private void handlerFailListUser(Throwable erro) {
        mView.showLoginError(erro);
    }

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

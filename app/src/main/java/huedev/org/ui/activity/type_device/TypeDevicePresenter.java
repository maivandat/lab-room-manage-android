package huedev.org.ui.activity.type_device;

import android.content.Context;

import huedev.org.data.repository.TypeDeviceRepository;
import huedev.org.data.source.remote.response.type_device.ListTDReponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class TypeDevicePresenter implements TypeDeviceContact.Presenter {
    private Context mContext;
    private TypeDeviceContact.View mView;
    private TypeDeviceRepository mTDRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public TypeDevicePresenter(Context Context, TypeDeviceRepository TDRepository, BaseSchedulerProvider BaseSchedulerProvider) {
        this.mContext = Context;
        this.mTDRepository = TDRepository;
        this.mBaseSchedulerProvider = BaseSchedulerProvider;
    }

    @Override
    public void listTD() {
        mTDRepository.listTD()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(listTDReponse -> handleListTDSuccess(listTDReponse),
                        err -> handleListTDFaild(err));
    }

    private void handleListTDSuccess(ListTDReponse listTDReponse) {
        mView.listTD(listTDReponse.listTD);
    }

    private void handleListTDFaild(Throwable err) {
        mView.showLoginError(err);
    }

    @Override
    public void setView(TypeDeviceContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

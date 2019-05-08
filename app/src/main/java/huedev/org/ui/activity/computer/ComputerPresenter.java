package huedev.org.ui.activity.computer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import huedev.org.data.source.remote.response.computer.UpdateCPTReponse;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class ComputerPresenter implements ComputerContract.Presenter {
    private Context mContext;
    private ComputerContract.View mView;
    private ComputerRepository mComputerRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private Navigator navigator;

    public ComputerPresenter(Context context,
                             ComputerRepository computerRepository,
                             BaseSchedulerProvider baseSchedulerProvider) {
        this.mContext = context;
        this.mComputerRepository = computerRepository;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
        this.navigator = new Navigator((Activity) mContext);
    }

    //Get List
    @Override
    public void computersByRoom() {

        mComputerRepository.computersByRoom()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(computerResponse -> handleComputerSuccess(computerResponse),
                        error -> handleComputerFailed(error));
    }

    @Override
    public void computersByRoomAll() {

        mComputerRepository.computersByRoom()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(computerResponse -> handleComputerAllSuccess(computerResponse),
                        error -> handleComputerAllFailed(error));
    }

    private void handleComputerAllFailed(Throwable error) {
        mView.showLoginError(error);
    }

    private void handleComputerAllSuccess(ListCPTResponse computerResponse) {
        mView.updateComputerListAll(computerResponse.computersByRoom);
    }

    private void handleComputerSuccess(ListCPTResponse computerResponse){

        List<Computer> computerList = new ArrayList<>();
        String id = navigator.getData().getString(AppConstants.ID_ROOM);
        for (Computer computer: computerResponse.computersByRoom) {
            if (computer.getRoomId().equals(id)){
                computerList.add(computer);
            }
        }
        mView.updateComputerList(computerList);
    }

    private void handleComputerFailed(Throwable error){
        mView.showLoginError(error);
    }

    //Create
    @Override
    public void createComputer(String name, String desc, int status, int room_id) {
        if (name.isEmpty() || desc.isEmpty()){
            mView.logicCreateFaild();
        }else {

            mComputerRepository.computerItem(name, desc, status, room_id)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(createCPTReponse -> handleCreateComputerSuccess(createCPTReponse),
                            erro -> handleCreateComputerFaild(erro));
        }
    }

    private void handleCreateComputerFaild(Throwable erro) {
        mView.showLoginError(erro);
    }

    private void handleCreateComputerSuccess(CreateCPTReponse createCPTReponse) {
        mView.createSucess(createCPTReponse.computerItem);
    }

    //Update
    @Override
    public void updateComputer(int id, String name, String desc, int status, int room_id, Dialog dialog) {
        if (name.isEmpty() || desc.isEmpty()){
            mView.logicUpdateFaild(dialog);
        }else {

            mComputerRepository.computerItem(id, name, desc, status, room_id)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(updateCPTReponse -> handleUpdateComputerSuccess(updateCPTReponse, dialog),
                            erro -> handleUpdateComputerFaild(erro));
        }
    }

    private void handleUpdateComputerSuccess(UpdateCPTReponse updateCPTReponse, Dialog dialog) {
        mView.updateSuccess(dialog);
    }

    private void handleUpdateComputerFaild(Throwable err){
        mView.showLoginError(err);
    }
    //Delete
    @Override
    public void deleteComputer(int id, DialogInterface dialogInterface) {
        mComputerRepository.deleteItem(id)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(nope -> handleDelComputerSuccess(dialogInterface),
                        erro -> handleDelComputerFaild(erro));
    }

    private void handleDelComputerSuccess(DialogInterface dialogInterface){
        mView.delSuccess(dialogInterface);
    }

    private void handleDelComputerFaild(Throwable err){

    }

    @Override
    public void setView(ComputerContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}

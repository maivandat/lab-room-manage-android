package huedev.org.ui.fragments.room;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.adapter.RoomAdapter;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class RoomFragment extends BaseFagment implements RoomContact.View {
    RoomPresenter mPresenter;

    RecyclerView mRecyclerView;

    RoomAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        mRecyclerView = view.findViewById(R.id.rc_room);
        init();

        return view;
    }

    public void init(){
        RoomRepository roomRepository = RoomRepository.getInstance(RoomLocalDataSource.getInstance(),
                RoomRemoteDataSource.getInstance(getContext()));
        mPresenter = new RoomPresenter(getContext(), roomRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
        mPresenter.rooms();
    }

    @Override
    public void updateRoomsList(List<Room> roomList) {
        mAdapter = new RoomAdapter(getContext(), roomList, mPresenter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setOnClickListener(view -> {

        });
    }

    @Override
    public void updateRoomItem(Room room, Dialog dialog) {
        dialog.dismiss();
        mPresenter.rooms();
    }

    @Override
    public void createRoomItem(Room room) {

    }

    @Override
    public void delRoomSuccess(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        mPresenter.rooms();
    }

    @Override
    public void delRoomFaild(Throwable err) {
        Log.e("ErroDelRoom", err.toString());
    }

    @Override
    public void addRoomFaild(Throwable err) {

    }

    @Override
    public void updateRoomFaild(Throwable err) {

    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void logicFaild() {
        Toast.makeText(getContext(),
                StringHelper.getStringResourceByName(
                        "logic_faild", getContext()), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoadingIndicator(Dialog dialog) {
        dialog.show();
    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
    }
}

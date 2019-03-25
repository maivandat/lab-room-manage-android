package huedev.org.ui.fragments.room;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import huedev.org.utils.rx.SchedulerProvider;

public class RoomFragment extends Fragment implements RoomContract.View {
    RoomContract.Presenter mPresenter;

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
        mAdapter = new RoomAdapter(getContext(), roomList, () -> {

        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIndicator() {
        Toast.makeText(getContext(), "Xin chào", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingIndicator() {
        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.toString(), Toast.LENGTH_SHORT).show();
    }


}

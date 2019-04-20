package huedev.org.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.ui.activity.computer.ComputerActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context mContext;
    private List<Room> mRoomList;
    private Navigator navigator;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.mContext = context;
        this.mRoomList = roomList;
        navigator = new Navigator((Activity) mContext);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleItem, tvDescibeItem, tvViewComputer;
        Room rItem;
        public RoomViewHolder(View itemView) {
            super(itemView);

            tvTitleItem = itemView.findViewById(R.id.tv_titleItemRoom);
            tvDescibeItem = itemView.findViewById(R.id.tv_describeItemRoom);
            tvViewComputer = itemView.findViewById(R.id.tv_viewcomputer);

        }

        public void setData(Room room){
            this.rItem = room;

            tvTitleItem.setText(room.getName());
            tvDescibeItem.setText(room.getDesc());
        }

    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.setData(mRoomList.get(position));
        holder.tvViewComputer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.ID_ROOM, mRoomList.get(position).getId());
                navigator.startActivity(ComputerActivity.class, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

}

package huedev.org.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.ui.computer.ComputerActivity;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context mContext;
    private List<Room> mRoomList;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.mContext = context;
        this.mRoomList = roomList;
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
            tvDescibeItem.setText(room.getStatus());
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
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ComputerActivity.class);
                intent.putExtra("idRoom", mRoomList.get(position).getId());
                view.getContext().startActivity(intent);
//                Log.d("LoggId", mRoomList.get(position).getId() + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

}

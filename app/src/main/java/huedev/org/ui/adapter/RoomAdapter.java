package huedev.org.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Room;
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context mContext;
    private List<Room> mRoomList;
    private ItemListener mItemListener;

    public RoomAdapter(Context context, List<Room> roomList, ItemListener itemListener) {
        this.mContext = context;
        this.mRoomList = roomList;
        this.mItemListener = itemListener;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitleItem, tvDescibeItem;
        Room rItem;
        public RoomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvTitleItem = itemView.findViewById(R.id.tv_titleItemRoom);
            tvDescibeItem = itemView.findViewById(R.id.tv_describeItemRoom);
        }

        public void setData(Room room){
            this.rItem = room;

            tvTitleItem.setText(room.getName());
            tvDescibeItem.setText(room.getStatus());
        }

        @Override
        public void onClick(View view) {
            if (mItemListener != null){
                mItemListener.onItemClick();
            }
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
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

    public interface ItemListener {
        void onItemClick();
    }

}

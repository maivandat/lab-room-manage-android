package huedev.org.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Device;


public class DeviceAdapter extends RecyclerSwipeAdapter<DeviceAdapter.ViewHolder> {
    protected ArrayList<String> arrString;//chua cac chuoi status
    protected ArrayList<Integer> arrImg;//mang chua cac background drawable
    private Context context;
    private List<Device> deviceList;

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_device,viewGroup,false);
        addArray();

        return new ViewHolder(view);
    }

    private void addArray() {
        arrImg = new ArrayList<>();
        arrImg.add(R.drawable.custom_status_tag_green);
        arrImg.add(R.drawable.custom_status_tag_yellow);
        arrImg.add(R.drawable.custom_status_tag_red);
        arrString = new ArrayList<>();
        arrString.add("Working");
        arrString.add("Reparing");
        arrString.add("Crash");
    }

    public DeviceAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder viewHolder, int i) {
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.linearLayout);
        viewHolder.text_title.setText(deviceList.get(i).getName());
        viewHolder.text_content.setText(deviceList.get(i).getDesc());
        viewHolder.textStatus.setBackgroundResource(arrImg.get(Integer.parseInt(deviceList.get(i).getStatus())));
        viewHolder.textStatus.setText(arrString.get(Integer.parseInt(deviceList.get(i).getStatus())));//lay img trong mang dua theo status
        viewHolder.btn_edit.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_device_edit);
            dialog.setTitle("Edit");
            dialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_DeviceItem;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder{
        Button btn_edit, btn_del;
        LinearLayout linearLayout;
        SwipeLayout swipeLayout;
        TextView text_title,text_content;
        TextView textStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_edit = itemView.findViewById(R.id.btn_edit_device);
            btn_del = itemView.findViewById(R.id.btn_del_device);
            linearLayout = itemView.findViewById(R.id.linear_device_item_bottom);
            swipeLayout = itemView.findViewById(R.id.swipe_DeviceItem);
            textStatus = itemView.findViewById(R.id.text_status);
            text_title = itemView.findViewById(R.id.text_item_title);
            text_content = itemView.findViewById(R.id.text_item_content);
        }
    }
}


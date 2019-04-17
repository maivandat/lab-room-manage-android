package huedev.org.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Device;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    protected ArrayList<String> arrString;//chua cac chuoi status
    protected ArrayList<Integer> arrImg;//mang chua cac background drawable
    private Context context;
    private List<Device> deviceList;
    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.single_item_divice,viewGroup,false);
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
        viewHolder.text_title.setText(deviceList.get(i).getName());
        viewHolder.text_content.setText(deviceList.get(i).getDesc());
        viewHolder.textStatus.setBackgroundResource(arrImg.get(Integer.parseInt(deviceList.get(i).getStatus())));
        viewHolder.textStatus.setText(arrString.get(Integer.parseInt(deviceList.get(i).getStatus())));//lay img trong mang dua theo status
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
    protected class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title,text_content;
        TextView textStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textStatus = itemView.findViewById(R.id.text_status);
            text_title = itemView.findViewById(R.id.text_item_title);
            text_content = itemView.findViewById(R.id.text_item_content);
        }
    }
}


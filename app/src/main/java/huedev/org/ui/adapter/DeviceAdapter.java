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
import huedev.org.data.model.Device;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Context context;
    private List<Device> deviceList;
    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.single_item_divice,viewGroup,false);
        return new ViewHolder(view);
    }

    public DeviceAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder viewHolder, int i) {
        viewHolder.text_title.setText(deviceList.get(i).getName());
        viewHolder.text_content.setText(deviceList.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
    protected class ViewHolder extends RecyclerView.ViewHolder{

        TextView text_title,text_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_item_title);
            text_content = itemView.findViewById(R.id.text_item_content);
        }
    }
}


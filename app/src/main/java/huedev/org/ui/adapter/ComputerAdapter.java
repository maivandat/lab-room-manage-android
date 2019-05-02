package huedev.org.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.ui.activity.device.DeviceActivity;
import huedev.org.ui.activity.device.DeviceContact;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;


public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewholder>
         {
    private Context mContext;
    private List<Computer> mListComputer;
    private Navigator navigator;
    private List<Device> mListDevice;

    public ComputerAdapter(Context Context, List<Computer> ListComputer, List<Device> ListDevice) {
        this.mContext = Context;
        this.mListComputer = ListComputer;
        this.mListDevice = ListDevice;
        this.navigator = new Navigator((Activity) mContext);

    }

    public class ComputerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvDetail, tvChildDeviceCpu, tvChildDeviceHD, tvChildDeviceRam;
        ImageButton ibInsivilityDetailComputer;
        LinearLayout linearRoomDetail,linearRoomMain, linearChildDetailComputer;
        Computer cptItem;

        public ComputerViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nameComputer);
            tvDetail = itemView.findViewById(R.id.tv_describeItemComputer);
            ibInsivilityDetailComputer = itemView.findViewById(R.id.ib_insivilityDetailComputer);
            linearRoomDetail = itemView.findViewById(R.id.linear_detailcomputer);
            linearRoomMain = itemView.findViewById(R.id.linear_maincomputer);
            linearChildDetailComputer = itemView.findViewById(R.id.linear_childDetailComputer);
            tvChildDeviceCpu = itemView.findViewById(R.id.tv_cpuDevice);
            tvChildDeviceHD = itemView.findViewById(R.id.tv_driverDevice);
            tvChildDeviceRam = itemView.findViewById(R.id.tv_chipDevice);
            linearChildDetailComputer.setOnClickListener(this);
            tvDetail.setOnClickListener(this);
            ibInsivilityDetailComputer.setOnClickListener(this);
        }

        public void setData (Computer computer) {
            this.cptItem = computer;
            tvName.setText(computer.getName());
            Log.e("DEVICELIST", mListDevice.size() + "");
            if (mListDevice.size() > 0) {
                ArrayList<Device> devices = new ArrayList();
                for (Device device : mListDevice) {
                    if (device.getComputersId().equals(computer.getId())) {
                        devices.add(device);
                    }
                }
                if (devices.size() > 0){
                    tvChildDeviceCpu.setText("CPU: " + devices.get(0).getName());
                    tvChildDeviceHD.setText("HDD: " + devices.get(1).getName());
                    tvChildDeviceRam.setText("Ram: " + devices.get(2).getName());
                }
            }


        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.linear_childDetailComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_top_in, View.VISIBLE);
                    break;
                case R.id.tv_describeItemComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_top_in, View.VISIBLE);
                    break;
                case R.id.ib_insivilityDetailComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_bottom_out, View.INVISIBLE);
                    break;
            }
        }

        private void hideView(LinearLayout linearRoomDetail, Context context, int anim, int visibility) {
            Animation animation = AnimationUtils.loadAnimation(context, anim);
            linearRoomDetail.setVisibility(visibility);
            linearRoomDetail.startAnimation(animation);
        }

    }

    @NonNull
    @Override
    public ComputerAdapter.ComputerViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_computer, viewGroup, false);
        return new ComputerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerAdapter.ComputerViewholder myViewHolder, int i) {
            myViewHolder.setData(mListComputer.get(i));
            myViewHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getId());
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getName());
            navigator.startActivity(DeviceActivity.class, bundle);
        });
    }

//    private ArrayList<Device> getDeviceByCom(Computer computer) {
//        Log.d("IDCOMPUTER", computer.getId());
//        ArrayList<Device> devices = new ArrayList<>();
//        if (this.mListDevice.size() > 0){
//            for (Device device : this.mListDevice){
//                Log.d("IDCOMPUTER_DEVICE", device.getComputersId());
//                if (device.getComputersId().equals(computer.getId())){
//                    devices.add(device);
//                }
//            }
//        }
//        return devices;
//    }

    @Override
    public int getItemCount() {
        return mListComputer.size();
    }
}

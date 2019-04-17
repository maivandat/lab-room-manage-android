package huedev.org.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;
import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.ui.device.DeviceActivity;
import huedev.org.ui.device.DeviceConstract;
import huedev.org.ui.device.DevicePresenter;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewholder> implements DeviceConstract.View {
    DeviceConstract.Presenter presenter;
    private Context mContext;
    private List<Computer> mListComputer;
    Navigator navigator;
    List<Device> deviceList = new ArrayList<>();


    public ComputerAdapter(Context Context, List<Computer> ListComputer) {
        this.mContext = Context;
        this.mListComputer = ListComputer;
        navigator = new Navigator((Activity) mContext);
    }
    private void init() {
        DeviceRepository deviceRepository = DeviceRepository.getInstance(DeviceLocalDataSource.getInstance(),
                DeviceRemoteDataSource.getInstance(mContext));
        presenter = new DevicePresenter(mContext
                , deviceRepository
                , SchedulerProvider.getInstance()
        );
        presenter.setView(this);
        presenter.tempDevices();
    }

    @Override
    public void updateTempDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
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

        public void setData (Computer computer){
            this.cptItem = computer;
            tvName.setText(computer.getName());
        }
        public void setChildData(ArrayList<Device> devices){
            if (devices.size() > 2){
                Log.d("ListText", devices.get(0).getDesc());
                tvChildDeviceCpu.setText("CPU: " + devices.get(0).getDesc());
                tvChildDeviceHD.setText("HDD: " +devices.get(1).getDesc());
                tvChildDeviceRam.setText("Ram: " +devices.get(2).getDesc());
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
            init();
            myViewHolder.setData(mListComputer.get(i));
            myViewHolder.setChildData(getDeviceByCom(mListComputer.get(i)));
            myViewHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getId());
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getName());
            navigator.startActivity(DeviceActivity.class, bundle);
        });
    }

    private ArrayList<Device> getDeviceByCom(Computer computer) {

        ArrayList<Device> devices = new ArrayList<>();
        Log.d("ListSize2509", this.deviceList.size() + "");
        for (Device device : this.deviceList){
            if (device.getComputersId().equals(computer.getId())){
                devices.add(device);
            }
        }
        return devices;
    }

    @Override
    public int getItemCount() {
        return mListComputer.size();
    }

    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void hideLoadingIndicator() {
    }

    @Override
    public void showLoginError(Throwable throwable) {
    }
}

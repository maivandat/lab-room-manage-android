package huedev.org.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.model.Room;
import huedev.org.ui.activity.computer.ComputerPresenter;
import huedev.org.ui.activity.device.DeviceActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.helpers.ArrayHelper;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.WidgetHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;


public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewholder>
        implements NotifyHelper.ShowDialog {
    private Context mContext;
    private List<Computer> mListComputer;
    private List<Device> mListDevice;
    private List<Room> mListRoom;
    private List<Device> ListDeviceByCPT;
    private ComputerPresenter mComputerPresenter;
    private Navigator navigator;
    private int ROOM_ID;

    public ComputerAdapter(Context Context, List<Computer> ListComputer,
                           List<Device> ListDevice, List<Room> ListRoom,
                           ComputerPresenter computerPresenter) {
        this.mContext = Context;
        this.mListComputer = ListComputer;
        this.mListDevice = ListDevice;
        this.mListRoom = ListRoom;
        this.mComputerPresenter = computerPresenter;
        this.navigator = new Navigator((Activity) mContext);
        this.ListDeviceByCPT= new ArrayList();


    }

    @Override
    public void showDialogUpdate(Dialog dialog, int position) {
        dialog.setContentView(R.layout.fragment_add);
        RelativeLayout relativeFormEdit = dialog.findViewById(R.id.relative_container);
        EditText etTitle = dialog.findViewById(R.id.et_titleCreate);
        EditText etDesc = dialog.findViewById(R.id.et_descCreate);
        Spinner spinner = dialog.findViewById(R.id.spiner_type_first);
        Button btnUpdate = dialog.findViewById(R.id.btn_add);
        RadioButton rbActive = dialog.findViewById(R.id.rb_active);
        RadioButton rbRepair = dialog.findViewById(R.id.rb_repair);
        RadioButton rbBroken = dialog.findViewById(R.id.rb_broken);
        LinearLayout linearSecond = dialog.findViewById(R.id.linear_type_second);

        linearSecond.removeAllViews();
        etTitle.setHint(StringHelper.getStringResourceByName("computer_hint", mContext));
        btnUpdate.setText(StringHelper.getStringResourceByName("update", mContext));
        relativeFormEdit.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        etTitle.setText(mListComputer.get(position).getName());
        etDesc.setText(mListComputer.get(position).getDesc());
        Log.e("LISTROOM", mListRoom.size() + "");
        WidgetHelper.setupSpinner(spinner,
                ArrayHelper.getNameArrayR((ArrayList<Room>) mListRoom),
                mContext);
        dialog.show();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ROOM_ID = Integer.valueOf(mListRoom.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnUpdate.setOnClickListener(view -> {
            int id = Integer.parseInt(mListComputer.get(position).getId());
            String name = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            int status = 0;
            if (rbActive.isChecked()){
                status = 0;
            }if (rbRepair.isChecked()){
                status = 1;
            }if (rbBroken.isChecked()){
                status = 2;
            }

            mComputerPresenter.updateComputer(id, name, desc, status, ROOM_ID, dialog);

        });


    }

    @Override
    public void showAlerDialogDel(AlertDialog.Builder alertDialog, int position) {
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có muốn xóa máy tính " + mListComputer.get(position).getName() + " không ?");
        alertDialog.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mComputerPresenter.deleteComputer(Integer.parseInt(mListComputer.get(position).getId()), dialogInterface);
            }
        });

        alertDialog.show();
    }

    public class ComputerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvDetail, tvChildDeviceCpu, tvChildDeviceHD, tvChildDeviceRam;
        ImageButton ibInsivilityDetailComputer, ibEdit, ibDel;
        LinearLayout linearRoomDetail,linearRoomMain,
                linearChildDetailComputer, linearHandleComputer;
        Computer cptItem;

        public ComputerViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nameComputer);
            tvDetail = itemView.findViewById(R.id.tv_describeItemComputer);
            ibInsivilityDetailComputer = itemView.findViewById(R.id.ib_insivilityDetailComputer);
            ibEdit = itemView.findViewById(R.id.ib_editComputer);
            ibDel = itemView.findViewById(R.id.ib_delComputer);
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
                for (Device device : mListDevice) {
                    if (device.getComputersId().equals(computer.getId())) {
                        ListDeviceByCPT.add(device);
                    }
                }

                Log.e("DEVICELIST_CHILD", ListDeviceByCPT.size() + "");
                if (ListDeviceByCPT.size() > 0){
                    tvChildDeviceCpu.setText("CPU: " + ListDeviceByCPT.get(0).getName());
                    tvChildDeviceHD.setText("HDD: " + ListDeviceByCPT.get(1).getName());
                    tvChildDeviceRam.setText("Ram: " + ListDeviceByCPT.get(2).getName());
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
        View.OnClickListener onClickListener = view -> {
            switch (view.getId()){
                case R.id.ib_editComputer:
                    Dialog dialog = new Dialog(mContext);
                    showDialogUpdate(dialog, i);
                    break;
                case R.id.ib_delComputer:
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    showAlerDialogDel(alert, i);
                    break;
                default:
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getId());
                    bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getName());
                    navigator.startActivity(DeviceActivity.class, bundle);
                    break;
            }
        };
        myViewHolder.ibEdit.setOnClickListener(onClickListener);
        myViewHolder.ibDel.setOnClickListener(onClickListener);
        myViewHolder.itemView.setOnClickListener(onClickListener);
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

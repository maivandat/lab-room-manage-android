package huedev.org.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.model.TypeDevice;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.utils.helpers.ArrayHelper;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.WidgetHelper;
import huedev.org.utils.helpers.StringHelper;


public class DeviceAdapter extends RecyclerSwipeAdapter<DeviceAdapter.ViewHolder> implements NotifyHelper.ShowDialog {
    protected ArrayList<String> arrString;//chua cac chuoi status
    protected ArrayList<Integer> arrImg;//mang chua cac background drawable
    private Context mContext;
    private List<Device> mDeviceList;
    private List<TypeDevice> mTDList;
    private List<Computer> mCPTList;
    private DevicePresenter mDevicePresenter;

    public DeviceAdapter(Context context, List<Device> deviceList,
                         List<TypeDevice> TDList, List<Computer> CPTList,
                         DevicePresenter devicePresenter) {
        this.mContext = context;
        this.mDeviceList = deviceList;
        this.mTDList = TDList;
        this.mCPTList = CPTList;
        this.mDevicePresenter = devicePresenter;
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
    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_device,viewGroup,false);
        addArray();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder viewHolder, int i) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.linearLayout);

        viewHolder.text_title.setText(mDeviceList.get(i).getName());
        viewHolder.text_content.setText(mDeviceList.get(i).getDesc());
        viewHolder.textStatus.setBackgroundResource(arrImg.get(Integer.parseInt(mDeviceList.get(i).getStatus())));
        viewHolder.textStatus.setText(arrString.get(Integer.parseInt(mDeviceList.get(i).getStatus())));//lay img trong mang dua theo status


        View.OnClickListener onClickListener = view -> {
            switch (view.getId()){
                case R.id.btn_edit_device:
                    Dialog dialog = new Dialog(mContext);
                    showDialogUpdate(dialog, i);
                    break;
                case R.id.btn_del_device:
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    showAlerDialogDel(alert, i);
                    break;
            }

        };

        viewHolder.btn_edit.setOnClickListener(onClickListener);
        viewHolder.btn_del.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_DeviceItem;
    }

    @Override
    public void showDialogUpdate(Dialog dialog, int position) {
        dialog.setContentView(R.layout.fragment_add);
        RelativeLayout relativeContainer = dialog.findViewById(R.id.relative_container);
        relativeContainer.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        EditText etName = dialog.findViewById(R.id.et_titleCreate);
        EditText etDesc = dialog.findViewById(R.id.et_descCreate);
        TextView tvTD = dialog.findViewById(R.id.tv_tytpe_title_first);
        TextView tvCPT = dialog.findViewById(R.id.tv_tytpe_title_second);
        Spinner spinnerTD = dialog.findViewById(R.id.spiner_type_first);
        Spinner spinnerCPT = dialog.findViewById(R.id.spiner_type_second);
        RadioButton rbActive = dialog.findViewById(R.id.rb_active);
        RadioButton rbRepair = dialog.findViewById(R.id.rb_repair);
        RadioButton rbBroken = dialog.findViewById(R.id.rb_broken);
        Button btnUpdate = dialog.findViewById(R.id.btn_add);

        etName.setHint(StringHelper.getStringResourceByName("device_title", mContext));
        tvTD.setText(StringHelper.getStringResourceByName("type_device", mContext));
        tvCPT.setText(StringHelper.getStringResourceByName("computer_title", mContext));
        btnUpdate.setText(StringHelper.getStringResourceByName("update", mContext));

        WidgetHelper.setupSpinner(spinnerTD,
                ArrayHelper.getNameArrayTD((ArrayList<TypeDevice>) mTDList), mContext);
        WidgetHelper.setupSpinner(spinnerCPT,
                ArrayHelper.getNameArrayC((ArrayList<Computer>) mCPTList), mContext);
        btnUpdate.setOnClickListener(view -> {

        });
        dialog.show();
    }

    @Override
    public void showAlerDialogDel(AlertDialog.Builder alertDialog, int position) {
        alertDialog.setTitle("Thông Báo");
        alertDialog.setMessage(
                "Bạn có muốn xóa thiết bị " + mDeviceList.get(position).getName() + " không?");
        alertDialog.setPositiveButton("Không", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        alertDialog.setNegativeButton("Có", (dialogInterface, i) -> {
            mDevicePresenter.delDevice(Integer.parseInt(mDeviceList.get(i).getId()));
        });

        alertDialog.show();
    }

    private void addArray() {
        arrImg = new ArrayList<>();
        arrImg.add(R.drawable.custom_status_tag_green);
        arrImg.add(R.drawable.custom_status_tag_yellow);
        arrImg.add(R.drawable.custom_status_tag_red);
        arrString = new ArrayList<>();
        arrString.add(StringHelper.getStringResourceByName("active", mContext));
        arrString.add(StringHelper.getStringResourceByName("repair", mContext));
        arrString.add(StringHelper.getStringResourceByName("broken", mContext));
    }

}


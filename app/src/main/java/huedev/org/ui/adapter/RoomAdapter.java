package huedev.org.ui.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.activity.computer.ComputerActivity;
import huedev.org.ui.fragments.room.RoomContact;
import huedev.org.ui.fragments.room.RoomPresenter;
import huedev.org.utils.AppConstants;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>
        implements NotifyHelper.ShowDialog {
    private Context mContext;
    private List<Room> mRoomList;
    private Navigator navigator;
    private RoomPresenter mRoomPresenter;

    public RoomAdapter(Context context, List<Room> roomList, RoomPresenter roomPresenter) {
        this.mContext = context;
        this.mRoomList = roomList;
        this.mRoomPresenter = roomPresenter;
        navigator = new Navigator((Activity) mContext);


    }

    @Override
    public void showDialogUpdate(Dialog dialog, int position) {

        dialog.setContentView(R.layout.fragment_add);
        RelativeLayout relativeLayout = dialog.findViewById(R.id.relative_container);
        EditText etRoomTite = dialog.findViewById(R.id.et_titleCreate);
        EditText etRoomDesc = dialog.findViewById(R.id.et_descCreate);
        RadioButton rbRomOpen = dialog.findViewById(R.id.rb_active);
        RadioButton rbRomRepair = dialog.findViewById(R.id.rb_repair);
        RadioButton rbRomClose = dialog.findViewById(R.id.rb_broken);
        Button btnEdit = dialog.findViewById(R.id.btn_add);

        btnEdit.setText("Edit");
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
        etRoomTite.setText(mRoomList.get(position).getName());
        etRoomDesc.setText(mRoomList.get(position).getDesc());

        String id = mRoomList.get(position).getId();
        dialog.show();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "";
                if (rbRomOpen.isChecked()){
                    status = "Open";
                }if (rbRomRepair.isChecked()){
                    status = "Repair";
                }if (rbRomClose.isChecked()){
                    status = "Close";
                }
                String name = etRoomTite.getText().toString().trim();
                String desc = etRoomDesc.getText().toString().trim();
                mRoomPresenter.updateRoom(id, name, desc, status, dialog);
            }
        });
    }

    @Override
    public void showAlerDialogDel(AlertDialog.Builder alertDialog, int position) {
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có muốn xóa phòng " + mRoomList.get(position).getName() + " không ?");
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mRoomPresenter.deleteRoom(mRoomList.get(position).getId());
                dialogInterface.dismiss();
                mRoomPresenter.rooms();
            }
        });
        alertDialog.show();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleItem, tvDescibeItem, tvViewComputer, tvEdit, tvDel;
        View view;
        LinearLayout linearLayoutHandler;
        ImageButton ibMoreHandle, ibCollapseHandle;
        Room rItem;
        public RoomViewHolder(View itemView) {
            super(itemView);

            tvTitleItem = itemView.findViewById(R.id.tv_titleItemRoom);
            tvDescibeItem = itemView.findViewById(R.id.tv_describeItemRoom);
            tvViewComputer = itemView.findViewById(R.id.tv_viewcomputer);
            tvEdit = itemView.findViewById(R.id.tv_editRoom);
            tvDel = itemView.findViewById(R.id.tv_delRoom);
            linearLayoutHandler = itemView.findViewById(R.id.linear_handler_room);
            ibMoreHandle = itemView.findViewById(R.id.ib_moreHandleRoom);
            ibCollapseHandle = itemView.findViewById(R.id.ib_collapseHandleRoom);
            view = itemView.findViewById(R.id.v_status);
        }

        public void setData(Room room){
            this.rItem = room;

            tvTitleItem.setText(room.getName());
            tvDescibeItem.setText(room.getDesc());
            if (room.getStatus().equals("Open")){
                view.setBackgroundResource(R.drawable.green_status);
            }if (room.getStatus().equals("Repair")){
                view.setBackgroundResource(R.drawable.yellow_status);
            }if (room.getStatus().equals("Close")){
                view.setBackgroundResource(R.drawable.red_status);
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
        holder.linearLayoutHandler.setVisibility(View.INVISIBLE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.tv_viewcomputer:
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.ID_ROOM, mRoomList.get(position).getId());
                        navigator.startActivity(ComputerActivity.class, bundle);
                        break;
                    case R.id.tv_editRoom:
                        Dialog dialog = new Dialog(mContext);
                        showDialogUpdate(dialog, position);
                        break;
                    case R.id.tv_delRoom:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        showAlerDialogDel(alertDialog, position);
                        break;
                    case R.id.ib_moreHandleRoom:
                        holder.ibMoreHandle.setVisibility(View.INVISIBLE);
                        Animation animationMore = AnimationUtils.loadAnimation(mContext, R.anim.slide_right_in);
                        holder.linearLayoutHandler.setVisibility(View.VISIBLE);
                        holder.linearLayoutHandler.setAnimation(animationMore);
                        break;
                    case R.id.ib_collapseHandleRoom:
                        Animation animationCollapse = AnimationUtils.loadAnimation(mContext, R.anim.slide_right_out);
                        holder.linearLayoutHandler.setAnimation(animationCollapse);
                        holder.linearLayoutHandler.setVisibility(View.INVISIBLE);
                        holder.ibMoreHandle.setVisibility(View.VISIBLE);
                        break;
                    default:

                        break;
                }

            }
        };
        holder.tvViewComputer.setOnClickListener(onClickListener);
        holder.ibMoreHandle.setOnClickListener(onClickListener);
        holder.ibCollapseHandle.setOnClickListener(onClickListener);
        holder.tvEdit.setOnClickListener(onClickListener);
        holder.tvDel.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }


}

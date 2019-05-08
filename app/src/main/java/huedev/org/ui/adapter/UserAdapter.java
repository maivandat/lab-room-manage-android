package huedev.org.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.ui.activity.user.UserPresenter;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;

public class UserAdapter extends RecyclerSwipeAdapter<UserAdapter.UserViewHolder> implements NotifyHelper.ShowDialog {
    private Context mContext;
    private List<User> mUserList;
    private FloatingActionButton mFloatingActionButton;
    private UserPresenter mUserPresenter;

    public UserAdapter(Context context, List<User> roomList, FloatingActionButton floatingActionButton, UserPresenter userPresenter) {
        this.mContext = context;
        this.mUserList = roomList;
        this.mFloatingActionButton = floatingActionButton;
        this.mUserPresenter = userPresenter;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_UserItem;
    }

    @Override
    public void showDialogUpdate(Dialog dialog, int position) {
        dialog.setContentView(R.layout.dialog_edit_user);
        EditText etFullName = dialog.findViewById(R.id.et_nameEditUser);
        EditText etUsername = dialog.findViewById(R.id.et_usernameEditUser);
        EditText etEmail = dialog.findViewById(R.id.et_emailEditUser);
        RadioButton rbAmin = dialog.findViewById(R.id.rb_admin);
        RadioButton rbTechnicians = dialog.findViewById(R.id.rb_technicians);
        RadioButton rbMember = dialog.findViewById(R.id.rb_member);
        Button btnUpdate = dialog.findViewById(R.id.btn_editUser);

        etFullName.setText(mUserList.get(position).getName());
        etUsername.setText(mUserList.get(position).getUsername());
        etEmail.setText(mUserList.get(position).getEmail());
        if (mUserList.get(position).getRole().equals("0")){
            rbAmin.setChecked(true);
        }if (mUserList.get(position).getRole().equals("1")){
            rbTechnicians.setChecked(true);
        }if (mUserList.get(position).getRole().equals("2")){
            rbMember.setChecked(true);
        }
        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mUserList.get(position).getId();
                String name = etFullName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = mUserList.get(position).getPassword();
                int role = 2;
                if (rbAmin.isChecked()){
                    role = 0;
                }if (rbTechnicians.isChecked()){
                    role = 1;
                }if (rbMember.isChecked()){
                    role = 2;
                }

                mUserPresenter.updateUser(id, username, password, name, email, role, dialog);
            }
        });
    }

    @Override
    public void showAlerDialogDel(AlertDialog.Builder alertDialog, int position) {
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có muốn xóa user " + mUserList.get(position).getName() + " không ?");
        alertDialog.setPositiveButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.setNegativeButton("Có", (dialogInterface, i) -> mUserPresenter.deleteUser(mUserList.get(position).getId(), dialogInterface));

        alertDialog.show();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        User user;
        TextView tvName, tvEmail, tvRole;
        ImageButton ibEdit, ibDel;
        SwipeLayout swipeUserItem;
        LinearLayout linearLayout;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_itemNameUser);
            tvEmail = itemView.findViewById(R.id.tv_itemEmailUser);
            tvRole = itemView.findViewById(R.id.tv_itemRoleUser);
            swipeUserItem = itemView.findViewById(R.id.swipe_UserItem);
            linearLayout = itemView.findViewById(R.id.relative_handle_user);
            ibEdit = itemView.findViewById(R.id.ib_EditUser);
            ibDel = itemView.findViewById(R.id.ib_DelUser);
        }

        @SuppressLint("RestrictedApi")
        public void setData(User user){
            this.user = user;
            String sRole;

            swipeUserItem.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeUserItem.addDrag(SwipeLayout.DragEdge.Right, linearLayout);

            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            if (user.getRole().equals("0")){
                sRole = StringHelper.getStringResourceByName("admin", mContext);
            }else if (user.getRole().equals("1")){
                sRole = StringHelper.getStringResourceByName("technicians", mContext);
            }else {
                sRole = StringHelper.getStringResourceByName("member", mContext);
            }
            tvRole.setText(sRole);
        }

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.setData(mUserList.get(i));
        View.OnClickListener onClickListener = view -> {
            switch (view.getId()){
                case R.id.ib_EditUser:
                    AlertDialog.Builder alerDialog = new AlertDialog.Builder(mContext);
                    showAlerDialogDel(alerDialog, i);
                    break;
                case R.id.ib_DelUser:
                    Dialog dialog = new Dialog(mContext);
                    showDialogUpdate(dialog, i);
                    break;
            }
        };
        userViewHolder.ibEdit.setOnClickListener(onClickListener);
        userViewHolder.ibDel.setOnClickListener(onClickListener);
        userViewHolder.swipeUserItem.addSwipeListener(new SwipeLayout.SwipeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartOpen(SwipeLayout layout) {
                mFloatingActionButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStartClose(SwipeLayout layout) {
                mFloatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


}

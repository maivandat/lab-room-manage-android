package huedev.org.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<User> mUserList;

    public UserAdapter(Context context, List<User> roomList) {
        this.mContext = context;
        this.mUserList = roomList;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        User user;
        TextView tvName, tvEmail, tvRole;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_itemNameUser);
            tvEmail = itemView.findViewById(R.id.tv_itemEmailUser);
            tvRole = itemView.findViewById(R.id.tv_itemRoleUser);

        }

        public void setData(User user){
            this.user = user;
            String sRole;

            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            if (user.getRole().equals("0")){
                sRole = "Admin";
            }else if (user.getRole().equals("1")){
                sRole = "Techicians";
            }else {
                sRole = "Member";
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
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


}

package huedev.org.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import huedev.org.R;
import huedev.org.data.model.Work;
import huedev.org.data.source.local.io.SerializableFileFactory;
import huedev.org.utils.AppConstants;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<Work> listWork;

    public WorkAdapter(Context mContext, ArrayList<Work> listWork) {
        this.mContext = mContext;
        this.listWork = listWork;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvTime, tvName;
        private ImageButton ibDelWork;
        private LinearLayout linearHandler;
        private Work wItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nameWork);
            tvTime = itemView.findViewById(R.id.tv_timeWork);
            ibDelWork = itemView.findViewById(R.id.ib_delete);
            linearHandler = itemView.findViewById(R.id.linear_handler);
        }

        public void setData(Work work){
            this.wItem = work;

            tvName.setText(work.getName());
            tvTime.setText(work.getTime());

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_work, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.setData(listWork.get(i));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            int itemp;
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.ib_delete:
                            listWork.remove(i);
                            notifyDataSetChanged();
                            SerializableFileFactory.SaveFile(listWork, AppConstants.PATH, view.getContext());
                        break;
                    default:
                        if (itemp == 0){
                            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_right_in);
                            myViewHolder.linearHandler.setVisibility(View.VISIBLE);
                            myViewHolder.linearHandler.setAnimation(animation);
                            itemp = 1;
                        }else {
                            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_right_out);
                            myViewHolder.linearHandler.setAnimation(animation);
                            myViewHolder.linearHandler.setVisibility(View.INVISIBLE);
                            itemp = 0;
                        }
                        break;
                }
            }
        };
        myViewHolder.itemView.setOnClickListener(onClickListener);
        myViewHolder.ibDelWork.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return listWork.size();
    }


}

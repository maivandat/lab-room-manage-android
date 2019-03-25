package huedev.org.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;


public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewholder> {
    private Context mContext;
    private List<Computer> mListComputer;
    private ItemListener mItemListener;

    public ComputerAdapter(Context Context, List<Computer> ListComputer, ItemListener ItemListener) {
        this.mContext = Context;
        this.mListComputer = ListComputer;
        this.mItemListener = ItemListener;
    }

    public class ComputerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name, txt_describe;
        ImageButton ib_moreContent;
        Computer mItem;
        public ComputerViewholder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.tv_nameComputer);
            txt_describe = itemView.findViewById(R.id.tv_describeItemComputer);
            ib_moreContent = itemView.findViewById(R.id.ib_moreContentItemComputer);

            ib_moreContent.setOnClickListener(this::onClick);
        }

        public void setData (Computer item){
            this.mItem = item;
            txt_name.setText(item.getName());
            txt_describe.setText(item.getDesc());
        }

        @Override
        public void onClick(View view) {
            if (mItemListener != null){
                mItemListener.onItemClick();
            }
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
    }

    @Override
    public int getItemCount() {
        return mListComputer.size();
    }

    public interface ItemListener {
        void onItemClick();
    }
}
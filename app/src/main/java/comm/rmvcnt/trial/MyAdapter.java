package comm.rmvcnt.trial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int decider;
    private Context context;
    private ArrayList<String> values;

    private final int LINEAR = 0;
    private final int GRID = 1;
    OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClicked(int pos);
    }


    public MyAdapter( Context context,int decider, ArrayList<String> values,OnItemClickListener listener) {
        this.decider = decider;
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (decider == 0) {
            return LINEAR;
        } else if (decider == 1) {
            return GRID;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LINEAR: return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_linear, parent, false));
            case GRID: return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_grid, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
         if (holder instanceof LinearViewHolder){
             ((LinearViewHolder) holder).label.setText(values.get(position));
             ((LinearViewHolder) holder).parent.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onItemClicked(position);
                 }
             });
         }else if (holder instanceof GridViewHolder){
             ((GridViewHolder) holder).label.setText(values.get(position));
             ((GridViewHolder) holder).parent.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onItemClicked(position);
                 }
             });
         }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public void setDecider(int decider){
        this.decider = decider;
        notifyDataSetChanged();
    }


    public class LinearViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        View parent;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            parent = itemView.findViewById(R.id.parent);

        }
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        View parent;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            parent = itemView.findViewById(R.id.parent);

        }
    }

}


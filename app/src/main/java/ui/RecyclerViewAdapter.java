package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninjacode98.shoppinglist.R;

import java.util.List;

import model.ShoppingItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ShoppingItem> shoppingItems;

    public RecyclerViewAdapter(Context context, List<ShoppingItem> shoppingItems) {
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        ShoppingItem item = shoppingItems.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemSize.setText(item.getSize());
        holder.dateAdded.setText(item.getItemAddedDate());
        holder.itemQuantity.setText(item.getQuantity());
        holder.itemColor.setText(item.getItemColor());

    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemColor;
        public TextView itemQuantity;
        public TextView itemSize;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemColor = itemView.findViewById(R.id.item_color);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            dateAdded = itemView.findViewById(R.id.item_date);
            itemSize = itemView.findViewById(R.id.item_size);
            editButton = itemView.findViewById(R.id.editBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

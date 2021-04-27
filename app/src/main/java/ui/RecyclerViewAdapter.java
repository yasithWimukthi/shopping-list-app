package ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninjacode98.shoppinglist.R;

import java.text.MessageFormat;
import java.util.List;

import data.DatabaseHandler;
import model.ShoppingItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ShoppingItem> shoppingItems;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

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
        holder.itemName.setText(MessageFormat.format("Item Name : {0}", item.getItemName()));
        holder.itemSize.setText(MessageFormat.format("Item Size : {0}", Integer.toString(item.getSize())));
        holder.dateAdded.setText(MessageFormat.format("Date Added : {0}", item.getItemAddedDate()));
        holder.itemQuantity.setText(MessageFormat.format("Item Quantity : {0}", Integer.toString(item.getQuantity())));
        holder.itemColor.setText(MessageFormat.format("Item Color : {0}", item.getItemColor()));

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
        int position;

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
                    position = getAdapterPosition();
                    ShoppingItem item = shoppingItems.get(position);
                    deleteItem(item.getId());
                }
            });
        }

        public void deleteItem(int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View confirmationPopup = inflater.inflate(R.layout.confirm_popup,null);

            Button noBtn = confirmationPopup.findViewById(R.id.conf_no_btn);
            Button okBtn = confirmationPopup.findViewById(R.id.conf_ok_btn);

            builder.setView(confirmationPopup);
            dialog = builder.create();
            dialog.show();

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler dbHandler = new DatabaseHandler(context);
                    dbHandler.deleteItem(id);
                    shoppingItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });
        }
    }
}

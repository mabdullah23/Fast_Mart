package com.example.fastmart3.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastmart3.R;
import java.util.ArrayList;
import java.util.List;

public class SellerListDialog extends DialogFragment {
    
    private List<String> sellerIds;
    private List<String> sellerNames;
    private OnSellerSelectedListener listener;
    
    public interface OnSellerSelectedListener {
        void onSellerSelected(String sellerId, String sellerName);
    }
    
    public static SellerListDialog newInstance(ArrayList<String> sellerIds, ArrayList<String> sellerNames) {
        SellerListDialog dialog = new SellerListDialog();
        Bundle args = new Bundle();
        args.putStringArrayList("seller_ids", sellerIds);
        args.putStringArrayList("seller_names", sellerNames);
        dialog.setArguments(args);
        return dialog;
    }
    
    public void setOnSellerSelectedListener(OnSellerSelectedListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sellerIds = getArguments().getStringArrayList("seller_ids");
            sellerNames = getArguments().getStringArrayList("seller_names");
        }
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_seller_list, null);
        
        RecyclerView recyclerView = view.findViewById(R.id.recycler_sellers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        SellerAdapter adapter = new SellerAdapter(sellerIds, sellerNames, (sellerId, sellerName) -> {
            if (listener != null) {
                listener.onSellerSelected(sellerId, sellerName);
            }
            dismiss();
        });
        recyclerView.setAdapter(adapter);
        
        builder.setView(view)
               .setTitle("Chat with Seller")
               .setNegativeButton("Cancel", (dialog, which) -> dismiss());
        
        return builder.create();
    }
    
    static class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
        private List<String> sellerIds;
        private List<String> sellerNames;
        private OnSellerClickListener clickListener;
        
        interface OnSellerClickListener {
            void onClick(String sellerId, String sellerName);
        }
        
        SellerAdapter(List<String> ids, List<String> names, OnSellerClickListener listener) {
            this.sellerIds = ids;
            this.sellerNames = names;
            this.clickListener = listener;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvName.setText(sellerNames.get(position));
            holder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(sellerIds.get(position), sellerNames.get(position));
                }
            });
        }
        
        @Override
        public int getItemCount() { return sellerIds != null ? sellerIds.size() : 0; }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_seller_name);
            }
        }
    }
}
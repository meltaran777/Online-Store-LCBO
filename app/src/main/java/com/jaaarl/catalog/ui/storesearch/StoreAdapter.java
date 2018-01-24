package com.jaaarl.catalog.ui.storesearch;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaaarl.catalog.R;
import com.jaaarl.catalog.model.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bohdan on 28.10.2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private View view;
    private List<Store> data = new ArrayList<>();
    private StoreItemClickListener listener;


    public StoreAdapter(List<Store> data) {
        this.data = data;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, final int position) {
        final Store store = data.get(position);

        holder.name.setText(store.getName());

        String address = store.getAddressLine1();
        if (address == null)
            holder.address.setText(store.getCity());
        else holder.address.setText(address);

        holder.phone.setText(store.getTelephone());

        holder.cardView.setOnClickListener((v) -> {
            if (listener != null)
                listener.onStoreItemClick(store, position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setListener(StoreItemClickListener listener) {
        this.listener = listener;
    }


    static class StoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewName)   TextView name;
        @BindView(R.id.textViewAdress) TextView address;
        @BindView(R.id.textPhone)      TextView phone;
        @BindView(R.id.CardView)       CardView cardView;

        StoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


interface StoreItemClickListener {

    void onStoreItemClick(Store store, int position);
}
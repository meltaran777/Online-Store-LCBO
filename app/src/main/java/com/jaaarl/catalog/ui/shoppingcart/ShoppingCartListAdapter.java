package com.jaaarl.catalog.ui.shoppingcart;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaaarl.catalog.R;
import com.jaaarl.catalog.model.Product;
import com.jaaarl.catalog.model.ShoppingCartEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bohdan on 29.10.2017.
 */


public class ShoppingCartListAdapter extends RecyclerView.Adapter<ShoppingCartListAdapter.ShoppingCartViewHolder> {

    private View view;

    private List<ShoppingCartEntity> data;

    ShoppingCartItemListener listener;


    public ShoppingCartListAdapter(List<ShoppingCartEntity> items) {
        data = items;
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_card_list_item, parent, false);

        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartViewHolder holder, int position) {
        ShoppingCartEntity sh = data.get(position);
        Product product = sh.getProduct();

        holder.countView.setText(String.valueOf(sh.getProductCount()));

        if (product != null) {
            holder.name.setText(product.getName());

            holder.price.setText(String.format("%s$",
                    String.valueOf(Double.valueOf(product.getPriceInCents()) / 100)));

            Picasso.with(holder.image.getContext())
                    .load(product.getImageUrl())
                    .fit()
                    .centerInside()
                    .into(holder.image);

        }

        holder.cardView.setOnClickListener((v) -> {
            if (listener != null)
                listener.onShoppingCartItemClick(sh, position);
        });
        holder.image_menu.setOnClickListener((v) -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.shopping_cart_item_popup, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (listener != null)
                    listener.onDeleteAction(sh, holder.getAdapterPosition());
                return true;
            });
            popup.show();
        });
        holder.plus_product_btn.setOnClickListener((v) -> {
            if (listener != null)
                listener.onPlusProductClick(sh, position);
        });
        holder.minus_product_btn.setOnClickListener((v) -> {
            if (listener != null)
                listener.onMinusProductClick(sh, position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setListener(ShoppingCartItemListener listener) {
        this.listener = listener;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public interface ShoppingCartItemListener {

        void onShoppingCartItemClick(ShoppingCartEntity sh, int position);

        void onPlusProductClick(ShoppingCartEntity sh, int position);

        void onMinusProductClick(ShoppingCartEntity sh, int position);

        void onDeleteAction(ShoppingCartEntity sh, int position);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Holder
    ////////////////////////////////////////////////////////////////////////////////////////////////

    static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_text_view)  TextView  name;
        @BindView(R.id.price_text_view)         TextView  price;
        @BindView(R.id.item_count_text_view)    TextView  countView;
        @BindView(R.id.product_image_view)      ImageView image;
        @BindView(R.id.plus_image_view)         ImageView plus_product_btn;
        @BindView(R.id.minus_image_view)        ImageView minus_product_btn;
        @BindView(R.id.menu_image_view)         ImageView image_menu;
        @BindView(R.id.shopping_cart_container) CardView  cardView;

        ShoppingCartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

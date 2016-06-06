package com.tp.tppm3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> listProduct;
    private Context mContext;


    public ProductAdapter(Context context, List<Product> listProduct) {
        this.listProduct = listProduct;
        this.mContext = context;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, null);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ProductViewHolder customViewHolder, int i) {
        Product feedItem = listProduct.get(i);
        //Setting price and name
        customViewHolder.name.setText("Item name: " + feedItem.getName());
        customViewHolder.price.setText("Item price: R$"  + feedItem.getPrice() );

    }

    @Override
    public int getItemCount() {
        return (null != listProduct ? listProduct.size() : 0);
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder {
        protected TextView price;
        protected TextView name;

        public ProductViewHolder(View view) {
            super(view);
            this.price = (TextView) view.findViewById(R.id.price);
            this.name = (TextView) view.findViewById(R.id.name);
        }
    }

}
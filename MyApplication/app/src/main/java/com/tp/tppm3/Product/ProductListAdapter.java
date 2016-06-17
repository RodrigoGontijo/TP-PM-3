package com.tp.tppm3.Product;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tp.tppm3.Product.ProductList;
import com.tp.tppm3.R;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>
{
    private List<ProductList> listProductList;
    private Context mContext;

    public ProductListAdapter(Context context, List<ProductList> listProductListt){
        this.listProductList = listProductListt;
        this.mContext = context;
    }


    public ProductListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, null);
        ProductListViewHolder viewHolder = new ProductListViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(ProductListViewHolder customViewHolder, int i){
        ProductList feedItem = listProductList.get(i);
        customViewHolder.name.setText("Item name: " + feedItem.getName());
    }

    @Override
    public int getItemCount() {
        return (null != listProductList ? listProductList.size() : 0);
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public ProductListViewHolder(View view){
            super(view);
            this.name  = (TextView) view.findViewById(R.id.name);
        }


    }
}

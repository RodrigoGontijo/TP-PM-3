package com.tp.tppm3.List;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tp.tppm3.Activities.ProductListActivity;
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
        customViewHolder.name.setText("Lista: " + feedItem.getName());
    }

    @Override
    public int getItemCount() {
        return (null != listProductList ? listProductList.size() : 0);
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;

        public ProductListViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            this.name  = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public void onClick(View v) {
            listProductList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, ProductListActivity.class);
            intent.putExtra("List_ID", listProductList.get(getAdapterPosition()).getName());
            mContext.startActivity(intent);
        }



    }
}

package com.tp.tppm3.Product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tp.tppm3.Firebase.SingletonFirebase;
import com.tp.tppm3.R;
import com.tp.tppm3.User.UserLocalStore;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> listProduct;
    private Context mContext;
    private List<Product> listProductSend;
    private UserLocalStore listInformation;


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
        customViewHolder.name.setText("Name: " + feedItem.getName());
        customViewHolder.price.setText("Price: R$" + feedItem.getPrice());

    }

    @Override
    public int getItemCount() {
        return (null != listProduct ? listProduct.size() : 0);
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView price;
        protected TextView name;
        protected CheckBox checkBox;


        public ProductViewHolder(View view) {
            super(view);
            this.price = (TextView) view.findViewById(R.id.price);
            this.name = (TextView) view.findViewById(R.id.name);
            this.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        }


        @Override
        public void onClick(View v) {
            listInformation = new UserLocalStore(mContext);
            listInformation.setUserId(listInformation.getListId() + 1);
            int listid = listInformation.getListId();
            SingletonFirebase.getConnection().child("List").child("Items").child(listProduct.get(getAdapterPosition()).getName())
                    .setValue(listProduct.get(getAdapterPosition()).getName());
        }
    }

}
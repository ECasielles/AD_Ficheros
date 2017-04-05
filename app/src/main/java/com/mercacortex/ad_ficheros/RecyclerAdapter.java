package com.mercacortex.ad_ficheros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter {extends RecyclerView.Adapter<ProductAdapterRecycler.ProductViewHolder>{

    private List<Product> products;
    private Context context;
    private boolean ASC;

    public ProductAdapterRecycler(Context context){
        this.context = context;
        products = ((ProductApplication) context.getApplicationContext()).getProducts();
        ASC = false;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        // Poner parent, false es como poner parent a null y visualiza los xml
        // Toma el xml y lo introduce. Si no es null pone espacios en blanco
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(item);
    }
    @Override
    public void onBindViewHolder (ProductViewHolder holder, int position){
        holder.productImage.setImageResource(products.get(position).getmImage());
        holder.txvName.setText(products.get(position).getmName());
        holder.txvStock.setText(products.get(position).getFotmattedUnitsInStock());
        holder.txvPrice.setText(products.get(position).getFormatedPrice());
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    // This class just initialises View components
    // We want it static to have only one ViewHolder for every instance.
    // Also it's more rapidly initialised.
    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView txvName, txvStock, txvPrice;

        // VH does almost all that did the other adapter
        public ProductViewHolder(View item) {
            super(item);
            productImage = (ImageView) item.findViewById(R.id.imageView);
            txvName = (TextView) item.findViewById(R.id.txv_itemName);
            txvStock = (TextView) item.findViewById(R.id.txv_itemStock);
            txvPrice = (TextView) item.findViewById(R.id.txv_itemPrice);
        }
    }

    //public void getAllProducts(List<Product> productList) {
    public void getAllProducts() {
        //return products;
    }

    public void sortAlphabetically() {

        // Empties the list and reloads it
        products.clear();

        //products.addAll(productList);
        products.addAll(((ProductApplication) context.getApplicationContext()).getProducts(ASC));

        // Notifies all views whose adapter is this one (Observable-Observer Pattern)
        notifyDataSetChanged();

        // Sorting can be managed by either the DAO or the Adapter
        // We prefer loading the DAO
        ASC = !ASC;
        products.clear();
        // Reinitialize the product list from DAO
        products.addAll(((ProductApplication) context.getApplicationContext()).getProducts(ASC));
    }

}
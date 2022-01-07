package me.enzd.pasarkantan.productadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import me.enzd.pasarkantan.R;
import me.enzd.pasarkantan.apiclientproduct.Products;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHoder> {
    ArrayList<Products> listProduct;

    public ProductAdapter(ArrayList<Products> listProduct){
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHoder holder = new ViewHoder(inflater.inflate(R.layout.item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        Products products = listProduct.get(position);
        holder.txtNama.setText(products.getNama());
        holder.txtharga.setText("Rp. " + String.valueOf(products.getHarga()) + "/ 250gr");
        Glide.with(holder.itemView.getContext())
                .load("http:/10.0.2.2/product/img/" + listProduct.get(position).getGambar())
                .apply(new RequestOptions())
                .into(holder.imgProudct);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        public TextView txtNama, txtharga;
        public ImageView imgProudct;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNamaProduct);
            txtharga = itemView.findViewById(R.id.txtHargaProduct);
            imgProudct = itemView.findViewById(R.id.imgProduct);
        }
    }
}

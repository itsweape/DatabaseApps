package com.example.databaseapps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    private final ArrayList<Product> product;
    private static   DatabaseHelper mDatabaseHelpper;

    public ProductAdapter(Context context, int item_product, ArrayList<Product> product) {
        this.context = context;
        this.product = product;
        mDatabaseHelpper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //setting view recyclerview
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        //get position object dari class product
        final Product products = product.get(position);

        //set value tampilan
        holder.txtProduct.setText(products.getName());
        holder.txtPrice.setText(String.valueOf(products.getPrice()));

        //memanggil fungsi editData ketika produk di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData(products);
            }
        });

        //delete produk ketika produk di tekan lama
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //memanggil fungsi deleteProduct dari class DatabaseHelper
                mDatabaseHelpper.deleteProduct(products.getId());

                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                //refresh page
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
                return true;
            }
        });

        //delete produk ketika imagebutton hapus di klik
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //memanggil fungsi deleteProduct dari class DatabaseHelper
                mDatabaseHelpper.deleteProduct(products.getId());

                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                //refresh page
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct, txtPrice;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProduct = (TextView)itemView.findViewById(R.id.txtProduct);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);
        }
    }

    //edit data
    private void editData(final Product product){
        //set view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_form, null);

        final EditText produk = (EditText)view.findViewById(R.id.edtBarang);
        final EditText harga = (EditText)view.findViewById(R.id.edtHarga);

        if(product != null){
            produk.setText(product.getName());
            harga.setText(String.valueOf(product.getPrice()));
        }

        //alert digunakan untuk membuat form edit
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Product");
        builder.setView(view);
        builder.create();

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String productName = produk.getText().toString();
                final Integer price = Integer.valueOf(harga.getText().toString());

                if (TextUtils.isEmpty(productName)){
                    Toast.makeText(context, "Cek kembali data yang diinputkan!", Toast.LENGTH_SHORT).show();
                }else{
                    //memanggil fungsi updateProduct dari class DatabaseHelper
                    mDatabaseHelpper.updateProduct(new Product(product.getId(), productName, price));

                    Toast.makeText(context, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();

                    //refresh page
                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}

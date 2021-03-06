package ch.appbrew.wichtelapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Base64;

import ch.appbrew.wichtelapp.utils.ByteUtil;

public class MyWishListAdapter extends FirestoreRecyclerAdapter<MyWishListItem, MyWishListAdapter.WishHolder> {

    public MyWishListAdapter(@NonNull FirestoreRecyclerOptions<MyWishListItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WishHolder wishHolder, int i, @NonNull MyWishListItem myWishListItem) {
        String encodedString = myWishListItem.getProductImage();
        byte[] compressed = Base64.getDecoder().decode(encodedString);
        byte[] decompressed = ByteUtil.decompress(compressed);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decompressed, 0, decompressed.length);

        wishHolder.productImage.setImageBitmap(decodedImage);
        wishHolder.productName.setText(myWishListItem.getProductName());
        wishHolder.productDescription.setText(myWishListItem.getProductDescription());

    }

    @NonNull
    @Override
    public WishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywishlist_item,
                parent, false);
        return new WishHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class WishHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;
        private TextView productDescription;

        public WishHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
        }
    }
}

/*
public class MyWishListAdapter extends RecyclerView.Adapter<MyWishListAdapter.MyWishListViewHolder> {
    private ArrayList<MyWishListItem> myWishList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class MyWishListViewHolder extends RecyclerView.ViewHolder {
        public TextView productImage;
        public TextView productName;
        public TextView productDescription;
        //public ImageView mDeleteImage;
        public MyWishListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            //mDeleteImage = itemView.findViewById(R.id.image_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
    public MyWishListAdapter(ArrayList<MyWishListItem> exampleList) {
        myWishList = exampleList;
    }
    @Override
    public MyWishListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywishlist_item, parent, false);
        MyWishListViewHolder evh = new MyWishListViewHolder(v, mListener);
        return evh;
    }
    //Holds the WishList Item
    @Override
    public void onBindViewHolder(MyWishListViewHolder holder, int position) {
        MyWishListItem currentItem = myWishList.get(position);
        holder.productImage.setText(currentItem.getProductImage());
        holder.productName.setText(currentItem.getProductName());
        holder.productDescription.setText(currentItem.getProductDescription());
    }
    @Override
    public int getItemCount() {
        return myWishList.size();
    }



}*/

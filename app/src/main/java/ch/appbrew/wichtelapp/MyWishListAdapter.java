package ch.appbrew.wichtelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        public ImageView productImage;
        public TextView productName;
        public TextView productDescription;
        public ImageView mDeleteImage;
        public MyWishListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
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
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
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
    @Override
    public void onBindViewHolder(MyWishListViewHolder holder, int position) {
        MyWishListItem currentItem = myWishList.get(position);
        holder.productImage.setImageResource(currentItem.getProductImage());
        holder.productName.setText(currentItem.getProductName());
        holder.productDescription.setText(currentItem.getProductDescription());
    }
    @Override
    public int getItemCount() {
        return myWishList.size();
    }
}
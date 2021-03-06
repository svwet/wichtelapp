package ch.appbrew.wichtelapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class MyWishListAdapter extends FirestoreRecyclerAdapter<MyWishListItem, MyWishListAdapter.WishHolder> {

    public MyWishListAdapter(@NonNull FirestoreRecyclerOptions<MyWishListItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WishHolder wishHolder, int i, @NonNull MyWishListItem myWishListItem) {


        byte[] imageBytes = myWishListItem.getProductImage().getBytes();
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        YuvImage yuvimage=new YuvImage(imageBytes, ImageFormat.NV21, 100, 100, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, 100, 100), 80, baos);
        byte[] jdata = baos.toByteArray();

        // Convert to Bitmap
        Bitmap bmp = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);

        wishHolder.productImage.setImageBitmap(bmp);
        wishHolder.productName.setText(myWishListItem.getProductName());
        wishHolder.productDescription.setText(myWishListItem.getProductDescription());
    }

    @NonNull
    @Override
    public WishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywishlist_item,
                parent, false);
        return new WishHolder(v) ;
    }

    public class WishHolder extends RecyclerView.ViewHolder{

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

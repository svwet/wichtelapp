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
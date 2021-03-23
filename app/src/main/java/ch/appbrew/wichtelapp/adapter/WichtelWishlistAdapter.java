package ch.appbrew.wichtelapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import ch.appbrew.wichtelapp.R;
import ch.appbrew.wichtelapp.model.WichtelWishlist;

public class WichtelWishlistAdapter extends FirestoreRecyclerAdapter<WichtelWishlist, WichtelWishlistAdapter.WichtelHolderList> {

    public WichtelWishlistAdapter(@NonNull FirestoreRecyclerOptions<WichtelWishlist> options) {
        super(options);
    }

    @NonNull
    @Override
    public WichtelWishlistAdapter.WichtelHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_wichtel_wishlist,
                parent, false);
        return new WichtelHolderList(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @Override
    protected void onBindViewHolder(@NonNull WichtelHolderList wichtelHolderList, int i, @NonNull WichtelWishlist wichtelWishlist) {
        wichtelHolderList.wichtelName.setText(wichtelWishlist.getWichtelName());

    }

    public class WichtelHolderList extends RecyclerView.ViewHolder {
        private TextView wichtelName;
        public WichtelHolderList(@NonNull View itemView) {
            super(itemView);
            wichtelName = itemView.findViewById(R.id.wichtelName);
        }
    }
}

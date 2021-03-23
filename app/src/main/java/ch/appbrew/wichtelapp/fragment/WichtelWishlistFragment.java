package ch.appbrew.wichtelapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import ch.appbrew.wichtelapp.R;
import ch.appbrew.wichtelapp.adapter.GroupAdapter;
import ch.appbrew.wichtelapp.adapter.WichtelWishlistAdapter;
import ch.appbrew.wichtelapp.model.ModelGroup;
import ch.appbrew.wichtelapp.model.WichtelWishlist;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WichtelWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WichtelWishlistFragment extends Fragment {

    private RecyclerView wRecyclerView;
    private WichtelWishlistAdapter wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public WichtelWishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WichtelwunschlisteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WichtelWishlistFragment newInstance(String param1, String param2) {
        WichtelWishlistFragment fragment = new WichtelWishlistFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wichtelwunschliste, container, false);
        setUpRecyclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        wAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        wAdapter.stopListening();
    }

    private void setUpRecyclerView(View view) {
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();
        CollectionReference groupListRef = db.collection("Benutzer").document(email).collection("Wichtel");
        Query query = groupListRef;
        FirestoreRecyclerOptions<WichtelWishlist> options = new FirestoreRecyclerOptions.Builder<WichtelWishlist>()
                .setQuery(query, WichtelWishlist.class)
                .build();
        wAdapter = new WichtelWishlistAdapter(options);
        wRecyclerView = view.findViewById(R.id.recyclerViewWishlist);
        wLayoutManager = new LinearLayoutManager(getActivity());
        wRecyclerView.setLayoutManager(wLayoutManager);
        //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(gRecyclerView);
        wRecyclerView.setAdapter(wAdapter);
        wAdapter.notifyDataSetChanged();
    }

}
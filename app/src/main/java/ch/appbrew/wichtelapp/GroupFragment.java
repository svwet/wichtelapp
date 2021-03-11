package ch.appbrew.wichtelapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    private ArrayList<ModelGroup> modelGroups;
    private RecyclerView gRecyclerView;
    private GroupAdapter gAdapter;
    private RecyclerView.LayoutManager gLayoutManager;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GruppenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_gruppen,
                container, false);
        setUpRecyclerView(view);
        setButtons(view);
        return view;
    }

    public void setButtons(View view){
        FloatingActionButton btnAddGroup = getActivity().findViewById(R.id.btnAddGrp);


        view.findViewById(R.id.btnAddGrp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GroupFragment.this)
                        .navigate(R.id.action_fragment_gruppen_to_createGroupeFragment);
            }
        });

    }

    private void setUpRecyclerView(View view) {
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();

        CollectionReference groupListRef = db.collection("MeineWunschliste").document(email).collection("Liste");

        Query query = groupListRef;
        FirestoreRecyclerOptions<ModelGroup> options = new FirestoreRecyclerOptions.Builder<ModelGroup>()
                .setQuery(query, ModelGroup.class)
                .build();

        gAdapter = new GroupAdapter(options);
        gRecyclerView = view.findViewById(R.id.recyclerViewGroup);
        gRecyclerView.setHasFixedSize(true);
        gLayoutManager = new LinearLayoutManager(getActivity());
        gRecyclerView.setLayoutManager(gLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(gRecyclerView);
        gRecyclerView.setAdapter(gAdapter);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            gAdapter.deleteItem(viewHolder.getAdapterPosition());

        }
    };

}
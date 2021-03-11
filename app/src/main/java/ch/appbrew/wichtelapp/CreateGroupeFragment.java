package ch.appbrew.wichtelapp;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;





/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupeFragment extends Fragment {

    private ArrayList<ModelCreateGroupe> modelGroup;
    private RecyclerView mRecyclerView;
    private CreateGroupeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private EditText editGroupeName;
    private EditText editInvitePerson;


    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter fAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateGroupeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateGroupeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGroupeFragment newInstance(String param1, String param2) {
        CreateGroupeFragment fragment = new CreateGroupeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_groupe,
                container, false);

        setButtons(view);
        createGroupeList();
        setUpRecyclerView(view);


        return view;
    }

    private void setButtons(View view) {


        view.findViewById(R.id.btnInvitePerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInvitePerson(view);
            }
        });
        view.findViewById(R.id.btnCreateGrp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGrp();
            }
        });
        view.findViewById(R.id.btnRndWichtel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rndWichtel();
            }
        });


    }

    private void rndWichtel() {
        Toast.makeText(getActivity().getApplicationContext(), "Die Würfel sind gefallen!", Toast.LENGTH_LONG).show();
    }

    private void createGrp() {
        Toast.makeText(getActivity().getApplicationContext(), "Gruppe erstellt!", Toast.LENGTH_LONG).show();
    }

    //Check for existing Users!
    private void checkInvitePerson(View view) {
        editGroupeName = (EditText) getView().findViewById(R.id.editGroupeName);
        editInvitePerson = (EditText) getView().findViewById(R.id.editInvitePerson);

        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();

        db = FirebaseFirestore.getInstance();


        CollectionReference mailCheckRef = db.collection("Benutzer");
        Query query = mailCheckRef.whereEqualTo("Benutzer", editInvitePerson.getText().toString());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot d : task.getResult()) {
                        if (d.exists()) {
                            //addToGroupe();
                        }
                    }
                }
            }
        });


    }
    public void createGroupeList() {
        modelGroup = new ArrayList<>();

    }

    private void setUpRecyclerView(View view) {
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();

        //editGroupeName = (EditText) getView().findViewById(R.id.editGroupeName);

        CollectionReference groupeListRef = db.collection("Gruppen").document(email).collection("Test");

        Query query = groupeListRef;
        FirestoreRecyclerOptions<ModelCreateGroupe> options = new FirestoreRecyclerOptions.Builder<ModelCreateGroupe>()
                .setQuery(query, ModelCreateGroupe.class)
                .build();

        mAdapter = new CreateGroupeAdapter(options);

        mRecyclerView = view.findViewById(R.id.inviteRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.deleteItem(viewHolder.getAdapterPosition());

        }
    };

//    private void addToGroupe() {
//
//        //Gruppenname muss anfangs immer eingetragen werden
//        editGroupeName = (EditText) getView().findViewById(R.id.editGroupeName);
//
//        editInvitePerson = (EditText) getView().findViewById(R.id.editInvitePerson);
//
//        auth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//        auth.getCurrentUser();
//
//        final String email = auth.getCurrentUser().getEmail();
//
//        DocumentReference docRef = db.collection("Gruppen").document(email).collection(editGroupeName.getText().toString()).document();
//
//
//        ModelCreateGroupe pushUser = new ModelCreateGroupe(editInvitePerson.getText().toString(),"test");
//        docRef.set(pushUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getActivity().getApplicationContext(), "Freund hinzugefügt", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "Nicht hinzugefügt", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
}






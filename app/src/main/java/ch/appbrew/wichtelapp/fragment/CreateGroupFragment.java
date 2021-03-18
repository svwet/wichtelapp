package ch.appbrew.wichtelapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.Display;
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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

import ch.appbrew.wichtelapp.R;
import ch.appbrew.wichtelapp.adapter.CreateGroupAdapter;
import ch.appbrew.wichtelapp.model.ModelCreateGroup;
import ch.appbrew.wichtelapp.model.ModelGroup;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupFragment extends Fragment {

    private ArrayList<ModelCreateGroup> modelGroup;
    private RecyclerView mRecyclerView;
    private CreateGroupAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editGroupName;
    private EditText editInvitePerson;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter fAdapter;

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateGroupeFragment.
     */
    public static CreateGroupFragment newInstance(String param1, String param2) {
        CreateGroupFragment fragment = new CreateGroupFragment();
        Bundle args = new Bundle();
        ;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
    }

    private void rndWichtel() {
        Toast.makeText(getActivity().getApplicationContext(), "Die Würfel sind gefallen!", Toast.LENGTH_LONG).show();
    }

    private void createGrp() {
        Toast.makeText(getActivity().getApplicationContext(), "Gruppe erstellt!", Toast.LENGTH_LONG).show();
    }

    private void checkInvitePerson(View view) {
        editGroupName = (EditText) getView().findViewById(R.id.editGroupeName);
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
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot.getDocuments().size() > 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "Freund hinzugefügt", Toast.LENGTH_LONG).show();
                        addToGroupe();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Freund nicht registriert", Toast.LENGTH_LONG).show();
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
        String groupName = "group123456789!";
        if (getView() != null) {
            editGroupName = (EditText) getView().findViewById(R.id.editGroupeName);
            groupName = editGroupName.getText().toString();
        }
        CollectionReference groupListRef = db.collection("Gruppen").document(email).collection(groupName);
        Query query = groupListRef;
        FirestoreRecyclerOptions<ModelCreateGroup> options = new FirestoreRecyclerOptions.Builder<ModelCreateGroup>()
                .setQuery(query, ModelCreateGroup.class)
                .build();
        mAdapter = new CreateGroupAdapter(options);
        mRecyclerView = view.findViewById(R.id.inviteRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void updateAdapter() {
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();
        String groupName = "group123456789!";
        if (getView() != null) {
            editGroupName = (EditText) getView().findViewById(R.id.editGroupeName);
            groupName = editGroupName.getText().toString();
        }
        CollectionReference groupListRef = db.collection("Gruppen").document(email).collection(groupName);
        Query query = groupListRef;
        FirestoreRecyclerOptions<ModelCreateGroup> options = new FirestoreRecyclerOptions.Builder<ModelCreateGroup>()
                .setQuery(query, ModelCreateGroup.class)
                .build();
        mAdapter.stopListening();
        mAdapter = new CreateGroupAdapter(options);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
        mAdapter.notifyDataSetChanged();
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

    private void addToGroupe() {
        editGroupName = (EditText) getView().findViewById(R.id.editGroupeName);
        editInvitePerson = (EditText) getView().findViewById(R.id.editInvitePerson);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();
        DocumentReference docRef = db.collection("Gruppen").document(email).collection(editGroupName.getText().toString()).document(editInvitePerson.getText().toString());
        DocumentReference userRef = db.collection("Benutzer").document(editInvitePerson.getText().toString());
        DocumentReference userGroupRef = db.collection("Benutzer").document(editInvitePerson.getText().toString()).collection("Gruppen").document();
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String username = (String) document.getData().get("Name");
                        DocumentReference mailCheckRef = db.collection("Gruppen").document(email).collection(editGroupName.getText().toString()).document(editInvitePerson.getText().toString());
                        mailCheckRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (!document.exists()) {
                                        ModelCreateGroup pushUser = new ModelCreateGroup(editInvitePerson.getText().toString(), username);
                                        pushAdminToGroup(email, editGroupName.getText().toString());
                                        docRef.set(pushUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Freund hinzugefügt", Toast.LENGTH_LONG).show();
                                                    ModelGroup pushGroupToUser = new ModelGroup(editGroupName.getText().toString(), email);
                                                    userGroupRef.set(pushGroupToUser);
                                                    updateAdapter();
                                                } else {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Nicht hinzugefügt", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    } else {
                        //TODO:
                    }
                }
            }
        });
    }

    public void pushAdminToGroup(String admin, String group) {
        DocumentReference userRef = db.collection("Benutzer").document(admin).collection("Gruppen").document();
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if(!snapshot.exists()) {
                        ModelGroup pushGroupToAdmin = new ModelGroup(group, admin);
                        userRef.set(pushGroupToAdmin);
                        ModelCreateGroup pushAdmin = new ModelCreateGroup(admin, "Admin");
                        db.collection("Gruppen").document(admin).collection(editGroupName.getText().toString()).document(admin).set(pushAdmin);
                    }
                }
            }
        });
    }
}







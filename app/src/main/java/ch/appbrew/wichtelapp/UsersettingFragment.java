package ch.appbrew.wichtelapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ch.appbrew.wichtelapp.utils.DialogUtil;

import static android.content.ContentValues.TAG;


public class UsersettingFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;
    private FirebaseFirestore database;

    public UsersettingFragment() {
        // Required empty public constructor
    }

    public static UsersettingFragment newInstance(String param1, String param2) {
        UsersettingFragment fragment = new UsersettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_benutzereinstellung,
                container, false);
        Button button = (Button) view.findViewById(R.id.saveButton);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();

        DocumentReference docRef = database.collection("Benutzer").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final EditText nameText = (EditText) getView().findViewById(R.id.name);
                    final EditText emailText = (EditText) getView().findViewById(R.id.username);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.get("Name");
                        nameText.setText(document.get("Name").toString());
                        emailText.setText(email);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText oldPasswordEdit = (EditText) getView().findViewById(R.id.oldpassword);
                EditText newPasswordEdit = (EditText) getView().findViewById(R.id.newpassword);
                EditText newNameEdit = (EditText) getView().findViewById(R.id.name);

                String oldPasswordStr = oldPasswordEdit.getText().toString();
                String newPasswordStr = newPasswordEdit.getText().toString();
                String newNameStr = newNameEdit.getText().toString();

                if(!oldPasswordStr.isEmpty() && !newPasswordStr.isEmpty()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email, oldPasswordStr);

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPasswordStr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    DialogUtil.popupMessage("Settings has been updated", "Successful", getActivity());
                                                }
                                            }
                                        });
                                    } else {
                                       DialogUtil.popupMessage("Failed to update settings", "Failed", getActivity());
                                    }
                                }
                            });
                }
                Map<String, Object> map = new HashMap<>();
                map.put("Name", newNameStr);
                map.put("Benutzer", email);
                database.collection("Benutzer").document(email)
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Document added with ID " + email);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error while adding document");
                            }
                        });
            }
        });
        return view;
    }
}
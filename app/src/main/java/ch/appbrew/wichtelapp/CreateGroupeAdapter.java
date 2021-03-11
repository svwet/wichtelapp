package ch.appbrew.wichtelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class CreateGroupeAdapter extends FirestoreRecyclerAdapter<ModelCreateGroupe, CreateGroupeAdapter.GroupHolder> {

    public CreateGroupeAdapter(@NonNull FirestoreRecyclerOptions<ModelCreateGroupe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupHolder groupHolder, int i, @NonNull ModelCreateGroupe modelCreateGroupe) {

        groupHolder.benutzer.setText(modelCreateGroupe.getBenutzer());
        groupHolder.name.setText(modelCreateGroupe.getName());


    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_create_groupe,
                parent, false);
        return new GroupHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class GroupHolder extends RecyclerView.ViewHolder {

        private TextView benutzer;
        private TextView name;


        public GroupHolder(@NonNull View itemView) {
            super(itemView);

            benutzer = itemView.findViewById(R.id.createGroupeBenutzer);
            name = itemView.findViewById(R.id.createGroupeName);
        }
    }
}



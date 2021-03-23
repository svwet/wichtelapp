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
import ch.appbrew.wichtelapp.model.ModelCreateGroup;


public class CreateGroupAdapter extends FirestoreRecyclerAdapter<ModelCreateGroup, CreateGroupAdapter.GroupHolder> {

    public CreateGroupAdapter(@NonNull FirestoreRecyclerOptions<ModelCreateGroup> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupHolder groupHolder, int i, @NonNull ModelCreateGroup modelCreateGroupe) {
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
            name = itemView.findViewById(R.id.createGroupName);
        }
    }
}



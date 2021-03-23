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
import ch.appbrew.wichtelapp.model.ModelGroup;


public class GroupAdapter extends FirestoreRecyclerAdapter<ModelGroup, GroupAdapter.GroupHolderListe> {

    public GroupAdapter(@NonNull FirestoreRecyclerOptions<ModelGroup> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupHolderListe groupHolder, int i, @NonNull ModelGroup modelGroup) {
        groupHolder.groupName.setText(modelGroup.getGroupName());
    }

    @NonNull
    @Override
    public GroupHolderListe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_group,
                parent, false);
        return new GroupHolderListe(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class GroupHolderListe extends RecyclerView.ViewHolder {
        private TextView groupName;
        public GroupHolderListe(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
        }
    }
}



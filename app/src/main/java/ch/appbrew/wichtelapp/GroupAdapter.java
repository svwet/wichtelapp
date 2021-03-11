package ch.appbrew.wichtelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class GroupAdapter extends FirestoreRecyclerAdapter<ModelGroup, GroupAdapter.GroupHolder> {

    public GroupAdapter(@NonNull FirestoreRecyclerOptions<ModelGroup> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupHolder groupHolder, int i, @NonNull ModelGroup modelGroup) {

        groupHolder.name.setText(modelGroup.getName());


    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_group,
                parent, false);
        return new GroupHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public class GroupHolder extends RecyclerView.ViewHolder {

        private TextView name;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.groupName);
        }
    }
}



package ch.appbrew.wichtelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Activity class to reset password of passed user.
 *
 * @author svenwetter, marcokuenzler
 */
public class ResetActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    /**
     * Create a new instance of the mapped activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        final EditText editTextEmail = findViewById(R.id.username);
        final Button resetButton = findViewById(R.id.reset);
        final com.google.android.material.floatingactionbutton.FloatingActionButton goBackButton = findViewById(R.id.resetGoBack);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(editTextEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(v.getContext(), ResetSuccessActivity.class);
                                    startActivity(i);
                                    ResetActivity.this.finish();
                                }
                            }
                        });
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
                ResetActivity.this.finish();
            }
        });
    }
}

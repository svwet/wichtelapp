package ch.appbrew.wichtelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity class to display success message when instructions to user are sent.
 *
 * @author svenwetter, marcokuenzler
 */
public class ResetSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_sucess);
        final TextView successMessage = findViewById(R.id.resetSuccessText);
        final com.google.android.material.floatingactionbutton.FloatingActionButton goBackButton = findViewById(R.id.resetSuccessGoBack);
        successMessage.setText("Es wurde ein Link mit weiteren Anweisungen per E-Mail gesendet.");
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
                ResetSuccessActivity.this.finish();
            }
        });
    }
}


package ch.appbrew.wichtelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * SignupActivity class to register a new user in the firebase database.
 *
 * @author svenwetter, marcokuenzler
 */
public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    /**
     * Creates a new signup instance.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText editTextEmail = findViewById(R.id.username);
        final EditText editTextPassword = findViewById(R.id.password);
        final EditText editTextRepeatPassword = findViewById(R.id.repeatPassword);
        final EditText editTextName = findViewById(R.id.name);
        final Button signupButton = findViewById(R.id.signup);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        auth = FirebaseAuth.getInstance(); //need firebase authentication instance

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String repeatPassword = editTextRepeatPassword.getText().toString();
                final String name = editTextName.getText().toString();
                //checkDataEntered(email, password, repeatPassword, name);

                if(TextUtils.isEmpty(name)) {
                    editTextName.setError("Enter name!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Enter a password!");
                    return;
                }
                if (TextUtils.isEmpty(repeatPassword) || !repeatPassword.equals(password)) {
                    editTextRepeatPassword.setError("Password does not match!");
                    return;
                }
                if (!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Enter a valid email!");
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, getString(R.string.fui_error_unknown), Toast.LENGTH_LONG).show();
                                    loadingProgressBar.setVisibility(View.INVISIBLE);
                                    return;
                                } else {
                                    SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    SignupActivity.this.finish();
                                    Toast.makeText(getApplicationContext(), "New user registration", Toast.LENGTH_SHORT).show();;
                                }
                            }
                        });

            }
        });
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    private static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
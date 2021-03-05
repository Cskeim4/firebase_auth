package css.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();

        // ======== NORMAL LOGIN button ========
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // ======== CREATE LOGIN button ========
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // ======== GOOGLE LOGIN button ========
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS3334", "Google login ");
                googleSignIn();
            }
        });

        // ======== SIGN OUT button ========
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if(currentUser != null){
            //User is signed in
            Log.d("CIS3334","onAuthStateChanged:signed_in" + currentUser.getUid());
            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed In");
        }
        else{
            //User is signed out
            Log.d("CIS3334","onAuthStateChanged:signed_out" );
            Toast.makeText(MainActivity.this, "User Signed Out", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed Out");
        }

    private void createAccount(String email, String password) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("CIS3334", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                textViewStatus.setText("Signed In");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("CIS3334", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                textViewStatus.setText("Signed Out");
                            }

                            // ...
                        }
                    });
    }

    private void signIn(String email, String password){
        Log.d("CIS3334", "signIn with " + email);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("CIS3334", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                textViewStatus.setText("Signed In");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("CIS3334", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                textViewStatus.setText("Signed Out");
                                // ...
                            }

                            // ...
                        }
                    });
    }

    private void signOut () {
        mAuth.signOut();
    }

    private void googleSignIn() {

    }
}
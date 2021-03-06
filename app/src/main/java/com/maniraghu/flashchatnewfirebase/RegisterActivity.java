package com.maniraghu.flashchatnewfirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mUsernameView;
    private AutoCompleteTextView mGenderView;
    private AutoCompleteTextView mCompanymailidView;
    private AutoCompleteTextView mCompanyNameView;
    private AutoCompleteTextView mRegionView;
    private AutoCompleteTextView mDobView;
    private TextInputLayout textInputLayoutUser,textInputLayoutEmail,textInputLayoutGender,textInputLayoutCMail,textInputLayoutCName,textInputLayoutRegion,textInputLayoutDob,textInputLayoutPassword,textInputLayoutCPassword;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private InputValidation validate;

    // Firebase instance variables
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        validate=new InputValidation();
        textInputLayoutUser=(TextInputLayout) findViewById(R.id.tilname);
        textInputLayoutEmail=(TextInputLayout) findViewById(R.id.tilemail);
        textInputLayoutCName=(TextInputLayout) findViewById(R.id.tilcname);
        textInputLayoutCMail=(TextInputLayout) findViewById(R.id.tilcmail);
        textInputLayoutDob=(TextInputLayout) findViewById(R.id.tildob);
        textInputLayoutGender=(TextInputLayout) findViewById(R.id.tilgender);
        textInputLayoutRegion=(TextInputLayout) findViewById(R.id.tilregion);
        textInputLayoutPassword=(TextInputLayout) findViewById(R.id.tilpass);
        textInputLayoutCPassword=(TextInputLayout) findViewById(R.id.tilcpass);



        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.register_username);
        mGenderView = (AutoCompleteTextView) findViewById(R.id.register_gender);
        mCompanymailidView = (AutoCompleteTextView) findViewById(R.id.register_companymail);
        mCompanyNameView = (AutoCompleteTextView) findViewById(R.id.register_companyname);
        mRegionView = (AutoCompleteTextView) findViewById(R.id.register_region);
        mDobView = (AutoCompleteTextView) findViewById(R.id.register_dob);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        mAuth= FirebaseAuth.getInstance();


    }

    public void validateForm(){
        if(!(validate.validateUsername(mUsernameView.getText().toString(),textInputLayoutUser)
                &&validate.validateEmail(mEmailView.getText().toString(),textInputLayoutEmail)
                &&validate.validateName(mCompanyNameView.getText().toString(),textInputLayoutCName)
                &&validate.validateEmail(mCompanymailidView.getText().toString(),textInputLayoutCMail)
                &&validate.validateDOB(mDobView.getText().toString(),textInputLayoutDob)
                &&validate.validateGender(mGenderView.getText().toString(),textInputLayoutGender)
                &&validate.validatePassword(mPasswordView.getText().toString(),textInputLayoutPassword)
                &&validate.validateConfirmPassword(mConfirmPasswordView.getText().toString(),mPasswordView.getText().toString(),textInputLayoutCPassword))){
            return;
        }

    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        validateForm();
        attemptRegistration();
    }

    //Executed when Already Registered button is pressed
    public void alreadyRegistered(View v) {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        finish();
        startActivity(intent);

    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();
        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword=mConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length()>4;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser(){
        String email=mEmailView.getText().toString();
        String password=mPasswordView.getText().toString();
        final String gender=mGenderView.getText().toString();
        final String cname=mCompanyNameView.getText().toString();
        final String cmail=mCompanymailidView.getText().toString();
        final String cregion=mRegionView.getText().toString();
        final String dob=mDobView.getText().toString();
        final String username=mUsernameView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("FlashChat","createUser onComplete :"+ task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("FlashChat","user creation failed");
                    showErrorDialog("Registration attempt failed");
                }else{

                    User user=new User(gender,cname,cmail,cregion,dob,username);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(getApplicationContext(),"User registered successfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Unsuccesfull registration",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    saveDisplayName();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    // TODO: Save the display name to Shared Preferences

    private void saveDisplayName(){
        String displayName=mUsernameView.getText().toString();
        SharedPreferences prefs=getSharedPreferences(CHAT_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();

    }
    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}

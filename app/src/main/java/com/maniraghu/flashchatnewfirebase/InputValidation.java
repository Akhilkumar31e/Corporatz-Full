package com.maniraghu.flashchatnewfirebase;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;

public class InputValidation {
    public boolean validatePassword(String password, TextInputLayout textInputPassword){
        if(password.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        }
        else if(password.length()<6){
            textInputPassword.setError("Password should be atleast 6 characters");
            return false;
        }
        else {

            return true;
        }
    }

    public boolean validateName(String companyName,TextInputLayout textInputCompanyName){
        if(companyName.isEmpty()){
            textInputCompanyName.setError("Field can't be empty");
            return false;
        }
        return true;
    }
    public boolean validateConfirmPassword(String password,String confirmPassword,TextInputLayout textInputConfirmPassword){
        if(confirmPassword.isEmpty()){
            textInputConfirmPassword.setError("Enter same as password");
            return false;
        }
        else if(!confirmPassword.equals(password)){
            textInputConfirmPassword.setError("This is not same as password");
            return false;
        }
        else{
            textInputConfirmPassword.setError(null);
            return true;
        }
    }
    public boolean validateEmail(String emailInput,TextInputLayout textInputEmail ) {

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }
    public boolean validateUsername(String usernameInput,TextInputLayout textInputUsername ) {
        String namePattern="[a-zA-Z0-9 ]+";
        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        }else if(!usernameInput.matches(namePattern)){
            textInputUsername.setError("Name should contain only apla-numeric characters");
            return false;
        }
        else {
            textInputUsername.setError(null);
            return true;
        }
    }
    public boolean validateDOB(String dob,TextInputLayout dobView){
        String dobPattren="^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if(dob.isEmpty()){
            dobView.setError("Field can't be empty");
            return false;
        }
        if(!dob.matches(dobPattren)){
            //dobView.requestFocus();
            dobView.setError("Enter in this \"DD/MM/YYYY\" format ");
            return false;
        }
        else{
            dobView.setError(null);
        }
        return true;
    }
    public boolean validateGender(String gender,TextInputLayout genderView){
        String g1="male";
        String g2="female";
        String g3="other";
        if(gender.isEmpty()){
            genderView.setError("Field can't be empty");
            return false;
        }
        else if(!(gender.equalsIgnoreCase(g1)||gender.equalsIgnoreCase(g2)||gender.equalsIgnoreCase(g2))){
            genderView.setError("Please enter either MALE/FEMALE/OTHER");
            return false;
        }
        else{
            genderView.setError(null);
            return true;
        }
    }
    public boolean validatePhoneNum(String phoneNum,TextInputLayout phonenumView){
        String phPattern="(0/91)?[7-9][0-9]{9}";
        if(phoneNum.isEmpty()){
            phonenumView.setError("Please enter your phone number");
            return false;
        }
        else if(!phoneNum.matches(phPattern)){
            phonenumView.setError("Please enter a valid phone number");
            return false;
        }
        else{
            phonenumView.setError(null);
            return true;
        }
    }

}
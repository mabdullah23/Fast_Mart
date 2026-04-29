package com.example.fastmart3.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.fastmart3.models.User;

public class AuthRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface AuthCallback { void onSuccess(String userId); void onFailure(String errorMessage); }
    public interface LoginCallback { void onSuccess(String accountType, String userId, String userName); void onFailure(String errorMessage); }

    public void signUp(User user, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    user.setUserId(userId);
                    databaseReference.child("users").child(userId).setValue(user.toMap())
                        .addOnCompleteListener(dbTask -> {
                            if (dbTask.isSuccessful()) callback.onSuccess(userId);
                            else callback.onFailure("Failed to save user: " + dbTask.getException().getMessage());
                        });
                } else callback.onFailure("Signup failed: " + task.getException().getMessage());
            });
    }

    public void login(String email, String password, LoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    databaseReference.child("users").child(userId).get()
                        .addOnCompleteListener(dbTask -> {
                            if (dbTask.isSuccessful() && dbTask.getResult().exists()) {
                                User user = dbTask.getResult().getValue(User.class);
                                if (user != null) callback.onSuccess(user.getAccountType(), userId, user.getName());
                                else callback.onFailure("User data not found");
                            } else callback.onFailure("Failed to get user data");
                        });
                } else callback.onFailure("Login failed: " + task.getException().getMessage());
            });
    }

    public void logout() { firebaseAuth.signOut(); }
    public String getCurrentUserId() { return firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getUid() : null; }
    public boolean isLoggedIn() { return firebaseAuth.getCurrentUser() != null; }
}

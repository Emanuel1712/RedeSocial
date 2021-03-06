package com.projeto.projetoredesocial.common.model;

import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storages;
    private static HashMap<String, HashSet<Post>> posts;

    private static Database INSTANCE;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        //usersAuth.add(new UserAuth("user1@gmail.com","1234"));
        //usersAuth.add(new UserAuth("user2@gmail.com","12345"));
        //usersAuth.add(new UserAuth("user3@gmail.com","123456"));
        //usersAuth.add(new UserAuth("user4@gmail.com","1234567"));
        //usersAuth.add(new UserAuth("user5@gmail.com","12345678"));
        //usersAuth.add(new UserAuth("user6@gmail.com","123456789"));
    }

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth userAuth;


    public static Database getInstance(){
        if(INSTANCE == null)
           INSTANCE = new Database();
            INSTANCE.init();
        return INSTANCE;
    }

    public void init() {
        String email = "user1@gmail.com";
        String password = "1234";
        String name = "user1";

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(email);
        userAuth.setPassword(password);

        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUUID());

        users.add(user);
        this.userAuth = userAuth;
    }

    public <T> Database addOnSuccessListener(OnSuccessListener<T> listener){
        this.onSuccessListener = listener;
        return this;
    }
    public  Database addOnFailureListener(OnFailureListener listener){
        this.onFailureListener = listener;
        return this;
    }
    public Database addOnCompleteListener(OnCompleteListener listener){
        this.onCompleteListener = listener;
        return this;
    }

    public Database findPosts(String uuid) {
        timeout(() ->{
            HashMap<String, HashSet<Post>> posts = Database.posts;
            HashSet<Post> res = posts.get(uuid);

            if( res == null)
                res = new HashSet<>();

            if(onSuccessListener != null)
                onSuccessListener.onSuccess(new ArrayList<>(res));

            if(onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database findUser(String uuid) {
        timeout(() ->{
            Set<User> users = Database.users;
            User res = null;
            for(User user : users) {
                if(user.getUuid().equals(uuid)) {
                    res = user;
                    break;
                }
            }
                if(onSuccessListener != null && res != null) {
                    onSuccessListener.onSuccess(res);
                } else if(onFailureListener != null) {
                    onFailureListener.onFailure(new IllegalArgumentException("Usuario nao encontrado"));
                }
                if(onCompleteListener != null)
                    onCompleteListener.onComplete();

        });
        return this;
    }

    public Database addPhoto(String uuid, Uri uri){
        timeout(() ->{
            Set<User> users = Database.users;
            for (User user :users) {
                if(user.getUuid().equals(uuid)){
                    user.setUri(uri);
                }
            }
            storages.add(uri);
            onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database createUser(String name, String email, String password){
        timeout(() ->{
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setUuid(userAuth.getUUID());

            boolean added = users.add(user);
            if(added){
                this.userAuth = userAuth;
                if(onSuccessListener != null)
                onSuccessListener.onSuccess(userAuth);
            }else{
                this.userAuth = null;
                if(onFailureListener != null)
                onFailureListener.onFailure(new IllegalAccessException("Usuário já existe"));
            }
            if(onCompleteListener != null)
            onCompleteListener.onComplete();
        });
        return this;
    }

    public Database login(String email, String password) {
        timeout(()->{
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            if(usersAuth.contains(userAuth)){
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            }else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }
            onCompleteListener.onComplete();
        });

        return this;
    }

    public UserAuth getUser(){
        return userAuth;
    }

    private void timeout(Runnable r){
        new Handler().postDelayed(r, 2000);
    }

    public interface OnSuccessListener<T>{
        void onSuccess(T response);
    }
    public interface OnFailureListener<T>{
        void onFailure(Exception e);
    }
    public interface OnCompleteListener<T>{
        void onComplete();
    }
}

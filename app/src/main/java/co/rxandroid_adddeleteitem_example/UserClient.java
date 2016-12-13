package co.rxandroid_adddeleteitem_example;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horror on 12/13/16.
 */

public class UserClient {
    public static List<User> listUser= new ArrayList<>();
    private static UserClient instance;

    //create singleton instance of UserClient
    public static UserClient getInstance(){
        if(instance==null){
            instance= new UserClient();
        }
        return instance;
    }
    //add and get users
    public List<User> getUserList(@NonNull String name){
        User user= new User(name);
        listUser.add(user);
          return listUser;

    }
    // delete user
    public void deleteUser(int position){
        listUser.remove(position);
    }
}

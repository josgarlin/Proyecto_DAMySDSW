package com.dam.sga;

import com.dam.sga.LoginActivity;
import com.dam.sga.UserLib;

public class Functions_App {
    public static UserLib user ;

    public static UserLib getUser() {
        return user;
    }

    public  static void setUser(UserLib userr) {
        user = userr;
    }


}

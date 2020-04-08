package com.example.ingredieat.setting;

import java.util.HashMap;
import java.util.Map;

public class Setting {
    public static String PREF_NAME = "Preference";

    public static String givenName;
    public static String familyName;
    public static String googleId;
    public static String email;
    public static boolean ifSignIn;
    public static int count;
    public static boolean longClickFlag;
    public static boolean shortClickFlag;


    public final static class Strings {
        public final static String account_email = "account email";
        public final static String account_given_name = "account given name";
        public final static String account_family_name = "account family name";
        public final static String account_id = "account_id";
        public final static String if_signin_succ = "if_signin_succ";
    }
}

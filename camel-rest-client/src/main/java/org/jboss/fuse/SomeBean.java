package org.jboss.fuse;

import org.apache.camel.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SomeBean {

    static List<String> users;

    public SomeBean() {
        users = new ArrayList<String>();
        users.add("James Strachan");
        users.add("Claus Ibsen");
        users.add("Hiram Chirino");
        users.add("Jeff Bride");
        users.add("Chad Darby");
        users.add("Rachel Cassidy");
        users.add("Bernard Tison");
        users.add("Nandan Joshi");
        users.add("Rob Davies");
        users.add("Guillaume Nodet");
        users.add("Marc Little");
        users.add("Mario Fusco");
        users.add("James Hetfield");
        users.add("Kirk Hammett");
        users.add("Steve Perry");
    }

    private int counter;

    public static String getRandomUser() {
        //0-11
        int index = new Random().nextInt(users.size());
        return users.get(index);
    }

    public static String getUser(@Header("CamelHttpQuery") String user) {
        String result[] = user.split("=");
        return result[1];
    }

}

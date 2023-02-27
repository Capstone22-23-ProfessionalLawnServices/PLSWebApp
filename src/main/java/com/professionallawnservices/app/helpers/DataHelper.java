package com.professionallawnservices.app.helpers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("dataHelper")
public class DataHelper<T> {

    public ArrayList<T> combineArrayLists(ArrayList<T> list1, ArrayList<T> list2) {

        ArrayList<T> combinedList = new ArrayList<T>();

        if (list1 != null) {
            combinedList.addAll(list1);
        }

        if (list2 != null) {
            combinedList.addAll(list2);
        }

        return combinedList;

    }

}

package huedev.org.utils.helpers;

import java.util.ArrayList;

import huedev.org.data.model.Room;

public class ArrayHelper {
    public static ArrayList<String> getNameArray(ArrayList<Room> arrayList){
        ArrayList<String> returnArray = new ArrayList<>();
        for (Room room : arrayList){
            returnArray.add(room.getName());
        }
        return returnArray;
    }
}

package huedev.org.utils.helpers;

import java.util.ArrayList;

import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.model.Room;
import huedev.org.data.model.TypeDevice;

public class ArrayHelper {
    public static ArrayList<String> getNameArrayR(ArrayList<Room> arrayList){
        ArrayList<String> returnArray = new ArrayList<>();
        for (Room room : arrayList){
            returnArray.add(room.getName());
        }
        return returnArray;
    }

    public static ArrayList<String> getNameArrayC(ArrayList<Computer> arrayList){
        ArrayList<String> returnArray = new ArrayList<>();
        for (Computer computer : arrayList){
            returnArray.add(computer.getName());
        }
        return returnArray;
    }

    public static ArrayList<String> getNameArrayD(ArrayList<Device> arrayList){
        ArrayList<String> returnArray = new ArrayList<>();
        for (Device device : arrayList){
            returnArray.add(device.getName());
        }
        return returnArray;
    }

    public static ArrayList<String> getNameArrayTD(ArrayList<TypeDevice> arrayList){
        ArrayList<String> returnArray = new ArrayList<>();
        for (TypeDevice typeDevice : arrayList){
            returnArray.add(typeDevice.getName());
        }
        return returnArray;
    }
}

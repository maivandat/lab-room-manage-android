package huedev.org.utils.helpers;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;

public class WidgetHelper {
    public static void setupSpinner(Spinner spinner, ArrayList<String> list, Context context){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public static Dialog setupDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progressbar);
        return dialog;
    }
}

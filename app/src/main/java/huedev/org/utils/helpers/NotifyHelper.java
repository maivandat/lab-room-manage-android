package huedev.org.utils.helpers;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class NotifyHelper {
    public static void logicFaild(String notify, Context context){
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    public static void logicSuccess(String notify, Context context){
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    public interface ShowDialog {
        void showDialogUpdate(Dialog dialog, int position);
        void showAlerDialogDel(AlertDialog.Builder alertDialog, int position);
    }

}

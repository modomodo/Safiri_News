package logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Michael on 01/04/2015.
 */
public class L {
    public static void m(String message) {
        Log.d("Yo ", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}

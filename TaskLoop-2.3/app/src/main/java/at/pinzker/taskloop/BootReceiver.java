package at.pinzker.taskloop;
import android.content.*;
public class BootReceiver extends BroadcastReceiver {public void onReceive(Context c,Intent i){Store.log(c,"Zeitplanung wiederhergestellt: "+i.getAction());Scheduler.restore(c);TaskWidget.refresh(c);}}

package at.pinzker.taskloop;
import android.app.*;import android.appwidget.*;import android.content.*;import android.os.*;import android.widget.*;
public class TaskWidget extends AppWidgetProvider {
 public void onUpdate(Context c,AppWidgetManager m,int[] ids){for(int id:ids){RemoteViews v=new RemoteViews(c.getPackageName(),R.layout.widget);v.setOnClickPendingIntent(R.id.widget_voice,PendingIntent.getActivity(c,7001,new Intent(c,MainActivity.class).setAction("VOICE").putExtra("voice",true),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));v.setContentDescription(R.id.widget_voice,L.english(c)?"Speak a task":"Aufgabe sprechen");m.updateAppWidget(id,v);}}
 public void onAppWidgetOptionsChanged(Context c,AppWidgetManager m,int id,Bundle options){onUpdate(c,m,new int[]{id});}
 public static void refresh(Context c){AppWidgetManager m=AppWidgetManager.getInstance(c);int[] ids=m.getAppWidgetIds(new ComponentName(c,TaskWidget.class));if(ids.length>0)new TaskWidget().onUpdate(c,m,ids);}
}

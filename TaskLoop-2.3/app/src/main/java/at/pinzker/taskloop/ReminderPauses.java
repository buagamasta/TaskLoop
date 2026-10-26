package at.pinzker.taskloop;
import android.app.*;
import android.content.*;
import android.os.Build;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ReminderPauses {
 public static final int WORK=0,PRIVATE=1;
 private static final int RESUME_ID=2100301;
 private ReminderPauses(){}
 private static String prefix(int category){return category==PRIVATE?"private":"work";}
 private static String enabledKey(int category){return prefix(category)+"_reminders_enabled";}
 private static String untilKey(int category){return prefix(category)+"_reminders_until";}
 public static boolean isEnabled(Context c,int category){
  int cat=category==PRIVATE?PRIVATE:WORK;
  android.content.SharedPreferences p=Store.prefs(c);
  boolean enabled=p.getBoolean(enabledKey(cat),true);
  long until=p.getLong(untilKey(cat),0);
  if(!enabled&&until>0&&until<=System.currentTimeMillis()){
   p.edit().putBoolean(enabledKey(cat),true).putLong(untilKey(cat),0).apply();
   Store.log(c,L.english(c)?"Reminder category resumed: "+prefix(cat):"Erinnerung wieder aktiv: "+(cat==WORK?"Dienstlich":"Privat"));
   return true;
  }
  return enabled;
 }
 public static long until(Context c,int category){return Store.prefs(c).getLong(untilKey(category==PRIVATE?PRIVATE:WORK),0);}
 public static void set(Context c,int category,boolean enabled,long until){
  int cat=category==PRIVATE?PRIVATE:WORK;
  Store.prefs(c).edit().putBoolean(enabledKey(cat),enabled).putLong(untilKey(cat),enabled?0:until).apply();
  Store.log(c,L.english(c)?(enabled?"Reminder category enabled: ":"Reminder category paused: ")+prefix(cat):(enabled?"Erinnerungen aktiviert: ":"Erinnerungen pausiert: ")+(cat==WORK?"Dienstlich":"Privat"));
  Scheduler.restore(c);
 }
 public static void setAll(Context c,boolean enabled,long until){
  android.content.SharedPreferences.Editor e=Store.prefs(c).edit();
  for(int cat=WORK;cat<=PRIVATE;cat++)e.putBoolean(enabledKey(cat),enabled).putLong(untilKey(cat),enabled?0:until);
  e.apply();
  Store.log(c,L.english(c)?(enabled?"All reminders enabled":"All reminders paused"):(enabled?"Alle Erinnerungen aktiviert":"Alle Erinnerungen pausiert"));
  Scheduler.restore(c);
 }
 public static void expire(Context c){isEnabled(c,WORK);isEnabled(c,PRIVATE);}
 public static void scheduleResume(Context c){
  long now=System.currentTimeMillis(),next=Long.MAX_VALUE;
  for(int cat=WORK;cat<=PRIVATE;cat++)if(!Store.prefs(c).getBoolean(enabledKey(cat),true)){
   long until=Store.prefs(c).getLong(untilKey(cat),0);
   if(until>now&&until<next)next=until;
  }
  AlarmManager manager=c.getSystemService(AlarmManager.class);
  Intent i=new Intent(c,AlarmReceiver.class).setAction("RESUME_REMINDERS");
  PendingIntent pi=PendingIntent.getBroadcast(c,RESUME_ID,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
  if(next==Long.MAX_VALUE){manager.cancel(pi);return;}
  try{
   if(Build.VERSION.SDK_INT>=23)manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,next,pi);
   else manager.setExact(AlarmManager.RTC_WAKEUP,next,pi);
  }catch(SecurityException e){manager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,next,pi);}
 }
 public static String status(Context c,int category){
  int cat=category==PRIVATE?PRIVATE:WORK;
  if(isEnabled(c,cat))return L.tr(c,"Aktiv");
  long end=until(c,cat);
  if(end<0)return L.tr(c,"Bis auf Weiteres pausiert");
  if(end==0)return L.tr(c,"Pausiert");
  String date=new SimpleDateFormat("dd.MM.yyyy HH:mm",L.english(c)?Locale.UK:Locale.GERMAN).format(new Date(end));
  return L.tr(c,"Pausiert bis ")+date;
 }
}

package at.pinzker.taskloop;
import android.content.*;import java.util.*;import org.json.*;import java.text.SimpleDateFormat;
public final class Store {
 static android.content.SharedPreferences prefs(Context c){return c.getSharedPreferences("taskloop",0);}
 public static ArrayList<Task> all(Context c){ArrayList<Task> a=new ArrayList<>();try{JSONArray js=new JSONArray(prefs(c).getString("tasks","[]"));for(int i=0;i<js.length();i++)a.add(new Task(js.getJSONObject(i)));}catch(Exception e){log(c,"Lesefehler: "+e.getMessage());}return a;}
 public static synchronized void save(Context c,Task t){ArrayList<Task>a=all(c);boolean found=false;for(int i=0;i<a.size();i++)if(a.get(i).id==t.id){a.set(i,t);found=true;break;}if(!found)a.add(t);write(c,a);}
 static void write(Context c,ArrayList<Task>a){JSONArray j=new JSONArray();for(Task t:a)j.put(t.json());prefs(c).edit().putString("tasks",j.toString()).commit();TaskWidget.refresh(c);}
 public static synchronized int newId(Context c){int n=prefs(c).getInt("counter",100)+1;prefs(c).edit().putInt("counter",n).commit();return n;}
 public static Task get(Context c,int id){for(Task t:all(c))if(t.id==id)return t;return null;}
 public static synchronized void delete(Context c,int id){Scheduler.cancel(c,id);ArrayList<Task>a=all(c);for(int i=a.size()-1;i>=0;i--)if(a.get(i).id==id)a.remove(i);write(c,a);Scheduler.clearNotification(c,id);}
 public static synchronized void log(Context c,String line){String ts=new SimpleDateFormat("dd.MM. HH:mm:ss",Locale.GERMAN).format(new Date());String old=prefs(c).getString("log","");String value=ts+"  "+line+"\n"+old;if(value.length()>14000)value=value.substring(0,14000);prefs(c).edit().putString("log",value).commit();}
}

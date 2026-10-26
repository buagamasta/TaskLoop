package at.pinzker.taskloop;
public final class DraftDefaults {
 public static String title(String title,String raw,String note){String s=title==null?"":title.trim();if(s.isEmpty())s=raw==null?"":raw.trim();if(s.isEmpty())s=note==null?"":note.trim();if(s.isEmpty())return "Neue Aufgabe";return s.length()>180?s.substring(0,180).trim():s;}
 public static int number(String input,int fallback,int max){try{int n=Integer.parseInt(input.trim());return n>0&&n<=max?n:fallback;}catch(Exception e){return fallback;}}
}

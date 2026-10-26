package at.pinzker.taskloop;
import android.app.*;import android.content.*;import android.graphics.*;import android.graphics.drawable.*;import android.view.*;import android.widget.*;
public final class Design {
 public static final int INK=0xFF291A38,PAPER=0xFFFAF8FC,WHITE=0xFFFFFFFF,LAVENDER=0xFFB69AFF,CORAL=0xFFFF786C,TEXT=0xFF291A38,MUTED=0xFF756C80,PURPLE=0xFF291A38;
 public static int dp(Context c,float v){return (int)(c.getResources().getDisplayMetrics().density*v+.5f);}
 public static GradientDrawable bg(int color,float radius,Context c){GradientDrawable b=new GradientDrawable();b.setColor(color);b.setCornerRadius(dp(c,radius));return b;}
 public static GradientDrawable gradient(Context c){GradientDrawable b=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{0xFF201A37,0xFF372050,0xFF251B38});b.setCornerRadius(dp(c,28));return b;}
 public static TextView text(Context c,String value,int size,int color,boolean bold){TextView t=new TextView(c);t.setText(value);t.setTextSize(size);t.setTextColor(color);t.setFontFeatureSettings("kern");t.setTypeface(android.graphics.Typeface.create(bold?"sans-serif-medium":"sans-serif",0));t.setLineSpacing(dp(c,2),1);return t;}
 public static LinearLayout col(Context c){LinearLayout l=new LinearLayout(c);l.setOrientation(1);return l;}
 public static LinearLayout row(Context c){LinearLayout l=new LinearLayout(c);l.setOrientation(0);l.setGravity(Gravity.CENTER_VERTICAL);return l;}
 public static void pad(View v,int n){int p=dp(v.getContext(),n);v.setPadding(p,p,p,p);}
 public static void gap(LinearLayout l,int n){View v=new View(l.getContext());l.addView(v,new LinearLayout.LayoutParams(1,dp(l.getContext(),n)));}
 public static TextView button(Context c,String label,int color,int text,Runnable run){TextView b=Design.text(c,label,15,text,true);b.setGravity(Gravity.CENTER);b.setHeight(dp(c,52));b.setMaxLines(1);b.setEllipsize(android.text.TextUtils.TruncateAt.END);b.setTextSize(14);b.setPadding(dp(c,12),dp(c,12),dp(c,12),dp(c,12));b.setBackground(new RippleDrawable(android.content.res.ColorStateList.valueOf(0x227861CB),bg(color,18,c),null));b.setClickable(true);b.setFocusable(true);b.setOnClickListener(v->run.run());return b;}
 public static void weighted(LinearLayout row,View v){LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-2,1);lp.setMargins(dp(row.getContext(),4),0,dp(row.getContext(),4),0);row.addView(v,lp);}
 public static void mount(Activity a,View content){LinearLayout shell=col(a);shell.setBackgroundColor(INK);shell.addView(content,new LinearLayout.LayoutParams(-1,-1));shell.setOnApplyWindowInsetsListener((v,in)->{if(android.os.Build.VERSION.SDK_INT>=30){android.graphics.Insets bars=in.getInsets(WindowInsets.Type.systemBars()|WindowInsets.Type.displayCutout()|WindowInsets.Type.ime());v.setPadding(bars.left,bars.top,bars.right,bars.bottom);}else v.setPadding(in.getSystemWindowInsetLeft(),in.getSystemWindowInsetTop(),in.getSystemWindowInsetRight(),in.getSystemWindowInsetBottom());return in;});a.setContentView(shell);shell.requestApplyInsets();}
 public static class Orbit extends View {
  Paint p=new Paint(3);long start=android.os.SystemClock.uptimeMillis();boolean animate=true;
  public Orbit(Context c){super(c);setContentDescription("Sprachaufnahme starten");setFocusable(true);setClickable(true);}
  protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight(),x=w/2,y=h/2,r=Math.min(w,h)*.31f;float phase=(android.os.SystemClock.uptimeMillis()-start)/1600f;
   p.setStyle(Paint.Style.FILL);p.setShader(new RadialGradient(x,y,r*1.9f,new int[]{0x886F45B2,0x00372050},null,Shader.TileMode.CLAMP));c.drawCircle(x,y,r*1.9f,p);p.setShader(null);
   p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(Design.dp(getContext(),1));p.setColor(0x337A63B0);c.save();c.rotate(-25,x,y);c.drawOval(new RectF(x-r*1.65f,y-r*.75f,x+r*1.65f,y+r*.75f),p);c.rotate(65,x,y);c.drawOval(new RectF(x-r*1.45f,y-r*1.05f,x+r*1.45f,y+r*1.05f),p);c.restore();
   p.setStyle(Paint.Style.FILL);p.setShader(new LinearGradient(x-r,y-r,x+r,y+r,LAVENDER,CORAL,Shader.TileMode.CLAMP));c.drawCircle(x,y,r,p);p.setShader(null);p.setColor(INK);float u=r/5;p.setStrokeWidth(u*.8f);c.drawRoundRect(new RectF(x-u*.85f,y-u*2,x+u*.85f,y+u*.6f),u,u,p);p.setStyle(Paint.Style.STROKE);p.setStrokeCap(Paint.Cap.ROUND);c.drawArc(new RectF(x-u*1.55f,y-u*.9f,x+u*1.55f,y+u*1.4f),0,180,false,p);c.drawLine(x,y+u*1.4f,x,y+u*2.2f,p);c.drawLine(x-u*.7f,y+u*2.2f,x+u*.7f,y+u*2.2f,p);p.setStyle(Paint.Style.FILL);
   float angle=phase*.45f; p.setColor(CORAL);c.drawCircle(x+(float)Math.cos(angle)*r*1.65f,y+(float)Math.sin(angle)*r*.8f,Design.dp(getContext(),4),p);p.setColor(LAVENDER);c.drawCircle(x-(float)Math.cos(angle+.9f)*r*1.4f,y-(float)Math.sin(angle+.9f)*r,Design.dp(getContext(),3),p);if(animate&&isShown())postInvalidateDelayed(40);
  }
 }
}

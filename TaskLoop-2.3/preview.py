from PIL import Image,ImageDraw,ImageFont
from pathlib import Path
import math
S=2
W,H=1040,1060
im=Image.new('RGB',(W*S,H*S),'#F0ECF6');d=ImageDraw.Draw(im)
fontroot=Path('/usr/share/fonts/truetype/dejavu')
def font(n,b=False):return ImageFont.truetype(str(fontroot/('DejaVuSans-Bold.ttf' if b else 'DejaVuSans.ttf')),int(n*S))
def txt(x,y,t,n=16,c='#252139',b=False):d.text((x*S,y*S),t,font=font(n,b),fill=c,spacing=4*S)
def box(x,y,w,h,c,r=22):d.rounded_rectangle((x*S,y*S,(x+w)*S,(y+h)*S),radius=r*S,fill=c)
def line(points,c,width):d.line([(int(x*S),int(y*S)) for x,y in points],fill=c,width=int(width*S))
def circle(x,y,r,c):d.ellipse(((x-r)*S,(y-r)*S,(x+r)*S,(y+r)*S),fill=c)
def button(x,y,w,t,c,tc='#19152B'):
 box(x,y,w,52,c,17);f=font(14,True);length=d.textbbox((0,0),t,font=f)[2];d.text(((x+w/2)*S-length/2,(y+16)*S),t,font=f,fill=tc)
def phone(x,y):
 box(x-8,y-8,406,854,'#BEB6CC',40);box(x,y,390,838,'#19152B',34)
 txt(x+22,y+16,'09:00',12,'#FFFFFF',True);txt(x+292,y+16,'●  ▰',13,'#FFFFFF')
 return x,y+46
# heading
txt(52,30,'TaskLoop',36,'#19152B',True);txt(52,80,'Ein Gedanke. Schon festgehalten.',18,'#79738A')
x,y=phone(64,144)
box(x,y,390,790,'#F7F5FC',26)
txt(x+20,y+21,'TaskLoop',25,'#252139',True);button(x+250,y+13,119,'Einstellungen','#FFFFFF','#7861CB')
box(x+20,y+82,350,388,'#281C3D',26)
txt(x+42,y+103,'WENIGER IM KOPF. MEHR IM LEBEN.',9,'#C3B0FF',True)
txt(x+42,y+127,'Ein Gedanke.\nSchon festgehalten.',27,'#FFFFFF',True)
txt(x+42,y+203,'Sag es. TaskLoop erinnert dich.',13,'#C3B8D7')
cx,cy=x+195,y+302
for r in range(90,43,-1):
 alpha=(90-r)/46*.15;cc=tuple(int(a*(1-alpha)+b*alpha) for a,b in zip((40,28,61),(143,93,220)));circle(cx,cy,r,cc)
pts=[]
for n in range(101):
 a=n*math.pi*2/100;xx=85*math.cos(a);yy=32*math.sin(a);rot=-.4;pts.append((cx+xx*math.cos(rot)-yy*math.sin(rot),cy+xx*math.sin(rot)+yy*math.cos(rot)))
line(pts,'#655078',1)
pts=[]
for n in range(101):
 a=n*math.pi*2/100;xx=76*math.cos(a);yy=41*math.sin(a);rot=.8;pts.append((cx+xx*math.cos(rot)-yy*math.sin(rot),cy+xx*math.sin(rot)+yy*math.cos(rot)))
line(pts,'#655078',1)
# gradient circle
for off in range(-47*S,48*S):
 yy=off/S;wide=math.sqrt(max(0,47**2-yy**2));f=(yy+47)/94;col=tuple(int(a*(1-f)+b*f) for a,b in zip((195,176,255),(255,173,148)));d.line(((cx-wide)*S,cy*S+off,(cx+wide)*S,cy*S+off),fill=col,width=1)
box(cx-7,cy-19,14,29,'#19152B',7);d.arc(((cx-14)*S,(cy-11)*S,(cx+14)*S,(cy+16)*S),0,180,fill='#19152B',width=4*S);line([(cx,cy+16),(cx,cy+24)],'#19152B',4);line([(cx-7,cy+24),(cx+7,cy+24)],'#19152B',4);circle(cx+72,cy-27,4,'#FFAD94');circle(cx-50,cy-42,3,'#C3B0FF')
txt(x+124,y+355,'ANTIPPEN & SPRECHEN',10,'#FFFFFF',True);button(x+42,y+395,306,'+  Lieber schreiben','#413451','#FFFFFF')
txt(x+20,y+492,'Deine Aufgaben',21,'#252139',True);box(x+280,y+489,90,35,'#ECE5FA',14);txt(x+295,y+498,'3 aktiv',12,'#7861CB',True)

box(x+20,y+546,350,164,'#FFFFFF',23);txt(x+38,y+564,'GEPLANT',9,'#7861CB',True);txt(x+38,y+587,'Rechnung prüfen',19,'#252139',True);txt(x+38,y+618,'Mi, 07. Okt · 09:00',13,'#7861CB',True);txt(x+38,y+642,'Täglich · Erneut nach 15 Min.',11,'#79738A');button(x+38,y+669,144,'Bearbeiten','#F2EEF8');button(x+198,y+669,152,'Erledigt ✓','#EAE2FC','#7861CB')
box(x,y+718,390,72,'#FFFFFF',0)
for i,(symbol,label) in enumerate([('☷','Offen'),('✓','Erledigt'),('+','Neu'),('☰','Mehr')]):
 nx=x+8+i*94
 if i==0:box(nx,y+725,88,59,'#F0E9FC',18)
 txt(nx+34,y+730,symbol,22,'#7861CB' if i==0 else '#79738A')
 txt(nx+21,y+763,label,10,'#7861CB' if i==0 else '#79738A',i==0)
x,y=phone(586,144)
txt(x+28,y+43,'T A S K L O O P',12,'#C3B0FF',True)
txt(x+28,y+111,'09:00',50,'#FFFFFF',True);txt(x+28,y+186,'JETZT IST DEIN MOMENT',10,'#FFAD94',True)
txt(x+28,y+257,'Rechnung\nprüfen',34,'#FFFFFF',True)
txt(x+28,y+365,'Unterlagen für das neue Projekt\nprüfen und freigeben.',14,'#C9BED8')
txt(x+28,y+439,'Ohne Bestätigung erinnere ich dich\nnach 15 Minuten erneut.',13,'#C3B0FF')
button(x+28,y+547,334,'Erledigt ✓','#C3B0FF');button(x+28,y+614,334,'Später · 15 Minuten','#382D4B','#FFFFFF');txt(x+132,y+693,'Nur schliessen',13,'#A99BBD');txt(x+28,y+746,'Erledigt bestätigt diesen Termin.\nDie Serie bleibt aktiv.',11,'#A99BBD')
txt(64,1018,'Designvorschau · keine Geräteaufnahme',12,'#79738A')
im.save(Path(__file__).resolve().parent.parent/'TaskLoop_1.1_Designvorschau.png')

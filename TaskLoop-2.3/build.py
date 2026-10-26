#!/usr/bin/env python3
import os, subprocess, zipfile, shutil
from pathlib import Path
ROOT=Path(__file__).resolve().parent
SDK=Path(os.environ['ANDROID_SDK_ROOT'])
BT=SDK/'build-tools/35.0.0'; AJ=SDK/'platforms/android-35/android.jar'
ECJ=Path(os.environ['ECJ_JAR']) if os.environ.get('ECJ_JAR') else None
R8=BT/'lib/r8.jar'
if not R8.exists():R8=SDK/'cmdline-tools/latest/lib/r8.jar'
BUILD=ROOT/'build'; shutil.rmtree(BUILD,ignore_errors=True)
for name in ('gen','classes','dex'):(BUILD/name).mkdir(parents=True,exist_ok=True)
def run(*args):subprocess.run([str(a) for a in args],check=True,cwd=ROOT)
run(BT/'aapt','package','-f','-M',ROOT/'app/src/main/AndroidManifest.xml','-S',ROOT/'app/src/main/res','-I',AJ,'--version-code','13','--version-name','2.3','-F',BUILD/'base.apk','-J',BUILD/'gen')
sources=list((ROOT/'app/src/main/java').rglob('*.java'))+list((BUILD/'gen').rglob('*.java'))
if ECJ:run('java','-jar',ECJ,'-1.8','-nowarn','-encoding','UTF-8','-cp',AJ,'-d',BUILD/'classes',*sources)
else:run(os.environ.get('JAVAC','javac'),'--release','8','-encoding','UTF-8','-cp',AJ,'-d',BUILD/'classes',*sources)
with zipfile.ZipFile(BUILD/'classes.jar','w',zipfile.ZIP_DEFLATED) as z:
 for f in (BUILD/'classes').rglob('*.class'):z.write(f,f.relative_to(BUILD/'classes'))
run('java','-cp',R8,'com.android.tools.r8.D8','--lib',AJ,'--min-api','26','--output',BUILD/'dex',BUILD/'classes.jar')
shutil.copy(BUILD/'base.apk',BUILD/'unsigned.apk')
with zipfile.ZipFile(BUILD/'unsigned.apk','a',zipfile.ZIP_DEFLATED) as z:
 for f in (BUILD/'dex').glob('*.dex'):z.write(f,f.name)
run(BT/'zipalign','-f','-p','4',BUILD/'unsigned.apk',BUILD/'aligned.apk')
ks=ROOT/'.signing/release.jks'; password=ROOT/'.signing/password'
if not ks.exists() or not password.exists():raise SystemExit('Signierschlüssel oder Passwortdatei fehlt. Nicht automatisch einen neuen Schlüssel erzeugen.')
out=ROOT.parent/'TaskLoop-2.3.apk'
run(BT/'apksigner','sign','--ks',ks,'--ks-pass','file:'+str(password),'--ks-key-alias','taskloop','--out',out,BUILD/'aligned.apk')
run(BT/'apksigner','verify','--verbose',out)
print('APK:',out)

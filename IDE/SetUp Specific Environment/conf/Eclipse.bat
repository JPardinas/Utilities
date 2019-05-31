@echo off
call \setEnvironment.bat
echo Se va abrir el entorno de desarollo. Espere por favor....
%ECLIPSE_HOME%\eclipse.exe -nosplash -clean -refresh -data %ECLIPSE_WKSPACE% -vm \java\jdk1.8.0_101\bin\javaw -Xms256m -Xmx1024m -XX:PermSize=256m -Dosgi.locking=none
pause
exit
@echo on
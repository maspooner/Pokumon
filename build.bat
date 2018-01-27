mkdir .\bin
mkdir .\out
"C:\Program Files\Java\jdk1.8.0_131\bin\javac.exe" -d ./bin -cp src src/*.java
if %errorlevel% NEQ 0 (goto done)
"C:\Program Files\Java\jdk1.8.0_131\bin\jar.exe" cvfm ./out/Pokumon.jar ./build-files/game-manifest.MF -C bin . images music sounds
xcopy /i /y .\build-files\run.bat .\out\run.bat
:done
PAUSE
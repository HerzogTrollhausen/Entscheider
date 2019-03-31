set path="C:\Program Files\Java\jdk1.8.0_181\bin"
cd src
javac -d ..\meinOutput model\Starter.java
cd ..\meinOutput
jar cfm ..\Entscheider.jar ..\src\Manifest model\*.class view\*.class
REM cd ..
REM jar cfM MemesAgainstHumanity.zip MemesAgainstHumanity.jar
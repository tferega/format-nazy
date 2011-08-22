@echo off
setlocal

set JVM_PARAMS=-Xss4m -Xmx1g -Xms256m -XX:MaxPermSize=256m -Dsbt.boot.properties=project/strap/sbt.boot.properties
if exist "%JREBEL_HOME%\jrebel.jar" set JVM_PARAMS=%JVM_PARAMS% -noverify -javaagent:%JREBEL_HOME%\jrebel.jar -XX:+CMSClassUnloadingEnabled %JREBEL_LIFT_PLUGIN%

set STRAP=%~dp0project\strap\
set SBT_PATH="%STRAP%sbt-launch-0.10.1.jar"

set SBT_REPO=ftp://xsbt.org/sbt-launch-0.10.1.jar
set SBT_HASH=B41D79CB2D919A0E8732FB8EF4E71347CCEEA90A

set GRUJ_PATH="%STRAP%gruj.jar"
set GRUJ_PARAMS=-q -c%SBT_HASH% -d %SBT_REPO% %SBT_PATH%

java %JVM_PARAMS% -jar %GRUJ_PATH% %GRUJ_PARAMS% %*

#!/bin/bash
cd `dirname $0`

## START JVM PARAMS
JVM_PARAMS="-Xss2m -Xmx712m -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled"

TRY_JREBEL=true
LOG_LEVEL=
NO_PAUSE=false
DO_LOOP=false

while [ -n "$*" ]
do
  case "$1" in
    "--debug")
      echo "Setting debug mode"
      LOG_LEVEL="\"set logLevel:=Level.Debug\""
      ;;
    "--no-jrebel")
      echo "Disabling JRebel for faster compilation"
      TRY_JREBEL=false
      ;;
    "--loop")
      echo "Will run SBT in loop mode"
      DO_LOOP=true
      ;;
    "--no-pause")
      echo "Will not pause in loop mode"
      NO_PAUSE=true
      ;;
    *)
      SBT_PARAMS="$SBT_PARAMS \"$1\""
      ;;
  esac
  shift

done

JVM_PARAMS="$JVM_PARAMS"
if $TRY_JREBEL && [ -n "$JREBEL_HOME" ] && [ -f $JREBEL_HOME/jrebel.jar ]; then
  JVM_PARAMS="$JVM_PARAMS -noverify -javaagent:$JREBEL_HOME/jrebel.jar $JREBEL_PLUGINS"
fi

STRAP="project/strap"
SBT_PATH="$STRAP/sbt-launch-0.11.0.jar"
SBT_PARAMS="$LOG_LEVEL $SBT_PARAMS"

SBT_URL="http://typesafe.artifactoryonline.com/typesafe/ivy-releases/org.scala-tools.sbt/sbt-launch/0.11.0/sbt-launch.jar"
GRUJ_PATH="$STRAP/gruj.jar"
GRUJ_PARAMS="$GRUJ_PATH -c5B15BA0FC63E355D47293DEF4BC2E58DA6F03787 -d $SBT_URL $SBT_PATH $SBT_PARAMS"

RUN_CMD="java $JVM_PARAMS -jar $GRUJ_PARAMS"

LOOPING=true
while $LOOPING
do
  eval "$RUN_CMD"

  if ! $DO_LOOP ; then
    LOOPING=false
  else
    if ! $NO_PAUSE ; then
      echo "Press Enter to continue or Press CTRL+C to exit!"
      read
    fi
  fi
done

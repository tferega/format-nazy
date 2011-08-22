#!/bin/bash

JVM_PARAMS="-Xss4m -Xmx1g -Xms256m -XX:MaxPermSize=256m -Dsbt.boot.properties=project/strap/sbt.boot.properties"
if [ -f $JREBEL_HOME/jrebel.jar ]; then
  JVM_PARAMS="$JVM_PARAMS -noverify -javaagent:$JREBEL_HOME/jrebel.jar -XX:+CMSClassUnloadingEnabled $JREBEL_LIFT_PLUGIN"
fi

STRAP="project/strap"
SBT_PATH="$STRAP/sbt-launch-0.10.1.jar"

SBT_REPO="ftp://xsbt.org/sbt-launch-0.10.1.jar"
SBT_HASH="B41D79CB2D919A0E8732FB8EF4E71347CCEEA90A"

GRUJ_PATH="$STRAP/gruj.jar"
GRUJ_PARAMS="-q -c$SBT_HASH -d $SBT_REPO $SBT_PATH"

cd `dirname $0`
java $JVM_PARAMS -jar $GRUJ_PATH $GRUJ_PARAMS "$@"

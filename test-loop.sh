#!/bin/bash

echo Executing tests...
`dirname $0`/sbt.sh "project Core" ~test

echo
echo Testing finished, press any key to re-run...
read
$0

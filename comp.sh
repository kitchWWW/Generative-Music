#!/bin/bash
timestamp="$(date +"%s")"
echo "starting run script, with timestamp:"
echo $timestamp
mkdir out/$timestamp
find . -name "*.class" -type f -delete
javac runner.java
echo "Completed compile portion"

java runner $timestamp
echo "Completed run"
cd out/$timestamp

#/Applications/Lilypond.app/Contents/Resources/bin/lilypond I.ly
/Applications/Lilypond.app/Contents/Resources/bin/lilypond III.ly
open III.pdf
#open I.pdf
open III.midi
cd ..
cd ..
find . -name "*.class" -type f -delete


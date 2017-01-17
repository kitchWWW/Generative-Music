#!/bin/bash
timestamp="$(date +"%s")"
echo "starting compile script, with timestamp:"
echo $timestamp
mkdir out/$timestamp
find . -name "*.class" -type f -delete
javac runner.java
echo "Completed compile portion"

java runner 800 600 $timestamp 400 400 400 100 30 2400 100 20 150 250 250 1000 500 200 200 200 200 500
echo "Completed run"
cd out/$timestamp

/Applications/Lilypond.app/Contents/Resources/bin/lilypond -fpng -dresolution=310 melody.ly
convert melody.png -trim melody.png 1> _Image.log

open melody.png
open melody.midi
cd ..
cd ..
find . -name "*.class" -type f -delete


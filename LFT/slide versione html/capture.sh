#!/bin/bash

echo "Iteration 0..."
node js/capture.js http://localhost:4000/slide/$1.html\#/0 $1.0.png A5 landscape
echo "Iteration 1..."
node js/capture.js http://localhost:4000/slide/$1.html\#/1 $1.1.png A5 landscape
export PREV=0
export LAST=1
while ! diff -q $1.$PREV.png $1.$LAST.png; do
  export PREV=$LAST
  ((LAST++))
  echo "Iteration "$LAST"..."
  node js/capture.js http://localhost:4000/slide/$1.html\#/$LAST $1.$LAST.png A5 landscape
done
rm $1.$LAST.png

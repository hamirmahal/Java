#!/bin/sh
touch locks.java.output.txt

a=1
while [ $a -le 1000 ]
do
  java locks.java >> locks.java.output.txt
  a=`expr $a + 1`
done

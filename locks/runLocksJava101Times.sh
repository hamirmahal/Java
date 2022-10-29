#!/bin/sh
java locks.java > 101RunsOutput.txt

for i in \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1 \
  1 1 1 1 1 1 1 1 1 1
do
java locks.java >> 101RunsOutput.txt
done

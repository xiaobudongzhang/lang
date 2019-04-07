#!/bin/bash
OLD_IFS=$IFS
IFS=
echo "$IFS"|od -b
echo "$*"
IFS=$OLD_IFS
echo "$IFS"|od -b


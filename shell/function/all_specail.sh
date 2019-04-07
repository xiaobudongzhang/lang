#!/bin/bash
function print(){
	for var in "$*" 
	do
	echo "$var"
	done
}

print
#echo "from \$*"
#tmp=$*
#print

#echo "from \$@"
#tmp=$@
#print

#echo "from \"\$*\""
#tmp="$*"
#print

#echo "from \"\$@\""
#tmp="$@"
#print

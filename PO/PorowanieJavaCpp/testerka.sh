#! /bin/bash

echo "10000000 1000696969" > test.in
testCount=3

# C++ bez opcji
g++ -O1 runC main.cpp
totalTime=0
for ((i = 0; i < $testCount; i++))
do
    start=$(gdate +%s%N)
    ./runC < test.in > /dev/null
    end=$(gdate +%s%N)
    totalTime=$(($totalTime+$end-$start))
    # echo "Elapsed time in Cpp: $(($(($end-$start))/1000000)) ms"
done 
echo -e "Total time in C++: \t $(($totalTime/1000000)) ms"

# Java bez opcji
javac Main.java
totalTime=0
for ((i = 0; i < $testCount; i++))
do
    start=$(gdate +%s%N)
    java Main < test.in > /dev/null
    end=$(gdate +%s%N)
    totalTime=$(($totalTime+$end-$start))
    # echo "Elapsed time in Java: $(($(($end-$start))/1000000)) ms"
done
echo -e "Total time in Java: \t $(($totalTime/1000000)) ms"

# Java -Xcomp -Xdiag opcji
javac Main.java
totalTime=0
for ((i = 0; i < $testCount; i++))
do
    start=$(gdate +%s%N)
    java -Xcomp -Xdiag Main < test.in > /dev/null
    end=$(gdate +%s%N)
    totalTime=$(($totalTime+$end-$start))
    echo "Elapsed time in Java -Xcomp -Xdiag: $(($(($end-$start))/1000000)) ms"
done
echo -e "Total time in Java -Xcomp -Xdiag: \t $(($totalTime/1000000)) ms"

# Java -Xint -Xdiag opcji
javac Main.java
totalTime=0
for ((i = 0; i < $testCount; i++))
do
    start=$(gdate +%s%N)
    java -Xint -Xdiag Main < test.in > /dev/null
    end=$(gdate +%s%N)
    totalTime=$(($totalTime+$end-$start))
    echo "Elapsed time in Java -Xint -Xdiag: $(($(($end-$start))/1000000)) ms"
done
echo -e "Total time in Java -Xint -Xdiag: \t $(($totalTime/1000000)) ms"



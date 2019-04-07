#/bin/bash

#单行直接定义
arr_var=(1 2 3)

#索引下标定义

arr_var2[0]="test0"
arr_var2[2]="test2"
arr_var2[3]="test3"

#打印特定下标
echo ${arr_var[0]}
echo ${arr_var2[0]}


#清单打印
echo ${arr_var2[*]}


echo ${arr_var2[@]}

#打印索引列表
echo ${!arr_var2[*]}

echo ${!arr_var2[@]}

#长度
echo ${#arr_var2[*]}




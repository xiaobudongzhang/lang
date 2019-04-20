package point

import "fmt"

func Print()  {
	//对切片字面量的索引结果值是可寻址的。
	fmt.Printf("%p\n",  &([]int{1, 2, 3}[0]))

	//变量
	a := 1
	fmt.Printf("%p\n",  &a)

	var map1 = map[int]string{1: "a", 2: "b", 3: "c"}
	_ = (map1[2]) // 对字典变量的索引结果值不可寻址。
	fmt.Printf("%v\n",  map1[2])
}

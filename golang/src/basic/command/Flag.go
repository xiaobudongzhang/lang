package main

import (
	"flag"
	"fmt"
)
var name string

func init()  {
	flag.StringVar(&name, "name", "zhang", "name max length 20")
	flag.StringVar(&name, "name2", "zhang", "name max length 20")
}

func main()  {
	//init的内容可以放到这里，必须放到Parse前
	flag.Parse()//parse必须使用，否则取不到客户端传的数据
	fmt.Println("input name is " + name)
}



package point

import (
	"lang/golang/basic/command"
	"testing"
	"fmt"
)

func TestDog_Name(t *testing.T) {
	command.Init()
	//绕过不可修改名称的权限
	dog := Dog{name:"little pig"}
	fmt.Println(dog.Name())
}

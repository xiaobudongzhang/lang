package point

import (
	"fmt"
	//"fmt"
	"github.com/xiaobudongzhang/lang/golang/basic/command"
	//"github.com/xiaobudongzhang/sf/lib"

	"github.com/xiaobudongzhang/sf/lib"
	"testing"
)

func TestDog_Name(t *testing.T) {
	lib.PrintFunc()
	command.Init()
	//绕过不可修改名称的权限
	dog := Dog{name:"little pig"}
	fmt.Println(dog.Name())
}

package command

import (
	"fmt"
	"github.com/xiaobudongzhang/lang/golang/basic/point"
	"github.com/xiaobudongzhang/sf/lib"
	"testing"
	"unsafe"
)

func TestInit(t *testing.T) {
	lib.PrintFunc()
	//绕过不可修改名称的权限
	dog := point.NewDog("little dog")
	dogP := &dog
	dogPtr := uintptr(unsafe.Pointer(dogP))


	fmt.Printf("ptr:%p\n", &dogPtr)
	fmt.Printf("dog:%v\n", &dog)

	str := "dddd"
	fmt.Printf("%d", str)
}
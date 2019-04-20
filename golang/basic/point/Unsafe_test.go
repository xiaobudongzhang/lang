package point

import (
	"fmt"
	"github.com/xiaobudongzhang/sf/lib"
	"reflect"
	"testing"
	"unsafe"
)

func TestDog_Name(t *testing.T) {
	lib.PrintFunc()
	int1 := 1
	var int2 uintptr
	int2 = uintptr(unsafe.Pointer(&int1))
	var int4 *int
	int4 = &int1
	int3 := &int1
	fmt.Printf("int1:%p-int2:%p-int3:%v-int4:%v\n", &int1, &int2, reflect.TypeOf(int3),int4)

	//绕过不可修改名称的权限
	dog := Dog{"little pig", 5}

	dogPtr := unsafe.Pointer(&dog)
	dogPp := uintptr(dogPtr)

	dogP := &dog
	agePtr := dogPp + unsafe.Offsetof(dogP.age)
	ageP := (*int)(unsafe.Pointer(agePtr))


	fmt.Printf("&dog:%p\n", &dog)
	fmt.Printf("dogPp:%v\n", dogPp)
	fmt.Printf("&dogPp:%p\n", &dogPp)

	*ageP = 4
	fmt.Printf("unsafe.Offsetof(dogP.age):%v\n", unsafe.Offsetof(dogP.age))
	fmt.Printf("&dog.age:%v\n", *(&dog.age))
	fmt.Printf("ageP:%v\n", ageP)



}

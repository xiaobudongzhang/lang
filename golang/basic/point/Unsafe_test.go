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
	dogPp := (*string)(dogPtr)

	dogP := &dog
	y :=uintptr(dogPtr)
	px := unsafe.Pointer(y+ unsafe.Offsetof(dogP.age))

	x2 := unsafe.Pointer(nil)
	fmt.Printf("px ,x2:%v,%v\n",  px, x2)
/*	dogP := &dog
	agePtr := dogPp + unsafe.Offsetof(dogP.age)
	ageP := (*int)(unsafe.Pointer(agePtr))
*/
	fmt.Printf("ageP:%v\n", *dogPp)

/*	fmt.Printf("&dog:%p\n", &dog)
	fmt.Printf("dogPp:%v\n", dogPp)
	fmt.Printf("&dogPp:%p\n", &dogPp)
	fmt.Printf("dog:%v\n", dog)

	dog.age =55

	fmt.Printf("dog:%v\n", dog)
	//*ageP = 4
	fmt.Printf("unsafe.Offsetof(dogP.age):%v\n", unsafe.Offsetof(dogP.age))
	fmt.Printf("&dog.age:%v\n", *(&dog.age))
*/
	dog.name = "big dog"
	fmt.Printf("ageP:%v\n", *dogPp)
/*
	// Alignof
	fmt.Println(unsafe.Alignof(struct {
		f  int8
		ff int32
	}{}))



	//1 .
	f := 1.1
	fmt.Printf("f:%f, Float64bits:%v\n", f, Float64bits(f))*/
	ax := 1
	u := uintptr(unsafe.Pointer(&ax))

	x4 := unsafe.Pointer(u)
	fmt.Printf("%v\n", x4)
}


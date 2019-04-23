package point

import (
	"fmt"
	"reflect"
	"unsafe"
)

func Float64bits(f float64) uint64  {
	return *(*uint64)(unsafe.Pointer(&f))
}

func SumObj()  {


	var x uint8 = 1<<1 | 1<<5
	var y uint8 = 1<<1 | 1<<2
	fmt.Printf("%08b\n", x) // "00100010", the set {1, 5}
	fmt.Printf("%08b\n", y) // "00000110", the set {1, 2}
	fmt.Printf("%08b\n", x&^y) // "00100000", the difference {5}


	type Dog struct {
		name string
	}

	dog := Dog{"haha"}
	namePtr := unsafe.Pointer(uintptr(unsafe.Pointer(&dog)) + unsafe.Offsetof(dog.name))
	fmt.Printf("%v\n", *(*string)(namePtr))


	var s string
	s = "something"
	end := unsafe.Pointer(uintptr(unsafe.Pointer(&s)) + unsafe.Sizeof(s))
	fmt.Printf("end:%v\n", *(*string)(end))


	up := uintptr(unsafe.Pointer(&dog))
	namePtr2 := unsafe.Pointer(up + unsafe.Offsetof(dog.name))
	fmt.Printf("%v\n", *(*string)(namePtr2))

	u := unsafe.Pointer(nil)
	p := unsafe.Pointer(uintptr(u) + 1)
	fmt.Printf("nil :%v\n", p)

}

func SysCall()  {
	//syscall.Syscall(SYS_READ, uintptr(fd), uintptr(unsafe.Pointer(p)), uintptr(n))
}

func Reflect()  {
	a := 1
	p := (*int)(unsafe.Pointer(reflect.ValueOf(&a).Pointer()))
	fmt.Printf("p:%v\n", *p)
	// INVALID: uintptr cannot be stored in variable
	// before conversion back to Pointer.
	u := reflect.ValueOf(&a).Pointer()
	p2 := (*int)(unsafe.Pointer(u))
	fmt.Printf("p2:%v\n", *p2)
}

func Header()  {

	s := "hello"
	hdr := (*reflect.StringHeader)(unsafe.Pointer(&s))
	hdr.Data = uintptr(unsafe.Pointer(&s))
	hdr.Len = len(s)

	fmt.Printf("hdr0:%v\n", hdr)
	//invliad
	var s2 string
	s2 = "hello"

	var hdr2 reflect.StringHeader
	hdr2.Data = uintptr(unsafe.Pointer(&s2))
	hdr2.Len = len(s)
	fmt.Printf("hdr2:%v\n", *(*string)(unsafe.Pointer(&hdr2)))
}
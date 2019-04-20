package point

//不安全的引用

type Dog struct {
	name string
	age int
}

func (dog *Dog) setName(name string)  {
	dog.name = name
}

func (dog *Dog) setAge(age int)  {
	dog.age = age
}
func (dog Dog) Name() string {
	return "this dog name is :" + dog.name
}

func (dog Dog) Age() string  {
	return "this dog age is :" + string(dog.age)
}
func NewDog(name string) *Dog {
	return &Dog{name:name}
}



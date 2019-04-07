package point

//不安全的引用

type Dog struct {
	name string
}

func (dog *Dog) setName(name string)  {
	dog.name = name
}

func (dog Dog) Name() string {
	return "this dog name is :" + dog.name
}



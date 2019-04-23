package point

import (
	"fmt"
	"testing"
)

func TestFloat64bits(t *testing.T) {
	fmt.Printf("1.1 float64bits:%d\n", Float64bits(1.1))
}

func TestSumObj(t *testing.T) {
	SumObj()
}
func TestReflect(t *testing.T) {
	Reflect()
}

func TestHeader(t *testing.T) {
	Header()
}

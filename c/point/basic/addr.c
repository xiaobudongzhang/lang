#include <stdio.h>

int main(void){
  int a = 1;
  int b = a;
  printf("a:%p\n", &a);
  printf("b:%p\n", &b);


  int c = 1;
  int * d = &c;
  
  printf("c:%p\n", &c);
  printf("d:%p\n", d);
  return 0;
}

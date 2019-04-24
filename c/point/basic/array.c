#include <stdio.h>
#include "array_head.h"

int print_arr(int arr[]){
	printf("arr:%p\n", arr);
	
	printf("sizeof:%d\n", sizeof(arr));
}

int main(void){
	
	extern int mango[];
	//error
	//extern int *mango;
	//
	char *p = "hello";
	//error 
	//double *pip =3.141;
	//error
	//strncpy(p, "world", 5);

	int arr[4]= {1, 2,3,4};
	printf("arr:%p\n", arr);
	print_arr(arr);
}

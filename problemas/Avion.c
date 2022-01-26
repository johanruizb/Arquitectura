#include <stdio.h>//link section
#define pi 3.14;//defination section
float area(float r);//global declaration
int main()//main function
{
float r;
printf(" Enter the radius:n");
scanf("%f",&r);
printf("the area is: %f",area(r));
return 0;
}
float area(float r)
{
return pi * r * r;//sub program
}
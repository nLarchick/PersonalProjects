#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Usage: %s <divisorInt> <dividendInt>\n", argv[0]);
        return 1; // Return an error code to indicate incorrect usage
    }

    // Retrieve the two arguments from the command line
    int num1 = atoi(argv[1]);
    int num2 = atoi(argv[2]);
    int ans  = num1/num2;
    if (num2 % num1){
        printf("%d IS divisible by %d, ans= %d\n", num1, num2, ans);
        return(1); // Is Divisible
    } else {
        printf("%d IS NOT divisible by %d\n", num1, num2);
        return (0); // Not Divisible
    }
}
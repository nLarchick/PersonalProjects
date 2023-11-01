#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]){
  // Check if the correct number of command-line arguments are provided
  if (argc != 6){
    printf("Usage: %s <divisor> <dividend1> <dividend2> <dividend3> <dividend4>\n", argv[0]);
    return 1; // Return an error code
  }

  for (int i = 2; i <= 5; i++) { // iter from 2-5
    int child_pid = fork();

    if (child_pid < 0) {
      perror("!! Fork Failed !!\n");
    }
    if (child_pid == 0) {
      printf("child %d prints:\n", getpid());
      char *checker_args[] = {"Checker", argv[i], argv[1], NULL};
      if (execvp("Checker", checker_args) == -1) {
        printf("Couldnt run checker in process %d", i);
        exit(1);
      }
      exit(0);
    } else {
      int status;
      int exit_status = WEXITSTATUS(status);
      wait(NULL);
      printf(" ==> run by child %d with exit %d\n", child_pid, exit_status);
    }
  }

  return 0; // Return 0 to indicate successful execution
}
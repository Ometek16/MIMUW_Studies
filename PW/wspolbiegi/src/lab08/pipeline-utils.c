#include <dirent.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ptrace.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#include "err.h"

#define MAX_PATH_LENGTH 1024

/* Print (to stderr) information about all open descriptors in current process. */
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

void print_open_descriptors() {
	fprintf(stderr, "Printing open file descriptors:\n");

	// Zakładamy maksymalną liczbę deskryptorów jako 1024 (zmień w razie potrzeby)
	for (int fd = 0; fd < 1024; ++fd) {
		// Sprawdź, czy deskryptor jest otwarty
		if (fcntl(fd, F_GETFD) != -1) {
			fprintf(stderr, "Descriptor %d is open.\n", fd);
		}
	}
}
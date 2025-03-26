#include <fcntl.h>   // For O_* constants.
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <unistd.h>
#include <dirent.h>
#include <errno.h>

void ASSERT_SYS_OK(int expr) {
	if (expr == -1) {
		perror("System call failed");
		exit(EXIT_FAILURE);
	}
}

void fatal(const char* msg, int code) {
	fprintf(stderr, msg, code);
	exit(EXIT_FAILURE);
}

void list_shared_memory_objects(void) {
	printf("Obecnie w katalogu /var/tmp znajdują się obiekty pamięci współdzielonej:\n");

	DIR* dir = opendir("/var");
	if (!dir) {
		perror("opendir failed");
		return;
	}

	struct dirent* entry;
	while ((entry = readdir(dir)) != NULL) {
		// Obiekty pamięci współdzielonej na macOS zaczynają się prefiksem "shm."
		if (strncmp(entry->d_name, "shm.", 4) == 0) {
			printf("%s\n", entry->d_name);
		}
	}

	if (closedir(dir) == -1) {
		perror("closedir failed");
	}

	printf("\n");
}

int main(void) {
	const int nap_time_seconds = 2;
	const char* shm_name = "/shm.mimuw_pw_lab_test_memory";

	list_shared_memory_objects();

	// Tworzymy i otwieramy fragment pamięci dzielonej.
	int fd_memory = shm_open(shm_name, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
	ASSERT_SYS_OK(fd_memory);
	printf("Stworzyłem fragment pamięci, jeśli nie istniał.\n");
	list_shared_memory_objects();

	sleep(nap_time_seconds);

	// Zamykamy deskryptor, ale plik specjalny dalej istnieje.
	ASSERT_SYS_OK(close(fd_memory));
	printf("Zamknąłem deskryptor z dostępem do pamięci.\n");
	list_shared_memory_objects();

	sleep(nap_time_seconds);

	// Usuwamy plik specjalny.
	ASSERT_SYS_OK(shm_unlink(shm_name));
	printf("Usunąłem fragment pamięci.\n");
	list_shared_memory_objects();

	return 0;
}

CC = gcc
CFLAGS = -Wall -g -Werror -DDEBUG
SOURCES = main.c communication_diffuseur.c communication_client.c
OBJECTS=$(SOURCES:.c=.o)
EXECUTABLE=gestionnaireC

$(EXECUTABLE): $(OBJECTS)
	gcc -o $(EXECUTABLE) $(OBJECTS) -pthread

%.o : %.c
	gcc -c $(CFLAGS) $< -o $@ -pthread

cleanall:
	rm -f $(EXECUTABLE) $(OBJECTS)

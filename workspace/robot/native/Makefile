CC = g++
CFLAGS = -lftd2xx -o relaydriver -L/usr/lib -W1,-rpath=/usr/lib

APP = relaydriver

all: $(APP)

$(APP): main.c  
        $(CC) relaydriver.cpp -o $(APP) $(CFLAGS)
        
clean:
        rm -f *.o ; rm $(APP)
OS     := linux
ARCH   := x86_64
NATURE := exe
TARGET := helloworld

CC      := g++
CCFLAGS := -Wall -O2
CXFLAGS :=

ifeq ($(NATURE), exe)
  ifeq ($(OS), windows)
	TARGET := $(TARGET).exe
  endif
else ifeq ($(NATURE), lib)
  CCFLAGS += -fPIC
  CXFLAGS += -shared  
  ifeq ($(OS), linux)
	TARGET := lib$(TARGET).so
  else ifeq ($(OS), windows)
	TARGET := $(TARGET).dll
  endif
endif

ifeq ($(OS), windows)
  ifeq ($(ARCH), x86_64)
    CC := x86_64-w64-mingw32-g++
  else ifeq ($(ARCH), i686)
    CC := i686-w64-mingw32-g++
  endif
  CCFLAGS += -static
  #CCFLAGS += -static-libstdc++ -static-libgcc
endif


SRCS := logo.cpp main.cpp Trie.cpp
ASMS := $(SRCS:.cpp=.s)
OBJS := $(SRCS:.cpp=.o)

all: clean $(TARGET) $(ASMS)

$(TARGET): $(OBJS)
	$(CC) $(CCFLAGS) $(CXFLAGS) $(OBJS) -o $(TARGET)

%.o: %.cpp
	$(CC) $(CCFLAGS) -c $< -o $@

%.s: %.cpp
	$(CC) $(CCFLAGS) -S $< -o $@

clean:
	rm -f $(ASMS) $(OBJS) $(TARGET) $(TARGET)

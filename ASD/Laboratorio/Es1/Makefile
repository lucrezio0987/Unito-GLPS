# CC = gcc
# CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion -w
# SRC_DIR = src
# BUILD_DIR = build
# BIN_DIR = bin
# TARGET = $(BIN_DIR)/ordinamento
# 
# SRCS = $(wildcard $(SRC_DIR)/*.c)
# OBJS = $(patsubst $(SRC_DIR)/%.c,$(BUILD_DIR)/%.o,$(SRCS))
# 
# .PHONY: all clean
# 
# all: $(TARGET)
# 
# $(TARGET): $(OBJS) | $(BIN_DIR)
# 	$(CC) $(CFLAGS) $^ -o $@
# 
# $(BUILD_DIR)/%.o: $(SRC_DIR)/%.c | $(BUILD_DIR)
# 	$(CC) $(CFLAGS) -c $< -o $@
# 
# $(BUILD_DIR):
# 	mkdir -p $(BUILD_DIR)
# 
# $(BIN_DIR):
# 	mkdir -p $(BIN_DIR)
# 
# clean:
# 	rm -rf $(BUILD_DIR) $(BIN_DIR)

all: build/ordinamento.o bin/ordinamento

clean: 
	$(RM) -rf bin/*
	$(RM) -rf build/*

build/ordinamento.o: src/Libreria.c src/Interfaccia.h
	$(CC) $(CFLAGS) -c src/Libreria.c -o build/Libreria.o

bin/ordinamento: src/Interfaccia.h build/Libreria.o src/Ordinamento.c
	$(CC) $(CFLAGS) -o bin/Ordinamento build/Libreria.o src/Ordinamento.c
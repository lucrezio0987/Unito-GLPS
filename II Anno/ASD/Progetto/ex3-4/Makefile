JUNIT_JAR 			= junit-4.10.jar
CLASS_DIR 			= ./
SRC_DIR_PRIORITYQUEUE  		= ../ex3/src
SRC_DIR_PRIM  			= ../ex4/src
TEST_CLASS_PRIM 		= TestPrim
TEST_CLASS_PRIORITYQUEUE 	= TestPriorityQueue

all: Compilazione Test Eseguzione

Compilazione:
	javac -cp $(JUNIT_JAR) -d $(CLASS_DIR) $(SRC_DIR_PRIORITYQUEUE)/*.java
	javac -cp $(JUNIT_JAR) -d $(CLASS_DIR) $(SRC_DIR_PRIM)/*.java

Test: Compilazione
	java -cp $(JUNIT_JAR):$(CLASS_DIR) org.junit.runner.JUnitCore $(TEST_CLASS_PRIORITYQUEUE)
	java -cp $(JUNIT_JAR):$(CLASS_DIR) org.junit.runner.JUnitCore $(TEST_CLASS_PRIM)

Eseguzione: Compilazione
	java Prim italian_dist_graph.csv

clean:
	rm -rf $(CLASS_DIR)*.class

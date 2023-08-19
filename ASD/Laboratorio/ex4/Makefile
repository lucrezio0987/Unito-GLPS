JUNIT_JAR = junit-4.10.jar
SRC_DIR = src
CLASS_DIR = classes
TEST_CLASS = TestPrim

all: Test Eseguzione

Compilazione: 
	mkdir -p $(CLASS_DIR)
	javac -cp $(JUNIT_JAR) -d $(CLASS_DIR) $(SRC_DIR)/*.java
	javac -cp $(JUNIT_JAR) -d ../ex3-4 $(SRC_DIR)/*.java

Test: Compilazione
	java -cp $(JUNIT_JAR):$(CLASS_DIR) org.junit.runner.JUnitCore $(TEST_CLASS)

Eseguzione:
	java -cp classes GrafoMain
	java -cp classes Prim italian_dist_graph.csv

clean:
	rm -rf $(CLASS_DIR)

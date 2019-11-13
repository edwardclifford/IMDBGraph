FLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  GraphPartialTester.java \
		  GraphSearchEngine.java \
		  GraphSearchGUI.java \
		  IMDBGraph.java \
		  Node.java \

MAIN = IMDBGraph 

GFX = GraphSearchGUI 

TEST = GraphPartialTester

default: classes

classes: $(CLASSES:.java=.class)

run: classes 
	$(JVM) $(MAIN) 

gfx: classes 
	$(JVM) $(GFX)

test: classes 
	$(JVM) org.junit.runner.JUnitCore $(TEST) 

clean:
	$(RM) *.class

FLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  GraphPartialTester.java \
		  GraphSearchEngine.java \
		  GraphSearchEngineImpl.java \
		  GraphSearchGUI.java \
		  IMDBGraph.java \
		  IMDBGraphImpl.java \
		  Node.java \
		  ActorNode.java \
		  MovieNode.java \


MAIN = IMDBGraphImpl 

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

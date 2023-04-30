antlr4 = java org.antlr.v4.Tool
grun = java org.antlr.v4.gui.TestRig
SRCFILES = CNFConverter.java AST.java Environment.java
GENERATED = LogicParser.java LogicBaseVisitor.java LogicVisitor.java LogicLexer.java

all:
	make tree

CNFConverter.class:	$(SRCFILES) $(GENERATED) Logic.g4
	javac  $(SRCFILES) $(GENERATED)

LogicLexer.java:	Logic.g4
	$(antlr4) -visitor Logic.g4

LogicLexer.class: LogicLexer.java
	javac $(GENERATED)

test:   CNFConverter.class input.txt
	java main input.txt

tree:	LogicLexer.class Logic.g4 input.txt
	$(grun) Logic start -gui -tokens input.txt

clean:
	rm -rf Logic*.java Logic*.interp Logic*.tokens *.class
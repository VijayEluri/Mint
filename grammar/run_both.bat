
rem set PATH = %PATH%;F:\Antlr\antlr-2.7.2a2;
rem set CLASSPATH = %CLASSPATH%;F:\Antlr\antlr-2.7.2a2\antlr.jar
java antlr.Tool MintParser.g
echo "==========================="
java antlr.Tool MintTree.g

move *.java ..\src\org\mint\src\
move *.txt ..\src\org\mint\src\
pause
# export JSOUP_HOME = "/home/alunos/.jdks/jsoup-1.16.1.jar:."

# export $PATH = $PATH:$JSOUP_HOME

# javac -classpath "/home/alunos/.jdks/jsoup-1.16.1.jar;." *.java

cd ./Model/

javac -cp "./libs/jsoup-1.16.1.jar:./" *.java

# java Main.java

java -cp "./libs/jsoup-1.16.1.jar:./" Main.java
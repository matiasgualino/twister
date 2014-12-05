twister
=======

Twister is a mini java compiler with JCup, JFlex and Jasmin.

Obtención del proyecto: git clone https://github.com/matiasgualino/twister.git

Compilación del proyecto maven: mvn clean compile assembly:single

Verificar que el código es aceptado: java -cp target/twister-jar-with-dependencies.jar ar.edu.itba.geb.compiler.java.core.Main accept src/main/java/ar/edu/itba/geb/compiler/java/examples/HelloWorld.twister

Obtener el listado de tokens: java -cp target/twister-jar-with-dependencies.jar ar.edu.itba.geb.compiler.java.core.Main tokens src/main/java/ar/edu/itba/geb/compiler/java/examples/HelloWorld.twister

Obtener un output del programa: java -cp target/twister-jar-with-dependencies.jar ar.edu.itba.geb.compiler.java.core.Main run src/main/java/ar/edu/itba/geb/compiler/java/examples/HelloWorld.twister

Visualizar el bytecode generado: java -cp target/twister-jar-with-dependencies.jar ar.edu.itba.geb.compiler.java.core.Main bytecode src/main/java/ar/edu/itba/geb/compiler/java/examples/HelloWorld.twister

Programas de ejemplo Factorial.twister, FactorialRecursive.twister, Fibonacci.twister, FibonacciIterative.twister, HelloWorld.twister, Prime.twister

# AI_Project_01

Repo for the first project of the Artificial Inteligence class (U.Porto FCUP 2020/2021);
This is a Java program which generates N points in a 2D space of boundaries -M to M;

# Compiling

The program should already be compiled, but to reassure it, you may call the following command on your shell, while in the main folder:
```bash
javac src/*.java -d bin/
```
# Usage

The program can be started by running 
```bash
java bin/Main
```

The user will be asked to input a number N of points to be randomly generated, and a boundary M to generate them.
Then, the user can choose which option he would like to use to generate the first candidate: randomly or nearest-neighbour.
Finally, the user may then enter the method to choose the next candidates until a simple polygon is found, which will reset the program.

At any given time, the user may exit the program by entering '0'.
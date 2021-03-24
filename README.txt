# AI_Project_01

Repo for the first project of the Artificial Inteligence class (U.Porto FCUP 2020/2021);
This is a Java program which generates N points in a 2D space of boundaries -M to M;

(Em português abaixo)

# Compiling

The program should already be compiled, but to reassure it, you may call the following command on your shell, while in the main folder:
```bash
javac src/*.java -d bin/
```
# Usage

The program can be started by ascending to the 'bin' folder and running
```bash
java Main
```

The user will be asked to input a number N of points to be randomly generated, and a boundary M to generate them.
```bash
Please enter the number of points to be generated: 
Please enter the boundary to generate the points: 
```

Then, the user can choose which option he would like to use to generate the first candidate: randomly or nearest-neighbour. The instructions will appear in the terminal.

Finally, the user may then enter the method to choose the next candidates until a simple polygon is found, which will restart the program. The instructions will appear in the terminal.

#Queries Resolved

So far, we've successfully implemented the queries 1., 2.a, 2.b, 3., 4.a, 4.b, 4.c and 4.d from the project guideline.

______

# Compilação

O programa já deve estar compilado, mas, se necessário, pode ser chamado o seguinte comando no terminal a partir do directório principal:
```bash
javac src/*.java -d bin/
```

# Uso

O programa pode ser arrancado ascendendo ao directório 'bin' e chamando o seguinte commando:
```bash
java Main
```

O usuário será pedido para entrar um número N de pontos que serão gerados aleatoriamente e um limite M.
```bash
Please enter the number of points to be generated: 
Please enter the boundary to generate the points: 
```

O usuário deve então escolher por qual método quer que seja gerado o primeiro candidato: permutação aleatória ou "nearest-neighbour first" a partir das instruções que apareceram no terminal.

Finalmente, o usuário deve escolher também o método que será usado para gerar os filhos do candidato atual até que se chegue a um polígono simples.

#Questões Resolvidas

Atéo momento foi implementada com sucesso as questões 1., 2.a, 2.b, 3., 4.a, 4.b, 4.c and 4.d do projecto.



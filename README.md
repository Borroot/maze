# Mazes
Generate AND solve mazes! The following solvers and generators are implemented.

## Solvers
- Depth first search
- Breath first search
- Iterative deepening
- Greedy search
- A* search

## Generators
- Recursive backtracking
- Recursive dividing rooms
- Randomized Kruskal's algorithm
- Randomized Prim's algorithm
- Wilson's algorithm
- Aldous Broder algorithm
- Hunt and Kill algorithm

# Usage
Add an environment variable.
```
export PATH_TO_FX=path/to/javafx-sdk-VERSION/lib
```
Compile the application.
```
cd src
javac --module-path $PATH_TO_FX --add-modules javafx.controls com/borroot/Main.java -d ../out
```
Run the application.
```
cd ../out
java --module-path $PATH_TO_FX --add-modules javafx.controls com.borroot.Main
```

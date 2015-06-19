Context			Programming Life
Group number	3
Group			programminglfe3
Product name	Helix²

Repository & source code can be found on:
https://github.com/ProgrammingLife3/ProgrammingLife3

- Newick Parser package.
To parse the phylogenetic tree files (.nwk) that we given to us by our client, we used the Newick Parser written in JavaCC. This code can be found on https://bitbucket.org/djiao/a-lightweight-javacc-newick-parser. We tried including the .jar file from this newick parser in our own project jar but this was not possible. The only solution to get this parser working was to include in our own source code. It can be found under src/main/java/newick/*. Our own code is under sr/main/java/tudelft/ti2806/pl3/*.
Please do not grade the newick package, because this is not our code, and should be treated as a library.

- Component balance property
We scored 0.6 on the component balance property at the last SIG grading. Since our project consists of loading multiple data strains and combine them to make a single graph, we need big data structures. That's why our data package is that big, it contains all the classes that hold data and classes that wrap nodes to achieve semantic zooming. After this low grade we reconsidered our package structure and came to the conclusion that moving classes and packages to balance the components doesn't make any sense, except for a ui package. It's more logical to put all of the data classes in the data package with the result that this package may be bigger than others.

Contact:
Tom Brouws 			T.H.Brouws@tudelft.nl
Boris Mattijssen 	borismattijssen@gmail.com
Mathieu Post 		mathieupost@gmail.com
Sam Smulders 		s.smulders@student.tudelft.nl
Kasper Wendel 		k.wendel@student.tudelft.nl
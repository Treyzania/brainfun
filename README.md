# brainfun

Brainfun is a fork of the original brainf\*\*\*.  This repo is a basic compiler
which does not support file IO nor direct data pointer access, however most of
the core language features are supported.

This program does not actually directly generate an executable, it generates
peice of C source code that represents an equivalent program.  If the compiler
is run on a Linux machine, it will attempt to invoke GCC to compile the `.c`
file if specified with the `-G` switch.

*See brainfun-0.2.3-clean.txt for details.*

[My website](http://treyzania.com/)
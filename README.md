# JD3Bash

A GUI for designing multistage Rosetta pipelines.
Use the interactive node editor to create a multistage protocol
and the program will give you two bash files:

- setup.sh: Creates directories and files required for the run.
- run.sh: Takes care of everything! Calls Rosetta and pipes the best results from stage to stage.

See below for more details.

# JD3Bash

A __zero-dependency__ GUI for designing multistage Rosetta pipelines.
Use the interactive node editor to create a multistage protocol
and the program will give you two bash files:

- __setup.sh__: Creates directories and files required for the run.
- __run.sh__: Takes care of everything! Calls Rosetta and pipes the best results from stage to stage.

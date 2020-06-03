RESOLVEWebAPI
==============
[![Build Status](https://travis-ci.org/ClemsonRSRG/RESOLVEWebAPI.svg?branch=master)](https://travis-ci.org/ClemsonRSRG/RESOLVEWebAPI)
[![License](https://img.shields.io/badge/license-BSD-blue.svg)](https://raw.githubusercontent.com/ClemsonRSRG/RESOLVEWebAPI/master/LICENSE.txt)
<img align="right" src="public/images/resolve_logo.png" width="200"/> 

This is the back-end API that services request to compile, translate and verification using the [RESOLVE compiler](https://github.com/ClemsonRSRG/RESOLVE). The project requires `sbt 1.x`, `Scala 2.13.x`, `Java 8+` and `Play Framework 2.8.x` to run. Instructions to install these requirements can be found on their respective websites.

## What is RESOLVE?

RESOLVE (REusable SOftware Language with VErification) is a specification and programming language designed for verifying correctness of object oriented programs.

The RESOLVE language is designed from the ground up to facilitate *mathematical reasoning*. As such, the language provides syntactic slots for assertions such as pre-post conditions that are capable of abstractly describing a program's intended behavior. In writing these assertions, users are free to draw from a variety of pre-existing and user-defined mathematical theories containing fundamental axioms, definitions, and results necessary/useful in establishing program correctness.

All phases of the verification process spanning verification condition (VC) generation to proving are performed in-house, while RESOLVE programs are translated to Java and run on the JVM.

## Setting up

As a prerequisite, you will need to download and install `sbt 1.x` and `Java JDK 8+`. The current project is already configured to install `Scala 2.13.x`, `Play Framework` and all necessary plugins. Instructions on how to setup your favorite development IDE can be found [here](https://www.playframework.com/documentation/2.8.x/IDE).

### Application Layout

This project is similar to a typical `Play Framework` project and follows the `MVC` pattern. Only the important files are described in this section and shown in the figure below. See [here](https://www.playframework.com/documentation/2.8.x/Anatomy) for more information. All application source files are located inside the `app` folder and the unit testing files are located in `test`. The `compiler` folder contains things that are needed to instantiate a compile job as well as how to handle input and output messages from the compiler. The `controllers` and `views` folders contain the controllers for the various different `WebSocket` protocols and `HTML` rendering template code. All static resources can be found in the `public` directory. 

The `bin` folder contains integration scripts to be used by `TravisCI` and should only be modified if any of the integration testing needs to be updated. Inside the `conf` folder are various different settings to be used by the `application`. This `application` depends on a deployment-dependent `local-config.conf`. More information on how to setup this file can be found in the following section.

```
RESOLVEWebAPI/
├── app/
│   ├── compiler
│   ├── controllers
│   ├── views
└── bin/
└── custom_lib/
└── conf/
└── project/
└── public/
│   ├── stylesheets
│   ├── javascripts 
│   ├── images 
└── test/
...
```

### Additional Project Configurations and Dependencies

There are two things that needs to be added to the application before it can run. First, you must have the latest version of the RESOLVE compiler. You can obtain this by running the maven install command on the compiler project. Place the generated `jar` file inside the `custom_lib` folder. Note that there is already a version inside `test/lib` folder. **Do not use this version!** This version is only used for integration and unit testing. There is no guarantee that it will produce the same results as the latest version of the compiler.

In addition to the RESOLVE compiler, you will need to supply a RESOLVE workspace folder containing all the library files for the `RESOLVE` language. The workspace can be found [here](https://github.com/ClemsonRSRG/RESOLVE-Workspace). Note that you can have multiple distinct copies of the RESOLVE workspace folder (with different names).

Lastly, you will need to use the `local-config-template.conf` and create a `local-config.conf` file with all the required information filled in. This contains information on the various port numbers as well as a path to all the RESOLVE workspaces.

## Authors and major contributors
The creation and continual evolution of the RESOLVE language is owed to an ongoing joint effort between Clemson University, The Ohio State University, and countless educators and researchers from a variety of [other](https://www.cs.clemson.edu/resolve/about.html) institutions.

Developers of this particular test/working-iteration of the `RESOLVE WebAPI` include:

* [RESOLVE Software Research Group (RSRG)](https://www.cs.clemson.edu/resolve/) - School of Computing, Clemson University

## Copyright and license

Copyright © 2020, [RESOLVE Software Research Group (RSRG)](https://www.cs.clemson.edu/resolve/). All rights reserved. The use and distribution terms for this software are covered by the BSD 3-clause license which can be found in the file `LICENSE.txt` at the root of this repository. By using this software in any fashion, you are agreeing to be bound by the terms of this license. You must not remove this notice, or any other, from this software.

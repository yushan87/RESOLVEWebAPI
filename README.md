RESOLVEWebAPI
==============
[![Build Status](https://travis-ci.org/ClemsonRSRG/RESOLVEWebAPI.svg?branch=master)](https://travis-ci.org/ClemsonRSRG/RESOLVEWebAPI)
<img align="right" src="public/images/resolve_logo.png"/>

This is the back-end API that services request to compile, translate and verification using the RESOLVE compiler. 
The project requires `Java 8+` and `Play Framework 2.6.x` to run. Instructions to install these requirements can be found on their respective websites.

## Setting up

As a first step, you will need to download `Play Framework 2.6.x`. 
Instructions on how to install `Play Framework` and setup your favorite development IDE can be found [here](http://www.playframework.com/).

(* Add more instructions when they are needed *)

## What is RESOLVE?

RESOLVE (REusable SOftware Language with VErification) is a programming and
specification language designed for verifying correctness of object oriented
programs.

The RESOLVE language is designed from the ground up to facilitate *mathematical
reasoning*. As such, the language provides syntactic slots for assertions such
as pre-post conditions that are capable of abstractly describing a program's
intended behavior. In writing these assertions, users are free to draw from from
a variety of pre-existing and user-defined mathematical theories containing
fundamental axioms, definitions, and results necessary/useful in establishing
program correctness.

All phases of the verification process spanning verification condition (VC)
generation to proving are performed in-house, while RESOLVE programs are
translated to Java and run on the JVM.

## Authors and major contributors
The creation and continual evolution of the RESOLVE language is owed to an
ongoing joint effort between Clemson University, The Ohio State University, and
countless educators and researchers from a variety of [other](https://www.cs.clemson.edu/resolve/about.html) 
institutions.

Developers of this particular test/working-iteration of the RESOLVE WebAPI
include:

* [RESOLVE Software Research Group (RSRG)](https://www.cs.clemson.edu/resolve/) - School of Computing, Clemson University

## Copyright and license

Copyright © 2017, [RESOLVE Software Research Group (RSRG)](https://www.cs.clemson.edu/resolve/). All rights reserved. 
The use and distribution terms for this software are covered by the BSD 3-clause 
license which can be found in the file `LICENSE.txt` at the root of this repository.
By using this software in any fashion, you are agreeing to be bound by the terms
of this license. You must not remove this notice, or any other, from this
software.

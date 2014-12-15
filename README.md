eclipse-extended-variables
==========================

Adds an extra variable (resource_locs) to eclipse.
The default list of variables contains variables resource_loc which returns one of the selected file paths. 

Our check in procedure at requires that we run a shell script on all the files that were just checked in.  Before the plug-in this was a complete pain.

Any time you want to run a script on multiple selected files, add ${resource_locs} to the argument list.

Installation
===========================

* Download [eclipse-extended-variables.zip](eclipse-extended-variables.zip)
* unzip into your eclipse directory.



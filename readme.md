# Introduction
This API provides functions to rescale an image and gives information about previous rescales

<a name="Usage"></a>
# Usage

* POST /task	Rescales the image to the expected resolution and stores the task information
* GET /task/{idTask} Returns the information of the task

# Instalation

Create a local "uploads" directory and put the path in application.properties
rescalator.local.upload=C:/Temp/uploads/



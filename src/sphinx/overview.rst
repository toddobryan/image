Overview
========

The most common way to use the library is to just import everything and
initialize the library::

    scala> import net.toddobryan.image._
    scala> initialize()

Once you've done that, you can happily write all the code in the sections
that follow.

If you get tired of entering that line each time you go into the SBT console,
you can modify your ``build.sbt`` file to automatically include those
commands whenever you start the console by adding the line::

    initialCommands in console := """import net.toddobryan.image._; net.toddobryan.image.initialize()"""
    

Library Dependency
------------------

To use ``dm-image`` in your own SBT project, the library dependency is::

    "org.dupontmanual" %% "dm-image" % "0.9-SNAPSHOT"
    
For the latest version as of this writing.

ScalaDoc API
------------

The `Scaladoc API <latest/api/>`_ lists all the classes and methods in
the library.

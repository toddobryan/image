Angles
======

If you import everything from ``net.toddobryan.image._``, you can
call ``.degrees`` or ``.radians`` on a number to create an ``Angle``.
[#angleBuilder]_

::

    scala> 45.degrees
    res1: net.toddobryan.image.Angle = 45.000000 degrees

    scala> (math.Pi / 2).radians
    res2: net.toddobryan.image.Angle = 1.570796 radians


Angles are measured as they normally are in trigonometry. Zero degrees 
(or radians) is due east, and the angles increase in the counter-clockwise
direction: ``45.degrees`` is northeast, ``90.degrees`` is north, etc.

Once you have an ``Angle`` object, you can convert ``.toDegrees`` or ``.toRadians``
if you're more comfortable with one unit than another.\ [#angleApprox]_

::

    scala> 180.degrees.toRadians
    res3: net.toddobryan.image.Angle = 3.141593 radians

    scala> (math.Pi / 4).radians.toDegrees
    res4: net.toddobryan.image.Angle = 45.000000 degrees


You can find the sine, cosine, or tangent of an `Angle` using ``.sin``, 
``.cos``, or ``.tan``. (Note that, if the  tangent is undefined, you'll get
the ``Double`` value ``NaN``, which stands for "Not a Number".) You can 
also add ``Angle``\ s using ``+``. The resulting ``Angle``\'s units are the
same as the units of of the first ``Angle`` in the addition.

::

    scala> 60.degrees + (math.Pi / 6).radians
    res5: net.toddobryan.image.Angle = 90.000000 degrees

    scala> (math.Pi / 4).radians + 45.degrees
    res6: net.toddobryan.image.Angle = 1.570796 radians

    scala> (math.Pi / 6).radians.sin
    res8: Double = 0.5

    scala> 60.degrees.cos
    res9: Double = 0.5

    scala> 90.degrees.tan
    res10: Double = NaN


.. rubric:: Footnotes

.. [#angleBuilder] What actually happens in this case is that there is an
  implicit conversion in the ``net.toddobryan.image`` package that turns
  numbers into something called an ``AngleBuilder``. You're actually calling
  the ``.degrees`` or ``.radians`` method on that ``AngleBuilder`` object,
  but you'll never have to do this explicitly.
  
.. [#angleApprox] Angle values are displayed with only 6 decimal digits of
  accuracy so that standard angle values in radians (π/2, π/3, π/4, π/6, etc.)
  will display as equal to their expected degree values (90, 60, 45, 30, etc.).

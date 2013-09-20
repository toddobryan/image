``Image`` Class
===============

The ``Image`` class is the superclass of all displayable shapes and bitmap 
images in the package. This means that the members of this class are key to
manipulating or combining any type of picture created in the package. 

Members of ``Image``
--------------------

Properties
^^^^^^^^^^

::

    width: Int
    height: Int
 
Return the width and height of ``this`` image. For example::

    scala> val circ: Image = CircleFilled(Color.Green, 50)
    scala> circ.width
    res0: Double = 100.0
    scala> circ.height
    res1: Double = 100.0
    scala> Glyphs.width
    res4: Double = 45.0    
    scala> Glyphs.height
    res5: Double = 68.0

Utility Methods
^^^^^^^^^^^^^^^

::

    display(): Unit

Returns nothing, but opens a message box with ``this`` image displayed.

::

    sameBitmapAs(img: Image): Boolean
    
Returns ``true`` or ``false``, depending on whether ``this`` image and
``img`` represent the same bitmap as one another. For example::

    scala> Hacker.sameBitmapAs(Glyphs)
    res0: Boolean = false

    scala> Hacker.rotate(180.degrees).sameBitmapAs(Hacker.flipVertical().flipHorizontal())
    res1: Boolean = true
    
::

    saveAsDisplayed(filename: String): Unit
    
Returns nothing, but saves ``this`` image as a ``.png`` file with the given
filename. (If the filename doesn't end in ``.png``, it gets added.) An image
saved this way can be accessed by using ``Bitmap(filename)``.

Methods that Produce new ``Image``\ s
-------------------------------------

These methods usually take in numbers as parameters, but some have no 
parameters at all. They all return a new version of the image.

Cropping
^^^^^^^^

::

     cropLeft(numPixels: Double): Image
     cropRight(numPixels: Double): Image
     cropTop(numPixels: Double): Image
     cropBottom(numPixels: Double): Image

These methods return an image that is ``this`` image with a given number of 
pixels cut from the indicated edge.

TODO: examples

::

     crop(x: Double, y: Double, cropWidth: Double, cropHeight: Double): Image

This method returns an image that is a rectangle cut from ``this``
image, with a top corner at (x, y) and given width and height.

TODO: examples

Reflections
^^^^^^^^^^^

::

     flipHorizontal(): Image
     flipVertical(): Image

These methods return an image that is ``this`` image flipped, either
left to right or top to bottom.

TODO: examples

Rotations
^^^^^^^^^

The ``rotate`` method takes an ``Angle`` argument. If you've imported everything
from the ``org.dupontmanual.image`` package, then the easiest way to make an angle
is to call the ``degrees`` method on a number. There is also a ``radians`` method
if you'd prefer to work in radians. For example::

    scala> 45.degrees
    res2: org.dupontmanual.image.Angle = 45.000000 degrees

    scala> 90.degrees
    res3: org.dupontmanual.image.Angle = 90.000000 degrees

    scala> 90.degrees == (math.Pi / 2).radians
    res4: Boolean = true
    
To rotate an image counter-clockwise, use the ``rotate`` method with a positive angle.
Negative angles rotate in a clockwise direction::

     rotate(factor: Angle): Image

This method returns an image that is ``this`` image rotated by a given
``factor`` around the image's center.

TODO: examples

Scaling
^^^^^^^

::

     scaleX(xFactor: Double): Image
     scaleY(yFactor: Double): Image

These methods return an image that is ``this`` image stretched by a given
factor either horizontally or vertically.

     scale(xFactor: Double, yFactor: Double): Image

This method returns an image that is ``this`` image scaled horizontally and 
vertically by the given factors.

TODO: examples

Translation
^^^^^^^^^^^

::

     translate(x: Double, y: Double): Image

This method returns an image that is ``this`` image moved ``x`` pixels
right and ``y`` pixels down. You can use negative numbers to translate
the image left and up.

TODO: examples

Adding Images Together
^^^^^^^^^^^^^^^^^^^^^^

::

    above(img: Image): Image
    
Creates a new ``Image``, which is ``this`` image vertically aboove ``img``.
The two ``Image``\ s are centered horizontally with respect to one another.

TODO: examples

::

    above(img: Image, xAlign: XAlign): Image
    
Creates a new ``Image``, which is ``this`` image vertically above ``img``.
The two images are aligned horizontally left, center, or right, depending
on whether ``xAlign`` is ``XAlign.Left``, ``XAlign.Center``, or 
``XAlign.Right``.

TODO: examples

::

    beside(img: Image): Image
    
Creates a new ``Image``, which is ``this`` image to the left of ``img``.
The two ``Image``\ s are centered vertically with respect to one another.

TODO: examples

::

    beside(img: Image, yAlign: YAlign): Image
    
Creates a new ``Image``, which is ``this`` image to the left of ``img``.
The two images are aligned vertically top, center, or bottom, depending
on whether ``yAlign`` is ``YAlign.Top``, ``YAlign.Center``, or 
``YAlign.Bottom``.

TODO: examples

Stacking Images
^^^^^^^^^^^^^^^

::

    stackOn(img: Image)
    slideUnder(img: Image)
    
Creates a new ``Image``, which is ``this`` image overlaid on top of ``img``.
The two ``Image``\ s are centered vertically and horizontally with respect
to one another. ``img1.stackOn(img2)`` is equivalent to
``img2.slideUnder(img1)``.

TODO: examples

::

    def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign): Image
    def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign): Image
    
Creates a new ``Image``, which is ``this`` image overlaid on top of ``img``.
The two ``Image``\ s are aligned according to the values of ``xAlign`` and 
``yAlign``. (The legal values of ``xAlign`` are ``XAlign.Left``, ``XAlign.Center``,
and ``XAlign.Right``, and the legal values of ``yAlign`` are ``YAlign.Top``,
``YAlign.Center``, and ``YAlign.Bottom``. ``img1.slideUnder(img2, xAlign, yAlign)``
is equivalent to ``img2.stackOn(img1, xAlign, yAlign)``.    

::

    def stackOn(img: Image, dx: Double, dy: Double): Image
    def slideUnder(img: Image, dx: Double, dy: Double): Image
    
Creates a new ``Image``, which is ``this`` image overlaid on top of ``img``, but offset
``dx`` pixels to the right and ``dy`` pixels down from what they would be if they were 
aligned at the center. (Use negative numbers to move the top image to the left or up.)
Note that ``img1.stackOn(img2, dx, dy)`` is equivalent to ``img2.slideUnder(img1, dx, dy)``
and that both move the image that ends up on top the given number of pixels in each
direction.

::

    def stackOn(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image
    def slideUnder(img: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double): Image
    
Creates a new ``Image``, which is ``this`` image overlaid on top of ``img``, aligned
according to ``xAlign`` and ``yAlign``, but offset ``dx`` pixels to the right and ``dy``
pixels down. (Use negative numbers for left and up.) Whether you use ``stackOn`` or
``slideUnder``, it is the top image that is moved the given number of pixels.


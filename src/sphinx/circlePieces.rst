Portions of Circles and Ellipses
================================

Arcs
----

An arc is a portion of the outline of a circle or ellipse. You can draw an arc
starting at any angle and have it extend in the positive (counter-clockwise)
or negative (clockwise) direction as little or as much as you'd like.

::

    CircularArc(paint: Paint, radius: Double, start: Angle, extent: Angle): Image
    CircularArc(pen: Pen, radius: Double, start: Angle, extent: Angle): Image
    EllipticalArc(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image
    EllipticalArc(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle): Image
    
Drawing an arc requires four kinds of information:

* ``paint`` or ``pen``—the color or color and size of the segment.
* ``radius`` or ``width`` and ``height``—the size of the whole circle or
  ellipse that this arc is part of. Note that circle uses radius, but
  ellipse uses the whole width and height, so a circle of radius 25
  would be equivalent to an ellipse with width and height of 50.
* ``start``—the angle at which the arc begins
* ``extent``—the angle that the resulting arc sweeps out

Here are some examples::

    scala> CircularArc(Color.Black, 100, 0.degrees, 180.degrees)
    
|CircularArc-Black-100-0-180|

::

    scala> EllipticalArc(Pen(Color.Magenta, 3), 200, 100, -90.degrees, -45.degrees)
    
|EllipticalArc-Magenta3-200-100-neg90-neg45|


CircularSector(paint: Paint, radius: Double, start: Angle, extent: Angle): Image
CircularSegment(paint: Paint, radius: Double, start: Angle, extent: Angle): Image
`````````````````````````````````````````````````````````````````````````````````

For all of these object's `apply()` methods, the first and second arguments
are just what you'd expect. The third and fourth arguments represent the angle
at which the arc, sector, or segment should begin, and the angle length of the
arc it cuts off. Both the start angle and the size of the angle can be positive
or negative.

A `CircularSector` looks like a pie wedge and a `CircularSegment` is what you get
when you connect the ends of an arc and fill in the area. Currently, there's
no way to draw the outline version of these shapes, unless you draw the lines and
arcs individually, but a future version may fix that.

```scala
```   
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMkAAABlCAYAAADnGMeQAAAE70lEQVR42u2dK1MjQRRG+///EQQCEYFAICIiIiIQiIiICEQEIgKBmK1vansXkvQ8Mv3uc6pSsLVQJDN9pu+9c6fbdBCFj4+Pbrvddrvdrn8dDodus9l0b29v/ev19XXwpd/Vz+v79Xrd//739zcHNgKGQ+CH8/ncvb+/Owf58Xj0+vc+Pz/7v2cl0/eSTyJJIP0/IElSNCAvr/SaLXJAM8zl+9O/AUmCYkMifdWV+uvrq6j3r/er923l0ecgXEOSxeHTzxygtgGlz6PPZWea0+nESUeSaWLYgaMrbmmzha/wEWGQ5CoMkRiaNTRQCEG6fwUAHZeWLhRIciPH0CDQVwbCcAHAHickaQSd8IeHh26/32PBDJT0r1arPhxrbbZtQhLNFM/Pz93T0xP3Dxai4ydZdDyVwyFJBSdUYry8vJBrBAjFdFwlTO0XniolsTNHi6FBCnSca5alKkkkhORg5kh37PWq7dhXI4naQpSUtxIn5zyL6zzofCBJJujGl2YOEvL88kGdl1z62ZqUxCaO6n6FfLE9byWHYEVKopo9eUdZKATz/bgAkjhmD7VL1DCFt4hCY+UqpV3cipFEVyFmD2YVJHEQ4sk+4KJXhSQq56ruTut2ndjzm3vZPltJdKVpufO0JXSec84zs5REHbqqYEE76HznWs7PThI9v8CNwTZRWK3qJZI4UAL3+PhIW0njaByoczunB+GykMQuukB5Fyw59eEll0ShlUq8ALdEySH0TiqJYlAqWDBEDpUvgyCQO6p6pbyZnEQSfWAWYoC5M0qqm8rRJdEHRRC4V5QUM4qJLQghFiwNvWLnKNEkUZUCQcDXjBKz6hVFEtW7KfOCT2KWh4NLYh+UAgghSow788ElUasJd9IhFGphKVoSNSvSiwWlRyrBJFGZl25eiEHo2wpBJOFmIcRGz6OEKg17l0ThFaVeSEGo0rB3SfRwP0Aq9Mx81pKw3x6kRpGM7wu1N0mUh7BoHOSA77HoRRK7Li9ALvjcVtyLJDx6Cznia/uHxZKELL0BLEH5sY/W+kWSEGZBC2HXIklYwBpKYOmF/G5JNJWxgQ6UwNLFJEwqOwFKmU3ukkRVA5oXoSQ0Xu+tds2WRDmIkiGAVpL42ZKUsJ8EwC3s9tlBJdEfIReB0nOTuanCLEk0i1DyhZKxq9YHkcRuXg9QOupWnzObTJZE9jGLQC3MmU0mSUIuAjXmJlMLUJMkIReBGnOTqU8xTpIkxtpGALFZrVaTLv6jkuSy2xCAbzSup9w3GZVEKzACtJzAD0qi7kn2U4ea0fgeWwLLjIVaALUzNs6dkqjsq7V8AWpnbM1qM2RXThvOA4RirLPdKQl7ikBLDI33m5Jo6vG1HAtAKSGXa/VR4wq1uMMOreHastDM+WEAJPkbalHVAkKuAUkItYDZZEQSQi1AkgkzCUCr3IqkfkmiHhZCLWgZjf/LXi5DqAUwHHJdzSQArXNZ3f0lCW3xANcemJ/20NAIcN0Bb8hHAIbzEiQBQBIAD5LQGg/wG+UkdpWgXhJt68YOugD/UaOjvSViCLUAhkMuJAFAEgAkAUASACQByBD7bIk5Ho+UfwFuoPsk+/2+MyxCBzA8mxhWRgFwIz8M7SgAbuSH4UErADd9TsLqKABuVPk1PNcO4EbNv0gCMJa4cyMRYCTcQhKAEUkUcwGAOyf5Ax7hNxJ0a503AAAAAElFTkSuQmCC"/>  
```scala
scala> CircularSector(Color.LimeGreen, 50, (math.Pi / 4).radians, (math.Pi/ 2).radians)
```  
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEcAAAAyCAYAAAAOX8ZtAAACsElEQVR42u3bO0vDUBQH8HymguAgCIKDIAjdCoJQcHNycxIcnAqCQxEcBKEgdC64CU4OPqvV+sRXbX1WChWr1SP/SKumiU3Se2/uTVI4UyENP86959xHNRLwqdQrtF5Zp8XSIiWuEjRxNkHxwzgN7Q1Rf7afIhuRlhjIDujfjx6N0vTFNM0X52mlvEK5ao5qHzURr00aL4y1yhpNXUxRNBelrs0uUwC3gecNHwzTbGFWR+eFxQynUCtQ6jZFY8dj1LPVwxSjXeD3xk/HaflpmaofVTlw6p91yjxm9Bfr3uoWCmIVfTt9esZi+HmCg2GzUFygwd1BKUCsAvMa5ikhOEjZuZs56t3ulRrFGJjYMeSQ6cxx8ND0fVo5FGOgOKBQMMM5eTnRK4PKKMaYPJ+k0lvJPQ6GULKQlGaiZR0YBavPq85xMNNjnPoRxRgz1zOWfVILztLdkm+zxSpG8iN6n/YvDlr7IKG0G2ZaoxqhkQsqzO9Al9/EQUOHtAphfgJrNiSMhrVQCNIaaHa1/EteX4+EIH+bxfJ7+XvOCYH+wjQqV7NahUARiu3H9IwxLeVBBjLCmDaBQQTC1ovZWst0+RAkIGSM1SLUcuEZBCCzoWR7y8LPQO1gbO3n+BHIDoztnUA/AdmFcbSH7AcgJzCON9hVBkK5fnh74Hs0oyIQMsYpjOtzK5WAnA4lJieeKgB1AtPxcbDMQJ3CMLlIICMQCxhmtyxkAnJTlbhfQZEBiCUM88tLXgKxhuFys8sLIGxtsobhdu1NJBBg7FwKkAZHFBBgLl8v1bowKQLo9ymBkji8gHhnjDAc1kA85xhPcFgB8SjXUuB0CiQaRjiOWyAvYDzBcQrEq8GTFscukMjJVyqcdkCiyrW0OFZAIho8JXCMQDJkjFQ4DSD8kcPLOcb4+QJ6z3WnS7EKWAAAAABJRU5ErkJggg=="/>  
```scala
scala> CircularSegment(Color.Violet, 100, -30.degrees, 100.degrees)
```  
<img src="data:image/png;base64,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"/>

### Ellipse Pieces

```scala
EllipticalSector(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image
EllipticalSegment(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle): Image
``````````````````````````````````````````````````````````````````````````````````````````````````

The elliptical arc, sector, and segment builders are similar to the circular versions.
Remember, however, that `width` and `height` represent the width and height of the whole
ellipse, not the radius (like with the circular versions), and not just the part of the 
ellipse that you're drawing.

```scala
```  
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEsAAAATCAYAAADYk/BwAAABkUlEQVR42u2Xr1MCURSFXyAQDAQCwUDYYDASCASDgUA0EAgbDEaCweAMgWAwGAwEAoHAH0AgEjYaDBsMBAOBYDAYDBvWc5mzw8LsY13xB+6+O/MxBCB8e859D+XLPAPXNxMzavl6znct8GCk6GVJqvKUFXAKHOAZQeuy5kxUbkOYUAEj8G5ErWros35NjbQiaINHI2t9pJYX4CBCmlADffBqZK1mAW6BpZEmMs/AGLxlXVZ4HIrRpa3ICo/TnTiV6NMvoAeqmt2meLKK2DvuQS+rssIzZ023iRPK3IFDfieTsqLEVWLEBfJsJtT9X8lT3/6Lcprec4eVYsQJBZ6wkr4BmGVJVnjkMjsFHVCnGPVJjinwBkwo0UuzrKhxWcEWK6kSIKfxEQ+QNqs/4gP5BZnqz7O94JXjiumzEgrcpMSHUON/XNmP16DLmg+Y1ClxuToCvH2WpbuiOPynIFVsUGJ+R5G7sLeytu3AGZPYY2JsXl8OjayvXWOeWLFAqhwwlxRrs54nxGJtyzHCUynrB+cDnSNP/yjjU5QAAAAASUVORK5CYII="/>  
```scala
scala> EllipticalSector(Color.DeepPink, 100, 200, -45.degrees, 180.degrees)
```  
<img src="data:image/png;base64,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"/>
```scala
scala> EllipticalSegment(Color.LightCoral, 75, 100, 45.degrees, 90.degrees)
```  
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANUAAAAPCAYAAABz5YrKAAACZ0lEQVR42u2aUUsCQRSF96dHREiERIRERERIDxIREiIRRkQPEhERERERPURERK7ruq7reJtzpM0eitK0Ve/AeczW9X5zz7kzjuj63YoiMa+v0np8lOjujmpeXEh4diaN42MJDg6o+u6u1Hd2KH9rS2rr67G8TEaqCwux3NlZqUxN9ST8bfdnvau2tvbpf1IbG+Jvb/OZgmKRz9k4OuKzQ9HtLb9P6+GB37EdBPp797CcSX8BxnXFPD9/wHF62gFjb4/Fh0L0VldZqJXp6Z6Lf5Tlzs+Lt7xMMLFB1AsFvqOwXI5hbD09EURsOgrVGK+270t0f9+BxRYAdufa5iaLo7q4KJWZmYmEZBgQslPaDamez7ODE76bmw54xihUiYUmDGlVmpeX0jg8ZGd57yr9WCrVgGU7Pn4j2GA/l5Ngf78Dnd0AsREqVAP3Z0bMy4s0r66448F6sNOk0xNrx8a+06VS3ByR/2Az4TSQYUfFWiYKKuQb+HNYNcJj7YNaNFW3OISBrbT1AdiQh5NmJ/8NKgTb5vm5BKUSX5LaNVU/dhJWEvktPDnh0Ok/J5fO0ACyuYfdxwZYd25OC0E1cGFiCQsJ0JC9h2Uf/xwqDA9g4RA+a9msAqRKjmyUQFajdbQZndYxiVChzUbX1zzX8VZWdHigGrnxP87ekOP/CjKnb4h0kKAaN8hyOY742543IKiMoR/FOBt5SDuRaqKmjUtLHKYh0vw0kzlf3UQAqWiLOpVTqT6mjDgjxdnZd1YxhgoHrOxG2axaOpXqh1YRV98wwu/uYg6I4xWffD6+Va1SqX6pQoHXrLDeAFMK9nGayb8UAAAAAElFTkSuQmCC"/>

.. |CircularArc-Black-100-0-180| image:: images/circlePieces/circlearc-black-100-0-180.png
.. |EllipticalArc-Magenta3-200-100-neg90-neg45| image:: images/circlePieces/ellipsearc-magenta3-200-100-neg90-neg45.png

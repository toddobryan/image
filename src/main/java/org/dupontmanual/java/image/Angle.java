package org.dupontmanual.java.image;

public class Angle extends org.dupontmanual.image.Angle {
    public Angle(double magnitude, AngleUnit unit) {
    	super(magnitude, unit == AngleUnit.DEGREES ? org.dupontmanual.image.AngleUnit.degrees() : org.dupontmanual.image.AngleUnit.radians());
    }
    
    public static Angle fromDegrees(double magnitude) {
        return new Angle(magnitude, AngleUnit.DEGREES);
    }
    
    public static Angle fromRadians(double magnitude) {
    	return new Angle(magnitude, AngleUnit.RADIANS);
    }
    
    public Angle plus(org.dupontmanual.image.Angle angle) {
    	org.dupontmanual.image.Angle a = this.$plus(angle);
    	AngleUnit unit = a.units().equals(org.dupontmanual.image.AngleUnit.degrees()) ? AngleUnit.DEGREES : AngleUnit.RADIANS;
    	return new Angle(a.magnitude(), unit);
    }
}

package net.toddobryan.java.image;

public class Angle extends net.toddobryan.image.Angle {
    public Angle(double magnitude, AngleUnit unit) {
    	super(magnitude, unit == AngleUnit.DEGREES ? net.toddobryan.image.AngleUnit.degrees() : net.toddobryan.image.AngleUnit.radians());
    }
    
    public static Angle fromDegrees(double magnitude) {
        return new Angle(magnitude, AngleUnit.DEGREES);
    }
    
    public static Angle fromRadians(double magnitude) {
    	return new Angle(magnitude, AngleUnit.RADIANS);
    }
    
    public Angle plus(net.toddobryan.image.Angle angle) {
    	net.toddobryan.image.Angle a = this.$plus(angle);
    	AngleUnit unit = a.units().equals(net.toddobryan.image.AngleUnit.degrees()) ? AngleUnit.DEGREES : AngleUnit.RADIANS;
    	return new Angle(a.magnitude(), unit);
    }
}

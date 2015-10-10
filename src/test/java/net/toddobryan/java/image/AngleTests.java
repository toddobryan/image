package net.toddobryan.java.image;

import static org.junit.Assert.*;
import net.toddobryan.java.image.Angle;
import net.toddobryan.java.image.AngleUnit;

import org.junit.Test;

public class AngleTests {
	@Test
	public void angleEquality() {
		assertEquals(new Angle(45, AngleUnit.DEGREES), Angle.fromDegrees(45));
		assertEquals(Angle.fromRadians(Math.PI / 2), Angle.fromDegrees(90));
	}
	
	@Test
	public void referenceAngles() {
		assertEquals(Angle.fromDegrees(360).refAngle(), Angle.fromDegrees(0));
		assertEquals(Angle.fromRadians(7 * Math.PI / 2.0).refAngle(), Angle.fromRadians(- Math.PI / 2.0));
		assertEquals(Angle.fromDegrees(270).refAngle(), Angle.fromDegrees(-90));
		assertEquals(Angle.fromRadians(-3.0 * Math.PI / 2.0).refAngle(), Angle.fromRadians(Math.PI / 2.0));
	}
	
	@Test
	public void specialAngleTrig() {
		assertEquals(Angle.fromDegrees(90).sin(), 1.0, 0.0000001);
		assertEquals(Angle.fromDegrees(-30).sin(), -0.5, 0.00000001);
		assertEquals(Angle.fromDegrees(135).tan(), -1.0, 0.0000001);
		assertEquals(Angle.fromDegrees(-240).cos(), -0.5, 0.0000001);
	}
	
	@Test
	public void angleAddition() {
		assertEquals(Angle.fromDegrees(60).plus(Angle.fromRadians(Math.PI / 2)), Angle.fromDegrees(150));
		assertEquals(Angle.fromDegrees(90).plus(Angle.fromRadians(-Math.PI / 6)), Angle.fromDegrees(60));
		assertEquals(Angle.fromRadians(Math.PI).plus(Angle.fromRadians(3 * Math.PI)), Angle.fromRadians(4 * Math.PI));
	}
	
	@Test
	public void angleEquals() {
		assertTrue(Angle.fromDegrees(45).equals(Angle.fromDegrees(45)));
		assertTrue(Angle.fromDegrees(45).equals(Angle.fromRadians(Math.PI / 4)));
		assertFalse(Angle.fromDegrees(45).equals(Angle.fromRadians(Math.PI / 2)));
		assertFalse(Angle.fromDegrees(45).equals("a dog"));
	}

}

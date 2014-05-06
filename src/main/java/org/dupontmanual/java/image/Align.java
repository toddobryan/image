package org.dupontmanual.java.image;

public enum Align {
	TOP_LEFT(XAlign.LEFT, YAlign.TOP),
	TOP(XAlign.CENTER, YAlign.TOP),
	TOP_RIGHT(XAlign.RIGHT, YAlign.TOP),
	LEFT(XAlign.LEFT, YAlign.CENTER),
	CENTER(XAlign.CENTER, YAlign.CENTER),
	RIGHT(XAlign.RIGHT, YAlign.CENTER),
	BOTTOM_LEFT(XAlign.LEFT, YAlign.BOTTOM),
	BOTTOM(XAlign.CENTER, YAlign.BOTTOM),
	BOTTOM_RIGHT(XAlign.RIGHT, YAlign.BOTTOM);
	
	public final XAlign xAlign;
	public final YAlign yAlign;
	
	private Align(XAlign xAlign, YAlign yAlign) {
		this.xAlign = xAlign;
		this.yAlign = yAlign;
	}
}

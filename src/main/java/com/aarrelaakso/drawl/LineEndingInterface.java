package com.aarrelaakso.drawl;

/**
 * Sets the public interface for line ending types.
 */
public interface LineEndingInterface {

    void setFill(String fillColor);

    /**
     * Sets the size of this LineEnding as a proportion of the default size.
     *
     * @param size the proportion of the default size to which to set the size of this LineEnding
     */
    void setSize(double size);
}

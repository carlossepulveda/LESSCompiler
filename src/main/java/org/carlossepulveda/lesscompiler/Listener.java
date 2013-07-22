package org.carlossepulveda.lesscompiler;

/**
 *
 * @author Carlos Sepulveda
 */
public interface Listener {

    public void onStart();

    public void onFinish();

    public void print(String text);
}

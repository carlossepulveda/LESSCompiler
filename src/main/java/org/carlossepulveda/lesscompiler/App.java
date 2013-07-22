package org.carlossepulveda.lesscompiler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        LESSProcessor processor = new LESSProcessor("/input/folder/less/", "/output/folder/");
        processor.start();
    }
}

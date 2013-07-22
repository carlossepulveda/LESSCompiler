package org.carlossepulveda.lesscompiler;

import java.io.File;
import org.lesscss.LessCompiler;

/**
 *
 * @author Carlos Sepulveda
 */
public class LESSProcessor implements Runnable{

    private Listener listener;

    private String folderOut;

    private String folderIn;

    public LESSProcessor(String folderIn, String folderOut, Listener listener) {
        this.folderIn = folderIn;
        this.folderOut = folderOut;
        this.listener = listener;
    }

    public LESSProcessor(String folderIn, String folderOut) {
        this.folderIn = folderIn;
        this.folderOut = folderOut;
    }

    public void process() {
        onStart();
        createFolder(folderOut);
        File folder = new File(folderIn);
        processFolder(folder, folderOut);
        onFinish();
    }

    private void processFolder(File folder, String output) {
        createFolder(output);
        for (File f : folder.listFiles()) {
            if(f.isDirectory()) {
                processFolder(f, output +f.getName() + "/");
            } else {
                processFile(f, output);
            }
        }
    }

    private void processFile(File file, String output) {
        String inputFile = file.getAbsolutePath();
        String outputFile = output + file.getName().replace(".less", ".css");
        LessCompiler lessCompiler = new LessCompiler();
        try {
            lessCompiler.compile(new File(inputFile), new File(outputFile));
            print(file.getAbsolutePath() + " - Processed");
        } catch (Exception e) {
            print("*** Error trying to process file " + file.getAbsolutePath() + e.getMessage());
        }
    }

    private void onStart() {
        if (listener != null) {
            listener.onStart();;
        }
        print("======== Starting ... ========");
    }

    private void onFinish() {
        if (listener != null) {
            listener.onFinish();;
        }
        print("======== Finished ========");
    }

    private void createFolder(String path) {
        File newFolder = new File(path);
        newFolder.mkdir();
    }

    private void print(String msg) {
        if (listener != null) {
            listener.print(msg);
        } else {
            System.out.println(msg);
        }
    }

    public void start() {
        Thread thread = (new Thread(this));
        thread.start();
    }

    public void run() {
        this.process();
    }
}

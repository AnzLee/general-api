/**
 * @author li.liangzhe
 * @create 2018-01-02 17:20
 **/
package com.anzlee.generalapi.coder.loader;

import com.anzlee.generalapi.util.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class FileClassLoader extends ClassLoader {
    private final static Log logger = LogFactory.getLog(FileClassLoader.class);

    private final static String ext = "java.ext.dirs";
    private final static String cp = "java.class.path";

    private ClassLoader parent = null; // parent classloader
    private String path;

    public FileClassLoader(ClassLoader parent, String path) {
        super(parent);
        this.parent = parent; // 这样做其实是无用的
        this.path = path;
    }

    public FileClassLoader(String path) {
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> cls = findLoadedClass(name);
        if (cls == null) {
            ClassLoader parent2 = getParent().getParent();
            try {
                logger.info("try to use ExtClassLoader to load class : " + name);
                cls = parent2.loadClass(name);
            } catch (ClassNotFoundException e) {
                logger.info("ExtClassLoader.loadClass :" + name + " Failed");
            }
            if (cls == null) {
                logger.info("try to FileClassLoader load class : " + name);
                cls = findClass(name);

                if (cls == null) {
                    logger.info("FileClassLoader.loadClass :" + name + " Failed");
                } else {
                    logger.info("FileClassLoader.loadClass :" + name + " Successful");
                }
            } else {
                logger.info("ExtClassLoader.loadClass :" + name + " Successful");
            }
        }
        return cls;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        return super.findClass(name);
        logger.info("try findClass " + name);
        InputStream is = null;
        Class class1 = null;
        try {
            String classPath = name.replace(".", "\\") + ".class";

            String classFile = path + classPath;
            byte[] data = getClassFileBytes(classFile);

            class1 = defineClass(name, data, 0, data.length);
            if (class1 == null) {
                logger.error("ClassLoaderLK.findClass() ERR ");
                throw new ClassFormatError();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return class1;
    }

    private byte[] getClassFileBytes(String classFile) throws Exception {
        FileInputStream fis = new FileInputStream(classFile);
        FileChannel fileC = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel outC = Channels.newChannel(baos);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            int i = fileC.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();
            outC.write(buffer);
            buffer.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

    public static void loadClassAndInvokeMethod(String className, String methodName, Class<?>... methodParam){
        logger.info("java.ext.dirs :\n" + System.getProperty(ext));
        logger.info("java.class.path :\n" + System.getProperty(cp));
        ClassLoader currentClassloader = FileClassLoader.class.getClassLoader();
        String classDir = SystemUtils.getClassesPath();
        FileClassLoader cl = new FileClassLoader(currentClassloader, classDir);
        logger.info("currentClassloader is " + currentClassloader);
        try {
            Class<?> loadClass = cl.loadClass(className);
            Object object = loadClass.newInstance();
            logger.info(" invoke some method !");
            if(methodParam!=null){
                Method method = loadClass.getMethod(methodName,methodParam);
                method.invoke(object,"1");
            } else {
                Method method = loadClass.getMethod(methodName);
                method.invoke(object);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

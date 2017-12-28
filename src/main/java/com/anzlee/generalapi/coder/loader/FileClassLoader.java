/**
 * FileClassLoader
 *
 * @author li.liangzhe
 * @create 2017-12-26 17:20
 **/
package com.anzlee.generalapi.coder.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class FileClassLoader extends CachedClassLoader{
    private static Log logger = LogFactory.getLog(FileClassLoader.class);
    public String CLASSPATH_ROOT=ClassLoaderFactory.getFactory().getPropertyValue(ClassLoaderFactory.FILEROOT_PATH);

    public FileClassLoader (String path,String classname) {
        super(path, classname);
    }

    /**
     * Implements CachedClassLoader.newClass method.
     * @param classname
     */
    protected  Class<?> newClass(String classname) {
        String fullpath = CLASSPATH_ROOT+ File.separator+this.path+File.separator+classname + ".class";
        logger.debug("loading remote class " + classname + " from "+ fullpath);
        byte data[] = loadClassData(fullpath);
        if (data == null) {
            logger.debug("Class data is null");
            return null;
        }
        logger.debug("defining class " + classname);
        try {
            return super.defineClass(this.path.replaceAll("\\\\", ".")+"."+classname, data, 0, data.length);
        } catch (Exception e) {
            logger.error("Init class exception",e);
        }
        return null;
    }

    /**
     * Read class as a byts array.
     * @return byte[]
     * @param urlString
     */
    private byte[] loadClassData(String urlString) {
        logger.debug("loadClassData by:"+urlString);
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            //return byteOutput.toByteArray();
            in =new FileInputStream(urlString);
            out = new ByteArrayOutputStream();
            FileChannel channel =in.getChannel();
            WritableByteChannel outchannel = Channels.newChannel(out);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = channel.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outchannel.write(buffer);
                buffer.clear();
            }
            byte[] bytes =out.toByteArray();
            out.close();
            in.close();
            return bytes;
        } catch (IOException ie) {
            logger.error("read local file exception "+urlString, ie);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Load spec file from FileClassLoader's  rootpath.
     * @param name resource's name.
     */
    public InputStream getResourceAsStream(String name) {
        String fullpath = CLASSPATH_ROOT+File.separator+this.path+File.separator+name;
        logger.debug("load resource from:"+fullpath);
        try {
            return new FileInputStream(fullpath);
        }
        catch(FileNotFoundException fe) {
            logger.error("spec:"+fullpath,fe);
            return null;
        }
    }
}

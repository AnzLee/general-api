/**
 * ClassLoaderFactory
 *
 * @author li.liangzhe
 * @create 2017-12-26 17:21
 **/
package com.anzlee.generalapi.coder.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ClassLoaderFactory {

    private static Log logger = LogFactory.getLog(ClassLoaderFactory.class);

    public static final String  LOADER_NAME = "classloader.name";
    public static final String  NETROOT_URL = "classloader.NetworkClassLoader";
    public static final String  FILEROOT_PATH = "classloader.FileClassLoader";
    public static final String PREVIOUS_ON_FILE="classloader.FileClassLoader.PRELOAD";

    private Map<String,ClassLoader> loaderMap = null;

    private static ClassLoaderFactory factory = new ClassLoaderFactory();

    public Properties conf = new Properties();

    private ClassLoaderFactory(){
        this.loaderMap = new ConcurrentHashMap<String,ClassLoader>();
        InputStream in = FileClassLoader.class.getResourceAsStream("/*****.properties");
        try {
            conf.load(in);
            logger.debug("ClassLoaderFactory init:"+LOADER_NAME +this.conf.getProperty(LOADER_NAME) );
            logger.debug("ClassLoaderFactory init:"+NETROOT_URL + this.conf.getProperty(NETROOT_URL) );
            logger.debug("ClassLoaderFactory init:"+FILEROOT_PATH  + this.conf.getProperty(FILEROOT_PATH));
        }
        catch(IOException ie) {
            logger.error("Init classpath exception",ie);
        }
    }

    /**
     * Implements factory pattern.
     * @return
     */
    public static ClassLoaderFactory getFactory() {
        return factory;
    }

    protected String getPropertyValue(String propertyName) {
        return this.conf.getProperty(propertyName);
    }
    /**
     * Create new classloader object for this wrapper. default classloader is FileClassLoader.
     * @param key
     * @param classname
     * @return
     */
    public ClassLoader getClassLoader(String key,String classname) {
        long startTime = System.currentTimeMillis();
        String loaderKey = key;
        if(!this.loaderMap.containsKey(loaderKey)){
            synchronized(this.loaderMap) {
                if(this.loaderMap.containsKey(loaderKey)){
                    return (ClassLoader)this.loaderMap.get(loaderKey);
                }
                try {
                    Class<?> cl = Class.forName(this.conf.getProperty(LOADER_NAME));
                    Class<?>[] params = {String.class,String.class};
                    Constructor<?> constructor = cl.getConstructor(params);
                    String[] args = {key,classname};
                    logger.info("create new ClassLoader for:"+key+" classname:"+classname+" consume:"+(System.currentTimeMillis()-startTime)+" (ms)");
                    this.loaderMap.put(loaderKey, (ClassLoader)constructor.newInstance(args));
                }
                catch(ClassNotFoundException cne) {
                    logger.error("init classloader failed. system occure fetal error.!!!"+key+" codename:"+classname, cne);
                }
                catch(NoSuchMethodException nme) {
                    logger.error("key:"+key+" classname:"+classname+ "get classloader failed.",nme);
                }
                catch(Exception e){
                    logger.error("key:"+key+" classname:"+classname+ "get classloader failed.",e);
                }
            }
        }else {
            //(ClassLoader)this.loaderMap.get(loaderKey);
            CachedClassLoader loader =(CachedClassLoader)this.loaderMap.get(loaderKey);
            loader.setClassname(classname);
            logger.debug("retrieve classloader from cache map, key:"+key+" classname:"+classname);
        }
        return (ClassLoader)this.loaderMap.get(loaderKey);
    }

    public void remove(String key){
        if(loaderMap.containsKey(key)){
            synchronized(this.loaderMap) {
                loaderMap.remove(key);
                logger.info("Wrapper classes for key:"+key+ " were removed!");
            }
        }
    }
    public void removeAll() {
        synchronized (this.loaderMap) {
            loaderMap.clear();
            logger.info("Wrapper classes for all key were removed!");
        }
    }

}

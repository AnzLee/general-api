/**
 * CachedClassLoader
 *
 * @author li.liangzhe
 * @create 2017-12-26 17:13
 **/
package com.anzlee.generalapi.coder.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

public abstract class CachedClassLoader extends ClassLoader {
    private final static Log logger = LogFactory.getLog(CachedClassLoader.class);

    protected HashMap<String, Class<?>> cache = null;
    protected String classname;
    protected String path;

    public String getPath() {
        return path;
    }

    public CachedClassLoader(String path, String classname) {
        super();
        this.classname = classname;
        this.path = path;
        this.cache = new HashMap<String, Class<?>>();
    }

    /**
     * Loads the class with the specified name.
     *
     * @param classname: classname.
     */
    public synchronized Class<?> loadClass(String classname, boolean resolve) {
        if (this.cache.containsKey(classname)) {
            logger.debug("load Class:" + classname + " from cache.");
            Class<?> c = this.cache.get(classname);
            if (resolve)
                resolveClass(c);
            return c;
        } else {
            try {
                Class<?> c = Class.forName(classname);
                return c;
            } catch (ClassNotFoundException e) {
                return noClassFound(resolve);
            } catch (NoClassDefFoundError e) {
                return noClassFound(resolve);
            }
        }
    }

    private Class<?> noClassFound(boolean resolve){
        Class<?> c = this.newClass(classname);
        if (c == null)
            return null;
        this.cache.put(classname, c);
        if (resolve)
            resolveClass(c);
        return c;
    }

    public synchronized Class<?> getClass(String classname){
        return this.cache.get(classname);
    }

    /**
     * @return java.lang.Class
     * @param resolve
     */
    public synchronized Class<?> loadClass(boolean resolve) {
        return this.loadClass(this.classname, resolve);
    }

    /**
     * Abstract method for create new class object.
     * @param classname
     * @return
     */
    abstract Class<?> newClass(String classname);

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}

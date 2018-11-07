package com.anzlee.generalapi.third;

import com.anzlee.generalapi.third._${tableName}Entity;
import com.anzlee.generalapi.third._${tableName}Dao;
import com.anzlee.generalapi.base.Page;
import com.anzlee.generalapi.base.Pageable;
import com.anzlee.generalapi.util.SessionFactoryManager;
import com.anzlee.generalapi.util.SpringManager;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
* 描述：${tableName}DAO实现层
* @author anzlee
* @date ${date}
*/
@Repository
public class _${tableName}DaoImpl implements _${tableName}Dao{

    /** Session工厂管理器 */
    SessionFactoryManager sessionFactoryManager = SessionFactoryManager.getSessionFactoryManager();

	/** Session工厂 */
	SessionFactory sessionFactory;

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = obtainSessionFactory(sessionFactory, _${tableName}Entity.class);
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Long save(_${tableName}Entity entity) {
        return (Long)getSession().save(entity);
    }

    public void remove(_${tableName}Entity entity) {
        getSession().delete(entity);
    }

    public _${tableName}Entity find(Long id) {
	    if(id != null){
	        return (_${tableName}Entity)getSession().get(_${tableName}Entity.class, id);
        }else{
	        return null;
	    }
    }

    public _${tableName}Entity find(Long id, LockMode lockMode) {
        if(id != null){
            return (_${tableName}Entity)getSession().get(_${tableName}Entity.class, id, lockMode);
        }else{
            return null;
        }
    }

    public Page<_${tableName}Entity> findPage(Long[] ids) {
        if(ids == null || ids.length == 0){
            return new Page<_${tableName}Entity>();
        }else{
            List<_${tableName}Entity> list = getSession().createQuery("from " + _${tableName}Entity.class.getSimpleName() + " where id in(:ids)").setParameterList("ids",ids).list();
            return new Page<_${tableName}Entity>(list, list.size(), new Pageable());
        }
    }

    public Page<_${tableName}Entity> findPage(Pageable pageable) {
        List<_${tableName}Entity> list = getSession().createQuery("from " + _${tableName}Entity.class.getSimpleName()).setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize()).setMaxResults(pageable.getPageSize()).list();
        return new Page<_${tableName}Entity>(list, list.size(), pageable);
    }

    public Page<_${tableName}Entity> findAll() {
        List<_${tableName}Entity> list = getSession().createQuery("from " + _${tableName}Entity.class.getSimpleName()).list();
        return new Page<_${tableName}Entity>(list, list.size(), new Pageable());
    }

    public long count() {
        return (Long)getSession().createQuery("select count(*) from " + _${tableName}Entity.class.getSimpleName()).uniqueResult();
    }

    public void update(_${tableName}Entity entity) {
        getSession().update(entity);
    }

	public LockMode getLockMode(_${tableName}Entity entity) {
		Assert.notNull(entity);
		return getSession().getCurrentLockMode(entity);
	}

	public void lock(_${tableName}Entity entity, LockMode lockMode) {
		if (entity != null && lockMode != null) {
			getSession().lock(entity, lockMode);
		}
	}

	public void clear() {
        getSession().clear();
	}

	public void flush() {
        getSession().flush();
	}

    public void refresh(_${tableName}Entity entity, LockMode lockMode) {
        if (entity != null) {
            getSession().refresh(entity, lockMode);
        }
    }

    public void refresh(_${tableName}Entity entity) {
        if (entity != null) {
            getSession().refresh(entity);
        }
    }

    public boolean isManaged(_${tableName}Entity entity){
        Assert.notNull(entity);
        return getSession().contains(entity);
    }

    public _${tableName}Entity merge(_${tableName}Entity entity){
        Assert.notNull(entity);
        return (_${tableName}Entity) getSession().merge(entity);
    }

    public Long getIdentifier(_${tableName}Entity entity){
        Assert.notNull(entity);
        return (Long) getSession().getIdentifier(entity);
    }

    public void persist(_${tableName}Entity entity){
        Assert.notNull(entity);
        getSession().persist(entity);
    }

    private SessionFactory obtainSessionFactory(SessionFactory sessionFactory, Class<?> entityClass){
        Map<String,ClassMetadata> map = sessionFactory.getAllClassMetadata();
        Set<String> set = map.keySet();
        if(!set.contains(entityClass.getName())){
            for(SessionFactory factory : sessionFactoryManager.getSessionFactoryList()){
                Set<String> existSet = factory.getAllClassMetadata().keySet();
                if(existSet.contains(entityClass.getName())){//该sessionFactory包含了此实体，就用该SessionFactory
                    return factory;
                }
            }
            Object o2 = SpringManager.getBean("sessionFactory");
            SessionFactoryImpl localSessionFactoryBean = (SessionFactoryImpl) o2;
            Properties properties = localSessionFactoryBean.getProperties();

            ServiceRegistry serviceRegistry =  new StandardServiceRegistryBuilder().applySettings(properties).build();
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(entityClass)//User是qizhong 一个实体类
                    .buildMetadata();
            SessionFactory newSessionFactory = metadata.getSessionFactoryBuilder().build();

            sessionFactoryManager.addSessionFactory(newSessionFactory);
            return newSessionFactory;
        }else{
            return sessionFactory;
        }
    }

    public void validate(){
        System.out.println("_${tableName}DaoImpl has been Loaded");
    }
}
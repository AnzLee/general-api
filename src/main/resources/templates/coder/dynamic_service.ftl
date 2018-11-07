package com.anzlee.generalapi.third;

import com.anzlee.generalapi.base.Page;
import com.anzlee.generalapi.base.Pageable;
import com.anzlee.generalapi.base.annotation.Ignore.Default;
import com.anzlee.generalapi.third._${tableName}Entity;

import java.util.List;

/**
* 描述：${tableName}服务层接口
* @author anzlee
* @date ${date}
*/
public interface _${tableName}Service {

    /**
    * 查找实体对象
    *
    * @param id
    *            Long
    * @return 实体对象，若不存在则返回null
    */
    _${tableName}Entity find(Long id);

    /**
    * 查找所有实体对象集合
    *
    * @return 所有实体对象集合
    */
    Page<_${tableName}Entity> findAll();

    /**
    * 查找实体对象集合
    *
    * @param ids
    *            Long
    * @return 实体对象集合
    */
    @SuppressWarnings("unchecked")
    Page<_${tableName}Entity> findList(Long... ids);

    /**
    * 查找实体对象分页
    *
    * @param pageable
    *            分页信息
    * @return 实体对象分页
    */
    Page<_${tableName}Entity> findPage(Pageable pageable);

    /**
    * 查询实体对象总数
    *
    * @return 实体对象总数
    */
    long count();

    /**
    * 判断实体对象是否存在
    *
    * @param id
    *            ID
    * @return 实体对象是否存在
    */
    boolean exists(Long id);

    /**
    * 保存实体对象
    *
    * @param entity
    *            实体对象
    */
    _${tableName}Entity save(_${tableName}Entity entity);

    /**
    * 保存实体对象集合
    *
    * @param entities
    *            实体对象集合
    */
    void save(List<_${tableName}Entity> entities);

    /**
    * 更新实体对象
    *
    * @param entity
    *            实体对象
    * @return 实体对象
    */
    _${tableName}Entity update(_${tableName}Entity entity);

    /**
    * 更新实体对象集合
    *
    * @param entities
    *            实体对象集合
    */
    void update(List<_${tableName}Entity> entities);

    /**
    * 更新实体对象
    *
    * @param entity
    *            实体对象
    * @param ignoreGroup
    *            更新过滤组
    * @return 实体对象
    */
    _${tableName}Entity update(_${tableName}Entity entity, Class<? extends Default> ignoreGroup);

    /**
    * 更新实体对象集合
    *
    * @param entities
    *            实体对象集合
    * @param ignoreGroup
    *            更新过滤组
    */
    void update(List<_${tableName}Entity> entities, Class<? extends Default> ignoreGroup);

    /**
    * 删除实体对象
    *
    * @param id
    *            Long
    */
    void delete(Long id);

    /**
    * 删除实体对象
    *
    * @param ids
    *            Long
    */
    @SuppressWarnings("unchecked")
    void delete(Long... ids);

    /**
    * 删除实体对象
    *
    * @param entity
    *            实体对象
    */
    void delete(_${tableName}Entity entity);

}

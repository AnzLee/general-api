package com.anzlee.generalapi.third;

import com.anzlee.generalapi.base.Page;
import com.anzlee.generalapi.base.Pageable;
import com.anzlee.generalapi.third._${tableName}Entity;
import org.hibernate.LockMode;

/**
* 描述：${tableName}DAO层
* @author anzlee
* @date ${date}
*/
public interface _${tableName}Dao {

	/**
	 * 保存实体类
	 * 
	 * @param entity
	 * 			实体对象
	 * @return 保存实体对象
	 */
	Long save(_${tableName}Entity entity);
	
	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            Long
	 * @return 实体对象，若不存在则返回null
	 */
    _${tableName}Entity find(Long id);

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            Long
	 * @param lockMode
	 *            锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
    _${tableName}Entity find(Long id, LockMode lockMode);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<_${tableName}Entity> findPage(Pageable pageable);

	/**
	 * 查找实体对象分页
	 *
	 * @param ids
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<_${tableName}Entity> findPage(Long[] ids);

	/**
	 * 查找全部的实体
	 *
	 * @return 全部的实体
	 */
	Page<_${tableName}Entity> findAll();

	/**
	 * 查询实体对象数量
	 * 
	 * @return 实体对象数量
	 */
	long count();

	/**
	 * 移除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void remove(_${tableName}Entity entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void refresh(_${tableName}Entity entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockMode
	 *            锁定方式
	 */
	void refresh(_${tableName}Entity entity, LockMode lockMode);

	/**
	 * 获取锁定方式
	 * 
	 * @param entity
	 *            实体对象
	 */
	LockMode getLockMode(_${tableName}Entity entity);

	/**
	 * 锁定实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockMode
	 *            锁定方式
	 */
	void lock(_${tableName}Entity entity, LockMode lockMode);

	/**
	 * 清除缓存
	 * <pre>
	 * 全部整个jpa环境
	 * </pre>
	 */
	void clear();

	/**
	 * 同步数据
	 * <pre>
	 * 全部整个jpa环境
	 * </pre>
	 */
	void flush();

	/**
	 * 判断是否为托管状态
	 *
	 * @param entity
	 *            实体对象
	 * @return 是否为托管状态
	 */
	boolean isManaged(_${tableName}Entity entity);

	/**
	 * 合并实体对象
	 *
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
    _${tableName}Entity merge(_${tableName}Entity entity);

	/**
	 * 获取实体对象ID
	 *
	 * @param entity
	 *            实体对象
	 * @return 实体对象ID
	 */
	Long getIdentifier(_${tableName}Entity entity);

	/**
	 * 持久化实体对象
	 *
	 * @param entity
	 *            实体对象
	 */
	void persist(_${tableName}Entity entity);

}
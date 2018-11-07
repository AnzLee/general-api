package com.anzlee.generalapi.third;

import com.anzlee.generalapi.base.Page;
import com.anzlee.generalapi.base.Pageable;
import com.anzlee.generalapi.base.annotation.Ignore;
import com.anzlee.generalapi.base.annotation.Ignore.Default;
import com.anzlee.generalapi.base.util.IgnoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

import com.anzlee.generalapi.third._${tableName}Entity;
import com.anzlee.generalapi.third._${tableName}Service;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class _${tableName}ServiceImpl implements _${tableName}Service {

	/** baseDao */
	@Autowired
	protected _${tableName}Dao baseDao;

	@Transactional(readOnly = true)
	public _${tableName}Entity find(Long id) {
		return baseDao.find(id);
	}

	@Transactional(readOnly = true)
	public Page<_${tableName}Entity> findAll() {
		return baseDao.findAll();
	}

	@Transactional(readOnly = true)
	public Page<_${tableName}Entity> findPage(Pageable pageable) {
		return baseDao.findPage(pageable);
	}

	@Transactional(readOnly = true)
	public Page<_${tableName}Entity> findList(Long... ids) {
		return baseDao.findPage(ids);
	}

	@Transactional(readOnly = true)
	public long count() {
		return baseDao.count();
	}

	@Transactional(readOnly = true)
	public boolean exists(Long id) {
		return baseDao.find(id) != null;
	}

	@Transactional
	public _${tableName}Entity save(_${tableName}Entity entity) {
		Assert.notNull(entity);
		Assert.isTrue(entity.isNew());

		baseDao.persist(entity);
		return entity;
	}

	@Transactional
	public void save(List<_${tableName}Entity> entities) {
		for (_${tableName}Entity entity : entities) {
			save(entity);
		}
	}

	@Transactional
	public _${tableName}Entity update(_${tableName}Entity entity) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());

//		if (!baseDao.isLoaded(entity)) {
            _${tableName}Entity persistant = baseDao.find(baseDao.getIdentifier(entity));
			if (persistant != null) {
				IgnoreUtils.copyProperties(entity, persistant, Ignore.Default.class);
			}
			return baseDao.merge(persistant);
//		}
//		return baseDao.merge(entity);
	}

	@Transactional
	public void update(List<_${tableName}Entity> entities) {
		for (_${tableName}Entity entity : entities) {
			update(entity);
		}
	}

	@Transactional
	public _${tableName}Entity update(_${tableName}Entity entity, Class<? extends Default> ignoreGroup) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());
		Assert.isTrue(!baseDao.isManaged(entity));
        _${tableName}Entity persistant = baseDao.find(baseDao.getIdentifier(entity));
		if (persistant != null) {
			if (!persistant.getVersion().equals(entity.getVersion())) {
				throw new ObjectOptimisticLockingFailureException(entity.getClass(), entity);
			}
			IgnoreUtils.copyProperties(entity, persistant, ignoreGroup);
		} else {
			throw new IllegalArgumentException("Entity must not be managed");
		}
		return update(persistant);
	}

	@Transactional
	public void update(List<_${tableName}Entity> entities, Class<? extends Default> ignoreGroup) {
		for (_${tableName}Entity entity : entities) {
			update(entity, ignoreGroup);
		}
	}

	@Transactional
	public void delete(Long id) {
		delete(baseDao.find(id));
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void delete(Long... ids) {
		if (ids != null) {
			for (Long id : ids) {
				delete(baseDao.find(id));
			}
		}
	}

	@Transactional
	public void delete(_${tableName}Entity entity) {
		if (entity != null) {
			baseDao.remove(baseDao.isManaged(entity) ? entity : baseDao.merge(entity));
		}
	}

    public void validate(){
        System.out.println("_${tableName}ServiceImpl has been Loaded");
    }

}
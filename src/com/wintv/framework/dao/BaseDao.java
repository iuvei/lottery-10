package com.wintv.framework.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;

import com.wintv.framework.exception.DaoException;
import com.wintv.framework.page.Page;


public interface BaseDao<T, PK extends Serializable> {

    T read(PK id);
    
    List<T> read(PK[] ids);
    
    List<T> readAll();

    void store(T object);
    
    void storeAll(Collection<T> persistentObjects);
    @Deprecated
    int updateBySQL(String sql);
    
    void delete(PK id);
    
    void delete(PK[] ids);

    void delete(T persistentObject);
    
    void delete(Collection<T> persistentObjects);
    
    T findUniqueBy(String name, Object value);
    
    List<T> findBy(String name, Object value);
    
    List<T> findByLike(String name, String value);
    
    Page pagedQuery(String queryString, int pageNo, int pageSize);
    
    Page pagedQuery(String queryString, Object[] args, int pageNo,int pageSize);
    
    Page pagedQueryWithQueryCache(String queryString, int pageNo, int pageSize,String cacheRegion);
    
    Page pagedQueryWithQueryCache(String queryString, Object[] args, int pageNo,int pageSize,String cacheRegion);
    
    List listedQuery(String queryString, Object[] args);
    
    List listedQueryWithCache(String queryString, Object[] args,String cacheRegion);
    public Long saveObject(Object o)throws DaoException;
    public boolean updateBySql(String sql);
  

}

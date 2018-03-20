package com.example.demo.core.repository;



import com.example.demo.core.util.Pagination;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
  
/**  
 *
 * @author jensen 
 * @description  mongo 公共方法
 * @date 2018/3/19 14:28
 * @param
 * @return 
 */ 
public interface BaseMongoRepository<T, ID extends Serializable> {  
  
    /** 
     * 通过条件查询实体(集合) 
     *  
     * @param query 
     */  
	List<T> find(Query query) ;
	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	List<T> findAll();
  
    /** 
     * 通过一定的条件查询一个实体 
     *  
     * @param query 
     * @return 
     */  
    T findOne(Query query) ;
  
    /**
	 * 查询并且修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	T findAndModify(Query query, Update update) ;
	
	/**
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	T findAndRemove(Query query);
  
	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	void updateFirst(Query query, Update update);
	/**
	 * 更新或保存
	 * @param query
	 * @param update
	 * @param collectionName
	 * @return
	 */
	WriteResult upsert(Query query, Update update, String collectionName);
	/**
	 * 更新或保存
	 * @param query
	 * @param update
	 * @param entityClass
	 * @param collectionName
	 * @param <S>
	 * @return
	 */
	<S> WriteResult upsert(Query query, Update update, Class<S> entityClass, String collectionName);
    /**
     * 保存一个对象到mongodb 
     *  
     * @param entity 
     * @return 
     */  
    Object save(Object entity) ;
  
    /** 
     * 通过ID获取记录 
     *  
     * @param id 
     * @return 
     */  
    T findById(String id) ;
  
    /** 
     * 通过ID获取记录,并且指定了集合名(表的意思) 
     *  
     * @param id 
     * @param collectionName 
     *            集合名 
     * @return 
     */  
    T findById(String id, String collectionName) ;
      
    /**
	 * 通过条件查询,查询分页结果
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	Pagination<T> getPage(int pageNo, int pageSize, Query query);
      
	/**
	 * 
	 * @param <S>
	 * @Description:  通过表名和查询条件获取结果
	 * @param query
	 * @param collectionName
	 * @return   
	 * @return List<S>  
	 * @throws
	 * @author jensen
	 * @date 2016年1月22日 下午1:06:49
	 */
	<S> List<S> find(Query query, String collectionName, Class<S> entityClass);
	
	/**
	 * 通过表明和条件查询,查询分页结果
	 * @param <S>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	<S> Pagination<S> getPage(int pageNo, int pageSize, Query query, String collectionName, Class<S> entityClass);
	
	/**
	 * 
	 * @param <C>
	 * @Description: TODO 根据表名保存
	 * @param collectionName
	 * @return void  
	 * @throws
	 * @author jensen
	 * @date 2016年1月23日 上午11:46:49
	 */
	<C> C save(C entity, String collectionName);
	/**
	 * 根据表名保存对象
	 */
	Object saveObject(Object object, String collectionName);
	/**
	 * 根据表名实体类查找对象
	 */
	<S> S findById(String id, Class<S> entityClass, String collectionName);
	
	/**
	 * 按条件实体类表名查询,并且删除记录
	 * @param <S>
	 * 
	 * @param query
	 * @return
	 */
	<S> S findAndRemove(Query query, Class<S> entityClass, String collectionName);
	/**
	 * 根据表名实体类获得总数
	 */
	long count(Query query, String collectionName);
	/**
	 * 根据表名批量更新
	 */
	WriteResult updateMulti(Query query, Update update, String collectionName);
	/**
	 * 根据表名更新
	 */
	<S> WriteResult updateMulti(Query query, Update update, Class<S> entityClass, String collectionName);
	/**
	 * 根据表名批量插入
	 */
	void insert(Collection<? extends Object> batchToSave, String collectionName);

	WriteResult remove(Query query, Class<?> entityClass);
	/**
	 * 聚合查询
	 */
	AggregationResults<?> aggregate(Aggregation aggregation, String collectionName, Class<?> entityClass);
	
	/**
	 * 获取文档
	 */
	DBCollection getCollection(String collectionName);
	/**
	 * 分页
	 */
	<S> Pagination<S> getPagination(String collectionName, Class<S> entityClass, int pageNo, int pageSize, List<Criteria> cs);
	/**
	 * 分页
	 */
	<S> Pagination<S> getPaginationByUnwind(String collectionName, Class<S> entityClass, int pageNo, int pageSize, List<Criteria> cs);
	/**
	 * 数量查询
	 */
	long count(Query query, Class<?> entityClass, String collectionName);
	/**
	 * 原生sql操作
	 * @param jsonCommand
	 * @return
	 */
	BasicDBList executeCommand(String jsonCommand);
}


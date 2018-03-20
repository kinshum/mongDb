package com.example.demo.core.repository.impl;


import com.example.demo.core.repository.BaseMongoRepository;
import com.example.demo.core.util.Pagination;
import com.example.demo.core.util.Reflections;
import com.mongodb.BasicDBList;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author jensen
 * @description   Mongodb公共方法实现CRUD
 * @date 2018/3/19 16:59
 * @param
 * @return
 */
@Component
@Transactional
public class BaseMongoRepositoryImpl<T, ID extends Serializable>
		implements BaseMongoRepository<T, ID> {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	private Class<T> getEntityClass(){
		return Reflections.getClassGenricType(getClass());
	}  
	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	private <S> Class<S> getEntityClass(Class<S> clazz){
		return Reflections.getClassGenricType(clazz);
	}  
	/**
	 * spring mongodb　集成操作类　
	 */


	/**
	 * 通过条件查询实体(集合)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}
	
	/**
	 * 查询出所有数据
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}
	
	/**
	 * 查询并且修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update) {

		return this.mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	/**
	 * 按条件查询,并且删除记录
	 * 
	 * @param query
	 * @return
	 */
	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	/**
	 * 更新或保存
	 * @param query
	 * @param update
	 * @param collectionName
     * @return
     */
	public WriteResult upsert(Query query, Update update, String collectionName){
		return  mongoTemplate.upsert(query, update, collectionName);
	}
	/**
	 * 更新或保存
	 * @param query
	 * @param update
	 * @param entityClass
	 * @param collectionName
	 * @param <S>
     * @return
     */
	public <S> WriteResult upsert(Query query, Update update, Class<S> entityClass, String collectionName){
		return  mongoTemplate.upsert(query, update, entityClass, collectionName);
	}

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param entity
	 * @return
	 */
	public Object save(Object entity) {
		mongoTemplate.save(entity);
		return entity;
	}
	
	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	/**
	 * 通过ID获取记录,并且指定了集合名(表的意思)
	 * 
	 * @param id
	 * @param collectionName
	 *            集合名
	 * @return
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}

	/**
	 * 通过条件查询,查询分页结果
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public Pagination<T> getPage(int pageNo, int pageSize, Query query) {
		long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		List<T> datas = this.find(query);
		page.setDatas(datas);
		return page;
	}
	/**
	 * 
	 * @param <S>
	 * @Description:  通过表名和查询条件获取结果
	 * @param query
	 * @param collectionName
	 * @return   
	 * @return List<T>  
	 * @throws
	 * @author jensen
	 * @date 2016年1月22日 下午1:06:49
	 */
	public <S> List<S> find(Query query,String collectionName,Class<S> entityClass ){
		return mongoTemplate.find(query, this.getEntityClass(entityClass), collectionName);
	};
	/**
	 * 通过表明和条件查询,查询分页结果
	 * @param <S>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public <S> Pagination<S> getPage(int pageNo, int pageSize, Query query,String collectionName,Class<S> entityClass) {
		long totalCount = mongoTemplate.count(query, collectionName);
		Pagination<S> page = new Pagination<S>(pageNo, pageSize, totalCount);
		query.skip(page.getFirstResult());// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		List<S> datas = this.find(query,collectionName, entityClass);
		page.setDatas(datas);
		return page;
	}

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
	public <C> C save(C entity,String collectionName){
		mongoTemplate.save(entity, collectionName);
		return entity;
	}
	/**
	 * 保存对象
	 */
	public Object saveObject(Object object,String collectionName){
		mongoTemplate.save(object, collectionName);
		return object;
	}
	
	/**
	 * 根据id实体类和表名查找对象
	 */
	public <S> S findById(String id, Class<S> entityClass,String collectionName){
		return mongoTemplate.findById(id, this.getEntityClass(entityClass), collectionName);
	}
	
	
	/**
	 * 按条件实体类表名查询,并且删除记录
	 * @param <S>
	 * 
	 * @param query
	 * @return
	 */
	public <S> S findAndRemove(Query query,Class<S> entityClass,String collectionName) {
		
		return mongoTemplate.findAndRemove(query, entityClass, collectionName);
	}
	/**
	 * 根据表名实体类获得总数
	 */
	public long count(Query query,String collectionName){
		return mongoTemplate.count(query, collectionName);
	}
	/**
	 * 根据表名批量更新
	 */
	public WriteResult updateMulti(Query query,Update update,String collectionName){
		return mongoTemplate.updateMulti(query, update, collectionName);
	}
	/**
	 * 批量更新
	 */
	public <S> WriteResult updateMulti(Query query,Update update,Class<S> entityClass,String collectionName){
		return mongoTemplate.updateMulti(query, update, entityClass, collectionName);
	}
	/**
	 * 批量添加
	 */
	public void insert(Collection<? extends Object> batchToSave, String collectionName){
		mongoTemplate.insert(batchToSave, collectionName);
	}

	public WriteResult remove(Query query, Class<?> entityClass) {
		return mongoTemplate.remove(query,entityClass);
	}
	/**
	 * 聚合查询
	 */
	public AggregationResults<?> aggregate(Aggregation aggregation, String collectionName,Class<?> entityClass) {
		return mongoTemplate.aggregate(aggregation, collectionName, entityClass);
	}
	/**
	 * 获取文档
	 */
	public DBCollection getCollection( String collectionName) {
		DBCollection collection = mongoTemplate.getCollection(collectionName);
		return collection;
	}
	/**
	 * 数量查询
	 */
	public long count(Query query,Class<?> entityClass,String collectionName) {
		return mongoTemplate.count(query, entityClass, collectionName);
	}
	/**
	 * 分页
	 */
	public <S> Pagination<S> getPagination(String collectionName,Class<S> entityClass,int pageNo,int pageSize,List<Criteria> cs){
		if(cs==null) return null;
		Query query = new Query();//查询条件
		List<AggregationOperation> aos = new ArrayList<AggregationOperation>();//聚合条件
		for (Criteria criteria : cs) {
			query.addCriteria(criteria);
			AggregationOperation match = Aggregation.match(criteria);
			aos.add(match);
		}
		//aos.add(Aggregation.sort(Direction.DESC, "modifiedTime"));	//修改时间：暂时写死字段	
		//aos.add(Aggregation.sort(Direction.DESC, "modifiedTime").and(Direction.ASC, "qusers.isView"));//未读升序
		aos.add(Aggregation.sort(Direction.ASC, "qusers.isView").and(Direction.DESC, "modifiedTime"));//未读升序
		aos.add(Aggregation.skip((pageNo-1)*pageSize));	
		aos.add(Aggregation.limit(pageSize));
		Aggregation aggregation = Aggregation.newAggregation(aos);
		AggregationResults<S> result = this.mongoTemplate.aggregate(aggregation, collectionName, entityClass);
		List<S> datas = result.getMappedResults();
		//总个数查询
		long totalCount = this.mongoTemplate.count(query, entityClass);
		Pagination<S> page = new Pagination<S>(pageNo, pageSize, totalCount);
		page.setDatas(datas);
		return page;
	}
	/**
	 * 分页
	 */
	public <S> Pagination<S> getPaginationByUnwind(String collectionName,Class<S> entityClass,int pageNo,int pageSize,List<Criteria> cs){
		if(cs==null) return null;
		Query query = new Query();//查询条件
		List<AggregationOperation> aos = new ArrayList<AggregationOperation>();//聚合条件
		for (Criteria criteria : cs) {
			query.addCriteria(criteria);
			AggregationOperation match = Aggregation.match(criteria);
			aos.add(match);
		}
		//aos.add(Aggregation.unwind("qusers"));
		//aos.add(Aggregation.match());
		aos.add(Aggregation.sort(Direction.ASC, "qusers.isView").and(Direction.DESC, "modifiedTime"));//未读升序
		aos.add(Aggregation.skip((pageNo-1)*pageSize));	
		aos.add(Aggregation.limit(pageSize));
		//aos.add(Aggregation.unwind("qusers"));
		
		Aggregation aggregation = Aggregation.newAggregation(aos);
		AggregationResults<S> result = this.mongoTemplate.aggregate(aggregation, collectionName, entityClass);
		List<S> datas = result.getMappedResults();
		//总个数查询
		long totalCount = this.mongoTemplate.count(query, entityClass);
		Pagination<S> page = new Pagination<S>(pageNo, pageSize, totalCount);
		page.setDatas(datas);
		return page;
	}

	/**
	 * 原生sql操作
	 * @param jsonCommand
	 * @return
     */
	public BasicDBList executeCommand(String jsonCommand){
		CommandResult commandResult = mongoTemplate.executeCommand(jsonCommand);
		BasicDBList list = (BasicDBList)commandResult.get("values");
		return list;
	}
	
}

package com.masters.application.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.application.model.Word;
import com.masters.utilities.logging.Log;


@Service("WordService")
@Transactional("appTransactionManager")
public class WordServiceImpl implements WordService {
	@Autowired
	@Qualifier("appSessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Word findWordById(String id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Word word = new Word();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_details_by_id (:id)")
					.setParameter("id", id);
			procedure.setResultTransformer(Transformers.aliasToBean(Word.class));		
			word = (Word) procedure.uniqueResult();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}		
		return word;
	}

	@Override
	public Word findWordByName(String name) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Word word = new Word();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_details_by_name (:name)")
					.setParameter("name", name);
			procedure.setResultTransformer(Transformers.aliasToBean(Word.class));		
			word = (Word) procedure.uniqueResult();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}
		return word;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Word> findWordsByCategory(String userId, String category) {		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Word> words = new ArrayList<Word>();
		try {
			transaction = session.beginTransaction();
			Query procedure = session.createSQLQuery("call sp_get_words_by_category (:user_id, :category)")
					.setParameter("user_id", Integer.parseInt(userId)).setParameter("category", category);				
			procedure.setResultTransformer(Transformers.aliasToBean(Word.class));		
			words = procedure.list();			
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			if (session.isOpen()) session.close();
			Log.e(e);			
		}	
		return words;
	}
}

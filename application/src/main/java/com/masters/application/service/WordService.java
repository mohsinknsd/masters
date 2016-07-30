package com.masters.application.service;

import java.util.List;

import com.masters.application.model.Word;

public interface WordService {
	Word findWordById(String id);
	Word findWordByName(String name);
	List<Word> findWordsByCategory(String userId, String category);
}

package com.masters.application.model;

public class Word {
	private long id;
	private String name;
	private String description;
	private String translation;
	private String pronunciation;
	private String key;
	private String method;
	private String synonyms;
	private String antonyms;
	private String image;
	private String category;
	private int level;
	
	public synchronized int getLevel() {
		return level;
	}
	public synchronized void setLevel(int level) {
		this.level = level;
	}
	public synchronized long getId() {
		return id;
	}
	public synchronized void setId(long id) {
		this.id = id;
	}
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
	public synchronized String getDescription() {
		return description;
	}
	public synchronized void setDescription(String description) {
		this.description = description;
	}
	public synchronized String getTranslation() {
		return translation;
	}
	public synchronized void setTranslation(String translation) {
		this.translation = translation;
	}
	public synchronized String getPronunciation() {
		return pronunciation;
	}
	public synchronized void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	public synchronized String getKey() {
		return key;
	}
	public synchronized void setKey(String key) {
		this.key = key;
	}
	public synchronized String getMethod() {
		return method;
	}
	public synchronized void setMethod(String method) {
		this.method = method;
	}
	public synchronized String getSynonyms() {
		return synonyms;
	}
	public synchronized void setSynonyms(String synonyms) {
		this.synonyms = synonyms;
	}
	public synchronized String getAntonyms() {
		return antonyms;
	}
	public synchronized void setAntonyms(String antonyms) {
		this.antonyms = antonyms;
	}
	public synchronized String getImage() {
		return image;
	}
	public synchronized void setImage(String image) {
		this.image = image;
	}
	public synchronized String getCategory() {
		return category;
	}
	public synchronized void setCategory(String category) {
		this.category = category;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (id != other.id)
			return false;
		return true;
	}	
	@Override
	public String toString() {
		return "Word [id=" + id + ", name=" + name + ", description="
				+ description + ", translation=" + translation
				+ ", pronunciation=" + pronunciation + ", key=" + key
				+ ", method=" + method + ", synonyms=" + synonyms
				+ ", antonyms=" + antonyms + ", image=" + image + ", category="
				+ category + "]";
	}	
}
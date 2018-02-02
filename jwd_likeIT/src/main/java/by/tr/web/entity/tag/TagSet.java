package by.tr.web.entity.tag;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TagSet {

	private Map<String, Integer> tagToIdSet = new HashMap<String, Integer>();
	private BiMap<String, String> caseNormalizationMapping = HashBiMap.create();

	TagSet() {

	}

	public Map<String, Integer> getTagToIdSet() {
		return tagToIdSet;
	}

	public void setTagToIdSet(Map<String, Integer> tagToIdSet) {
		this.tagToIdSet = tagToIdSet;
	}

	public BiMap<String, String> getCaseNormalizationMapping() {
		return caseNormalizationMapping;
	}

	public void setCaseNormalizationMapping(BiMap<String, String> caseNormalizationMapping) {
		this.caseNormalizationMapping = caseNormalizationMapping;
	}

	public Set<String> getTagSet() {
		return caseNormalizationMapping.values();
	}

	public boolean addTag(String tag, int id) {
		String lowerCaseTag = tag.toLowerCase();
		if (tagToIdSet.containsKey(lowerCaseTag)) {
			return false;
		}
		tagToIdSet.put(lowerCaseTag, id);
		caseNormalizationMapping.put(lowerCaseTag, tag);
		return true;
	}

	public boolean removeTag(String tag) {
		String lowerCaseTag = tag.toLowerCase();
		if (!tagToIdSet.containsKey(lowerCaseTag)) {
			return false;
		}
		tagToIdSet.remove(lowerCaseTag);
		caseNormalizationMapping.remove(lowerCaseTag);
		return true;
	}

	public int getTagId(String tag) throws NoSuchElementException {
		String lowerCaseTag = tag.toLowerCase();
		if (tagToIdSet.containsKey(lowerCaseTag)) {
			return tagToIdSet.get(lowerCaseTag);
		} else {
			throw new NoSuchElementException("No tag with name = " + tag);
		}
	}

	public String getTagStandartName(String tag) throws NoSuchElementException {
		String lowerCaseTag = tag.toLowerCase();
		if (caseNormalizationMapping.containsKey(lowerCaseTag)) {
			return caseNormalizationMapping.get(lowerCaseTag);
		} else {
			throw new NoSuchElementException("No tag with name = " + tag);
		}
	}
	
	public void clearTagSet(){
		tagToIdSet.clear();
		caseNormalizationMapping.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseNormalizationMapping == null) ? 0 : caseNormalizationMapping.hashCode());
		result = prime * result + ((tagToIdSet == null) ? 0 : tagToIdSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		TagSet other = (TagSet) obj;
		if (caseNormalizationMapping == null) {
			if (other.caseNormalizationMapping != null) {
				return false;
			}
		} else if (!caseNormalizationMapping.equals(other.caseNormalizationMapping)) {
			return false;
		}
		if (tagToIdSet == null) {
			if (other.tagToIdSet != null) {
				return false;
			}
		} else if (!tagToIdSet.equals(other.tagToIdSet)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "TagSet: tagToIdSet=" + tagToIdSet + ", caseNormalizationMapping=" + caseNormalizationMapping;
	}

}

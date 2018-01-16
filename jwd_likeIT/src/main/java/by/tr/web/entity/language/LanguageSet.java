package by.tr.web.entity.language;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class LanguageSet {

	private Map<String, Integer> languageToIdSet = new HashMap<String, Integer>();
	private BiMap<String, String> caseNormalizationMapping = HashBiMap.create();

	LanguageSet() {

	}

	public Map<String, Integer> getLanguageToIdSet() {
		return languageToIdSet;
	}

	public void setLanguageToIdSet(Map<String, Integer> languageToIdSet) {
		this.languageToIdSet = languageToIdSet;
	}

	public BiMap<String, String> getCaseNormalizationMapping() {
		return caseNormalizationMapping;
	}

	public void setCaseNormalizationMapping(BiMap<String, String> caseNormalizationMapping) {
		this.caseNormalizationMapping = caseNormalizationMapping;
	}
	
	public Set<String> getLanguageSet(){
		return caseNormalizationMapping.values();
	}

	public boolean addLanguage(String language, int id) {
		String lowerCaseLanguage = language.toLowerCase();
		if (languageToIdSet.containsKey(lowerCaseLanguage)) {
			return false;
		}
		languageToIdSet.put(lowerCaseLanguage, id);
		caseNormalizationMapping.put(lowerCaseLanguage, language);
		return true;
	}

	public boolean removeTag(String language) {
		String lowerCaseLanguage = language.toLowerCase();
		if (!languageToIdSet.containsKey(lowerCaseLanguage)) {
			return false;
		}
		languageToIdSet.remove(lowerCaseLanguage);
		caseNormalizationMapping.remove(lowerCaseLanguage);
		return true;
	}

	public int getLanguageId(String language) throws NoSuchElementException {
		String lowerCaseLanguage = language.toLowerCase();
		if (languageToIdSet.containsKey(lowerCaseLanguage)) {
			return languageToIdSet.get(lowerCaseLanguage);
		} else {
			throw new NoSuchElementException("No language with name = " + language);
		}
	}

	public String getLanguageStandartName(String language) throws NoSuchElementException {
		String lowerCaseLanguage = language.toLowerCase();
		if (caseNormalizationMapping.containsKey(lowerCaseLanguage)) {
			return caseNormalizationMapping.get(lowerCaseLanguage);
		} else {
			throw new NoSuchElementException("No language with name = " + language);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseNormalizationMapping == null) ? 0 : caseNormalizationMapping.hashCode());
		result = prime * result + ((languageToIdSet == null) ? 0 : languageToIdSet.hashCode());
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

		LanguageSet other = (LanguageSet) obj;
		if (caseNormalizationMapping == null) {
			if (other.caseNormalizationMapping != null) {
				return false;
			}
		} else if (!caseNormalizationMapping.equals(other.caseNormalizationMapping)) {
			return false;
		}
		if (languageToIdSet == null) {
			if (other.languageToIdSet != null) {
				return false;
			}
		} else if (!languageToIdSet.equals(other.languageToIdSet)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "LanguageSet: languageToIdSet=" + languageToIdSet 
				+ ", caseNormalizationMapping="
				+ caseNormalizationMapping;
	}	
	
}

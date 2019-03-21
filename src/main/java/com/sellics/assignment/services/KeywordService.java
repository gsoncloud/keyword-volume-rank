package com.sellics.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellics.assignment.clients.AmazonAutocompleteClient;
import com.sellics.assignment.dto.KeywordDetailDTO;

@Service
public class KeywordService {

	private final AmazonAutocompleteClient amazonAutocompleteClient;

	@Autowired
	public KeywordService(AmazonAutocompleteClient amazonAutocompleteClient) {
		this.amazonAutocompleteClient = amazonAutocompleteClient;
	}

	/**
	 * Service method to estimate the score for a keyword.
	 * 
	 * @param keyword
	 * @return KeywordDetailDTO with keyword and score.
	 */
	public KeywordDetailDTO getKeywordSearchVolume(String keyword) {
		char[] characters = keyword.toCharArray();
		boolean keywordNotFounded = true;
		Integer characterIndex = 0;
		StringBuilder searchCriteria = new StringBuilder();
		Integer score = 0;

		while (keywordNotFounded) {
			if (characterIndex < characters.length) {
				searchCriteria.append(characters[characterIndex]);
				ArrayList<String> suggestions = amazonAutocompleteClient.searchByKeyword(searchCriteria.toString(),"aps");
				Integer keywordPositionValue = getKeywordPositionValueFromSuggestions(suggestions, keyword,
						characterIndex);

				if (keywordPositionValue > 0) {
					keywordNotFounded = false;
					score = keywordPositionValue;
				}

				characterIndex++;

			} else {
				keywordNotFounded = false;
			}
		}
		
		return new KeywordDetailDTO(keyword, score);
	}
	
	/**
	 * Service method to estimate the score for a keyword.
	 * 
	 * @param keyword
	 * @return KeywordDetailDTO with keyword and score.
	 */
	public void getKeywordSearchVolume(String keyword,String dept) {
		char[] characters = keyword.toCharArray();
		boolean keywordNotFounded = true;
		Integer characterIndex = 0;
		StringBuilder searchCriteria = new StringBuilder();
		Integer score = 0;

		while (keywordNotFounded) {
			if (characterIndex < characters.length) {
				searchCriteria.append(characters[characterIndex]);
				ArrayList<String> suggestions = amazonAutocompleteClient.searchByKeyword(searchCriteria.toString(),dept);
				Integer keywordPositionValue = getKeywordPositionValueFromSuggestions(suggestions, keyword,
						characterIndex);

				if (keywordPositionValue > 0) {
					keywordNotFounded = false;
					score = keywordPositionValue;
				}

				characterIndex++;

			} else {
				keywordNotFounded = false;
			}
		}

//		return new KeywordDetailDTO(keyword, score);
	}

	/**
	 * Search for keyword in suggestions list. If keyword is present, calculate
	 * the score based on number of characters required for keyword to appear in
	 * the result. If keyword is not present return -1.
	 * 
	 * @param suggestions
	 * @param keyword
	 * @param characterIndex
	 * @return Integer value with keyword score if it is present or -1 in case
	 *         keyword is not present.
	 */
	private Integer getKeywordPositionValueFromSuggestions(ArrayList<String> suggestions, String keyword,
			Integer characterIndex) {
		Integer scoreValue = -1;
		int averageLength = getAverageLengthOfSuggestions(suggestions);
		for (int suggestionIndex = 0; suggestionIndex < suggestions.size(); suggestionIndex++) {
			String suggestion = suggestions.get(suggestionIndex);

			if (suggestion.equalsIgnoreCase(keyword)) {

				scoreValue = getCharacterIndexWeight(characterIndex, averageLength);
				break;
			}
		}

		return scoreValue;
	}

	/**
	 * Calculate the average length of all the returned suggestions
	 * 
	 * @param suggestions
	 * @return Integer value with keyword score if it is present or -1 in case
	 *         keyword is not present.
	 */
	private Integer getAverageLengthOfSuggestions(ArrayList<String> suggestions) {
		Integer totalLength = 0;
		Integer averageLength = -1;
		for (int i = 0; i < suggestions.size(); i++) {
			totalLength += suggestions.get(i).length();
		}
		if (totalLength > 0)
			averageLength = totalLength / suggestions.size();

		return averageLength;
	}

	/**
	 * Calculate the weight of the character index in the keyword using the
	 * below method.
	 * 
	 * weight = character index/average length of the resultant keywords
	 * 
	 * @param charactersIndex
	 * @param averageLengthOfSuggestions
	 * @return Integer with the weight of character index of the keyword
	 */
	private Integer getCharacterIndexWeight(float characterIndex, float averageLengthOfSuggestions) {
		float characterValue;
		float totalValue = 100;
		characterValue = ((characterIndex + 1) / averageLengthOfSuggestions) * 100;
		totalValue = 100 - characterValue;

		return Math.round(totalValue);
	}

	public static final List<String> LoadDepartments() {
		List<String> departments = new ArrayList<String>();
		departments.add("warehouse-deals");
		departments.add("appliances");
		departments.add("arts-crafts");
		departments.add("automotive");
		departments.add("baby-products");
		departments.add("automotive");
		departments.add("beauty");
		departments.add("popular");
		departments.add("mobile");
		departments.add("fashion");
		departments.add("computers");
		departments.add("electronics");
		departments.add("toys-and-games");
		departments.add("vehicles");
		return departments;

	}

}

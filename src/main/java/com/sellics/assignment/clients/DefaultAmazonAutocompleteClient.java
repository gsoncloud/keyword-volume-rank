package com.sellics.assignment.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.assignment.exceptions.BusinessException;
import com.sellics.assignment.handlers.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class DefaultAmazonAutocompleteClient implements AmazonAutocompleteClient{

    private final RestTemplate restTemplate;

    private final String AMAZON_AUTOCOMPLETE_AJAX_API = "http://completion.amazon.com/search/complete?search-alias=%2$s&client=amazon-search-ui&mkt=1&q=%1$s";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public DefaultAmazonAutocompleteClient(RestTemplateBuilder restTemplateBuilder, RestTemplateErrorHandler restTemplateErrorHandler) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(restTemplateErrorHandler)
                .build();

    }

    /**
     * Search products by keyword
     * @return a list of search suggestions
     */
    public ArrayList<String> searchByKeyword(String keyword,String dept) {
        String url = buildUrl(keyword,dept);
        String response = restTemplate.getForObject(url, String.class);

        ArrayList<String> results = parseResultOptions(response);

        return results;
    }

    /**
     * Parse Amazon autocomplete AJAX API response from string to array of strings with search suggestions
     * @param searchResult
     * @return
     */
    private ArrayList<String> parseResultOptions(String searchResult) {
        Object[] resultArray = new Object[2];

        try {
            resultArray = objectMapper.readValue(searchResult, Object[].class);
        } catch (IOException ex) {
            throw new BusinessException("Error parsing Amazon Autocomplete AJAX API response: " + searchResult, ex);
        }

        ArrayList<String> searchResults = (ArrayList<String>) resultArray[1];

        return searchResults;
    }

    /**
     * Build URL to call Amazon Autocomplete AJAX API with the keyword to search
     * @param keyword
     * @return
     */
    private String buildUrl(String keyword,String dept) {
        return String.format(AMAZON_AUTOCOMPLETE_AJAX_API, keyword,dept);
    }

}

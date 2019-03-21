package com.sellics.assignment.clients;

import java.util.ArrayList;

@FunctionalInterface
public interface AmazonAutocompleteClient {

    public ArrayList<String> searchByKeyword(String keyword,String dept);
}

# keyword-search-volume-api
API to get estimate keyword search volume from Amazon Autocomplete AJAX API.

The range defined to calculate the search-volume of a keyword is 0-100.

The returned value only represents an approximate integer value for the search-volume of the keyword.

### Getting Started

- Clone repository
- Go to project folder
- Run `mvn spring-boot:run`

## API

### Keyword Detail

Returns the keyword and the score which represents the search-volume of the keyword.

Endpoint:
```
/keyword/{keyword}
```

#### Return

A json object with the following structure:

```
{  
   "keyword":"linux",
   "score":40
}
```

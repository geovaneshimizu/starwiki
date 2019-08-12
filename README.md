# StarWiki

Find out all characters of a Star Wars movie that are from the same species.

This service exposes a HTTP endpoint that, given both parameters `film_id` and `character_id`, consults the 
[*swapi.co*](https://swapi.co/) API and returns a list of the characters of the film received as parameter 
that have the same species as the character received as parameter.

## API Guide

### `jdtest` resource

#### Get character names by film id and character id

`GET /api/jdtest?film_id={filmId}&character_id={character_id}`

##### HTTP request

```
GET /api/jdtest?film_id=1&character_id=2 HTTP/1.1
Accept: application/json;charset=UTF-8
```

##### HTTP response

```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

[
  "C-3PO",
  "R2-D2",
  "R5-D4"
]
```

## Developing

### System Requirements

- Java 1.8+

### Building

```
$ ./mvnw clean install
```

### Running

```
$ java -jar target/starwiki-0.0.1-SNAPSHOT.jar
```

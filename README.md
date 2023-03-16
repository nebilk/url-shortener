# url-shortener
> Spring Boot Url Shortener Example Implementations

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies)
* [API List](#api-list)
* [Setup](#setup)
## General Information
- In this project you can basically shorten an url.

## Technologies
- Spring Boot - version 2.7.9
- Java - version 8
- PostgreSQL - version 14.1
- Lombok - version 1.18.24
- Redis Cache - version 6.2.7
- Guava - version 31.0.1-jre

## API list
**There are 2 API endpoints;**

>[ShortUrlController](/src/main/java/tr/com/nebildev/urlshortener/controller/ShortUrlController.java) /api/v1/shorty

- POST -> Create a Short URL and put it on REDIS cache and save to PostgreSQL database.
- GET /{code} -> Check the short code on first cache after Database and if it found, redirect the user to the desired URL.

## Setup

- $ mvn clean package
// generates the jar file

- docker-compose up -d 
// create REDIS, PostgreSQL, PGAdmin containers and run them on docker.
package com.mhp.coding.challenges.mapping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No article found for the requested id")
public class ArticleNotFoundException extends RuntimeException{
}

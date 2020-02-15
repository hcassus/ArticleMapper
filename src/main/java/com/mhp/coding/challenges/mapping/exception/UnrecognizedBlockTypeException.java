package com.mhp.coding.challenges.mapping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Article block type not mapped")
public class UnrecognizedBlockTypeException extends RuntimeException {
}

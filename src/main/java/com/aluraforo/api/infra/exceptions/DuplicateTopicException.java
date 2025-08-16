// src/main/java/com/aluraforo/api/infra/errors/DuplicateTopicException.java
package com.aluraforo.api.infra.exceptions;

public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException(String title) {
        super("Ya existe un tópico con el mismo título y mensaje: " + title);
    }
}
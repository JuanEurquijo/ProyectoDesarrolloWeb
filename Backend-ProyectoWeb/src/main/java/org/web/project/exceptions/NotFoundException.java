package org.web.project.exceptions;

public class NotFoundException extends RuntimeException {
    private Long id;

    public NotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

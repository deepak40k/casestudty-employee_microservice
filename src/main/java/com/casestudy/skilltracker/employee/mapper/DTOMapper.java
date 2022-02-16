package com.casestudy.skilltracker.employee.mapper;

public interface DTOMapper<T,R> {
    public T mapToDto(R r);
    public R mapToEntity(T t);
}

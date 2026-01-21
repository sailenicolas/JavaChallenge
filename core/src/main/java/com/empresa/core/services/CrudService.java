package com.empresa.core.services;

public interface CrudService<T,V, U> extends GetAllService<T>, GetService<T>, PostService<T, V>, PutService<T, U>, DeleteService<T>{
}

package com.manan.tracker.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manan.tracker.Entity.Todo;

public interface todorepo extends JpaRepository<Todo, Integer>{

}

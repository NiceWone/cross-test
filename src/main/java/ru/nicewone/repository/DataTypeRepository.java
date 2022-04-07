package ru.nicewone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.nicewone.entity.EventTypeEntity;

public interface DataTypeRepository extends JpaRepository<EventTypeEntity, Long> {

}

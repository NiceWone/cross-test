package ru.nicewone.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@NoArgsConstructor
@Data
public class EventTypeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long eventType;
    private String mainRegExp;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttribEntity> attribEntities = new ArrayList<>();
}

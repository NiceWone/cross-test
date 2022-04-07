package ru.nicewone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attrib")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AttribEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String attribName;
    private String attribRegExp;
}

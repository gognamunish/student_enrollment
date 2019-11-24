package com.blackone.gognamunish.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Student {

    protected @Id
    @GeneratedValue
    Integer id;

    @EqualsAndHashCode.Exclude
    private String firstName;
    @EqualsAndHashCode.Exclude
    private String lastName;
    @EqualsAndHashCode.Exclude
    private String studentClass;
    @EqualsAndHashCode.Exclude
    private String nationality;


}

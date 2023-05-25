package kr.ac.zeroco.ch04_shopping.domain.user.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="USER")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ID")
    private long id;

    @Column(name="NAME", columnDefinition = "varchar(100)")
    private String name;

    @Column(name="CITY", columnDefinition = "varchar(255)")
    private String city;

    @Column(name="STREET", columnDefinition = "varchar(255)")
    private String street;

    @Column(name="ZIPCODE", columnDefinition = "varchar(255)")
    private String zipcode;
}

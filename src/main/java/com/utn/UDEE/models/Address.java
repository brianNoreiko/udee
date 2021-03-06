
package com.utn.UDEE.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity (name = "addresses")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "address_id",unique = true,nullable = false)
    private Integer id;

    @NotNull(message = "The street shouldn't be null")
    @Column(name = "street")
    private String street;

    @NotNull(message = "The street number shouldn't be null")
    @Column(name = "number")
    private Integer number;

    @Column(name = "apartment")
    private String apartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "meterId")
    private Meter meter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rate")
    private Rate rate;


}
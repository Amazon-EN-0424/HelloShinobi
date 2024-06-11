package com.ironhack.helloshinobi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Clan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String village;

    @OneToMany(mappedBy = "clan", fetch = FetchType.EAGER)
    private Set<Ninja> ninjas = new HashSet<>();
}

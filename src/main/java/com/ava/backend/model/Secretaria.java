package com.ava.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SECRETARIA")
@Getter
@Setter
@NoArgsConstructor
public class Secretaria extends Pessoa {
}

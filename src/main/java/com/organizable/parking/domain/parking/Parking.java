package com.organizable.parking.domain.parking;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "parking")
@Entity(name = "Spot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Parking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeDono;
    private String placa;
    private String modeloVeiculo;

}

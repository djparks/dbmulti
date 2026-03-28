package net.parksy.dbmulti.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}

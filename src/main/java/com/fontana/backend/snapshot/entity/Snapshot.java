package com.fontana.backend.snapshot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Snapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Column(name="snapshot_index")
//    private Integer snapshot_index;
//    @Column(name="template_id")
//    private Integer template_id;


    private Integer duration;
    @Column(name="data")
    private byte[] data;

}

package com.fontana.backend.snapshot.entity;

/*import com.fontana.backend.template.entity.Template;*/
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Table(name="snapshots")
public class Snapshot {
    @Id
    private int id;
    private Byte value;

    Snapshot(){
        this.id = 0;
        this.value = 0;
    }

}
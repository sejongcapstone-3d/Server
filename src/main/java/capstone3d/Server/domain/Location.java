package capstone3d.Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private Double lat;

    private Double lon;
}
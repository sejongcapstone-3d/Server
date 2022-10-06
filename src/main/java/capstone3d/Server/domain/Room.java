package capstone3d.Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String empty_room_url;

    private String full_room_url;

    private Double room_width;

    private Double room_height;

    private Double room_depth;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
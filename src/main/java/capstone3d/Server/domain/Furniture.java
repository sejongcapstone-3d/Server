package capstone3d.Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Furniture {

    @Id
    @GeneratedValue
    @Column(name = "furniture_id")
    private Long id;

    private String category;

    private String furniture_url;
}
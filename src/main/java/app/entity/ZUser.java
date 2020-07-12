package app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ZUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long user_id;

    @Column(nullable = false)
    public String username;
    @Column(nullable = false)
    public String password;
    @Column(nullable = false, unique = true)
    public String email;


    @Transient
    public String passwordConfirmation;


    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<ShortURL> urls;

}

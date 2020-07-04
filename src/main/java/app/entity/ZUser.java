package app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ZUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long user_id;

    public String username;
    public String password;
    public String email;


    @Transient
    public String passwordConfirmation;


    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<ShortURL> urls;

}

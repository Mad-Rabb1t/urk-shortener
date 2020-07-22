package app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountVerifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long confirmation_id;

    @Column(nullable = false,unique = true)
    public String token;
    @Column(nullable = false)
    public LocalDateTime expirationDate;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "verifier", cascade = CascadeType.ALL)
    private ZUser user;
}

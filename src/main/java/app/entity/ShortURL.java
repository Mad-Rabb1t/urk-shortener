package app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long url_id;

    @Column(nullable = false)
    public int numOfVisits;
    @Column(columnDefinition = "text", nullable = false)
    public String fullURL;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public String shortURL;
    @Column(nullable = false)
    public String dateOfCreation;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_urls",
            joinColumns = {
                    @JoinColumn(
                            name = "id_url",
                            referencedColumnName = "url_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "id_user",
                            referencedColumnName = "user_id")})

    private ZUser user;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "short_url", cascade = CascadeType.ALL)
    private Collection<VisitDetails> visitDetails;
}

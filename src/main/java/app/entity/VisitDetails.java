package app.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class VisitDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long visit_id;
    @Column(nullable = false)
    public String visit_date;
    @Column(nullable = false)
    public String ip_address;
    @Column(nullable = false)
    public String browser_info;
    @Column(nullable = false)
    public String os;
    @Column(nullable = false)
    public String lat_lon;
    @Column(nullable = false)
    public String city_country;
    @Column(nullable = false)
    public String org;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "url_visit",
            joinColumns = {
                    @JoinColumn(
                            name = "id_visit",
                            referencedColumnName = "visit_id")},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "id_url",
                            referencedColumnName = "url_id")})
    private ShortURL short_url;
}
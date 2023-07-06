package uz.mkb.start_template.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mkb.start_template.audit.Auditable;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(Auditable.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String generatedToken;

    boolean revoked, expired;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    Date created = new Date();
    Date updated = new Date();
}
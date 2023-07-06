package uz.mkb.start_template.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {
    List<E> findByNameContainingIgnoreCase(String name);
}

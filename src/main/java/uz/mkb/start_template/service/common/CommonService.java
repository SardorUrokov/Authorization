package uz.mkb.start_template.service.common;

import org.springframework.data.domain.Page;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;

public interface CommonService<E extends AbstractEntity> {
    E save(E entity);
    E get(Long id);
    Page<E> list(Integer page);
    void delete(Long id);
    Page<E> search(String param, Integer page);
    E put(Long id, E entity);
}

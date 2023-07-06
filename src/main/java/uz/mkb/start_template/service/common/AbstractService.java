package uz.mkb.start_template.service.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;
import uz.mkb.start_template.repository.common.CommonRepository;

import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>> implements CommonService<E> {
    final R repository;

    @Override
    public E save(E entity) {
        entity.setCreated(new Date());
        entity.setUpdated(new Date());
        return repository.save(entity);
    }

    @Override
    public E get(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Page<E> list(Integer page) {
        List<E> list = repository.findAll();
        return getPageableList(list, page);
    }


    @Override
    public void delete(Long id) {
        E entity = repository.findById(id).get();
        repository.delete(entity);
    }

    @Override
    public Page<E> search(String param, Integer page) {
        List<E> searchedList = repository.findByNameContainingIgnoreCase(param);
        return getPageableList(searchedList, page);
    }

    @Override
    public E put(Long id, E entity) {
        E existingEntity = repository.findById(id).orElse(null);
        if (existingEntity != null){
            entity = repository.save(entity);
        }
        return entity;
    }

    private Page<E> getPageableList(List<E> list, Integer page) {
        if (page == null) {
            page = 0;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}

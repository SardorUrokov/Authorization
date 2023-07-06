package uz.mkb.start_template.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;
import uz.mkb.start_template.service.common.CommonService;

public interface CommonController<E extends AbstractEntity, S extends CommonService<E>> {
    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id);

    @PostMapping("/")
    ResponseEntity<?> save(@RequestBody E entity);

    @GetMapping("/")
    ResponseEntity<?> getList(@RequestParam(required = false) Integer page);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<?> searchByParam(@RequestParam(required = false) String param,
                                    @RequestParam(required = false) Integer page);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id,
                             @RequestBody E entity);
}

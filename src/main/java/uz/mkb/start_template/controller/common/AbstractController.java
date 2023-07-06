package uz.mkb.start_template.controller.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.mkb.start_template.model.response.ApiResponse;
import uz.mkb.start_template.model.response.ResponseObject;
import uz.mkb.start_template.service.common.CommonService;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>> implements CommonController<E, S> {
    final S service;

    @Override
    public ResponseEntity<?> getById(Long id) {
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Get Element", service.get(id)));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @Override
    public ResponseEntity<?> save(E entity) {
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Created", service.save(entity)));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @Override
    public ResponseEntity<?> getList(Integer page) {
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Get List", service.list(page)));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        service.delete(id);
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Deleted", "Element with id:" + id + " is deleted"));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @Override
    public ResponseEntity<?> searchByParam(String param, Integer page) {
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Search by param", service.search(param, page)));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @Override
    public ResponseEntity<?> update(Long id, E entity) {
        ApiResponse response = new ApiResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setPayload(new ResponseObject("Element with id:" + id + " updated", service.put(id, entity)));
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }
}

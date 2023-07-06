package uz.mkb.start_template.model.global;

import jdk.jfr.Description;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mkb.start_template.model.response.ApiResponse;
import uz.mkb.start_template.model.response.ResponseObject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GlobalGenericServiceImp {
    public ApiResponse getListOfEntity(List<?> list, Integer page){
        if (listNotEmpty(list)){
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Success", listPageable(list, page)));
        } else {
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Empty", "List is empty"));
        }
    }

    @Description("Возвращает 1 если лист не пустой")
    public boolean listNotEmpty(List<?> list){
         return list.size() > 0;
    }

    public Page<?> listPageable(List<?> list, Integer page){
        if (page == null){
            page = 0;
        }
        Pageable pageable;
        pageable = PageRequest.of(Objects.requireNonNullElse(page, 0), 10, Sort.by(Sort.Direction.DESC, "id"));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }


    public ApiResponse getById(Optional<?> optional) {
        return optional.map(o -> new ApiResponse(HttpStatus.OK, new ResponseObject("Success", o))).orElseGet(() -> new ApiResponse(HttpStatus.NOT_FOUND, new ResponseObject("Not Found", "Element Not Found")));
    }

    public ApiResponse responseCreatedStatus(String name) {
        return new ApiResponse(HttpStatus.CREATED, new ResponseObject("Created", "Element with name " + name + " is created"));
    }

    public ApiResponse responseEditedStatus(Long id) {
        return new ApiResponse( HttpStatus.OK, new ResponseObject("Success Updated","Element with id:" + id + " is updated"));
    }

    public ApiResponse responseOKStatus(Object o){
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Success", o));
    }

    public ApiResponse responseSuccessDelete(Long id) {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Element Deleted", "Element with id : [" + id + "] is deleted"));
    }

    public String getAuthEmployeeName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "SYSTEM";
    }

    public ApiResponse responseBadRequest(Object o) {
        return new ApiResponse(HttpStatus.BAD_REQUEST, new ResponseObject("Bad Request", o));
    }

}

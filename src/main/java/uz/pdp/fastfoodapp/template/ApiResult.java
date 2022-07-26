package uz.pdp.fastfoodapp.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 16.12.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private boolean success;
    private String message;
    private T data;
    private List<ErrorData> errors;

    public ApiResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
 public ApiResult( String message,boolean success,T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResult(T data) {
        this.success = true;
        this.data = data;
    }

    public ApiResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResult(boolean success, List<ErrorData> errors) {
        this.success = success;
        this.errors = errors;
    }

    public static <E> ApiResult<E> successResponse(E data) {
        return new ApiResult<>(true, data);
    }

    public static <E> ApiResult<E> successResponse(String message) {
        return new ApiResult<>(true, message);
    }


}

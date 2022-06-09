package uz.pdp.fastfoodapp.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String massage;

    private boolean isSuccess;

    private Object data;

    public ApiResponse(String message, boolean status) {
        this.massage = message;
        this.isSuccess = status;
    }
}

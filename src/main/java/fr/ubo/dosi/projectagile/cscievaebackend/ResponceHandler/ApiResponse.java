package fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler;


import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<ApiResponse<String>> error(String message) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(false, message, null));
    }

    public  static <T> ResponseEntity<ApiResponse<T>> error(String message, T data) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(false, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<String>> ok(String message) {
        return ResponseEntity.accepted().body(new ApiResponse<>(true, message, null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.accepted().body(new ApiResponse<>(true, message, data));
    }
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.accepted().body(new ApiResponse<>(true, "L'opération a été effectuée avec succès", data));
    }
}

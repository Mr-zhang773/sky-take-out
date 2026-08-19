package result;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Result <T>{
    private Integer code;
    private String message;
    private T data;
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

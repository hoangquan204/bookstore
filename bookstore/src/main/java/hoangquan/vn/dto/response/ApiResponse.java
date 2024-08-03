package hoangquan.vn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T>{
    int code;
    String message;
    T result;
}

package com.example.webbanhang.dto.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Khai báo cho json cho nó biết rằng là khi mà cái object này sang Json thì những cái field nào null thì nó sẽ không có kèm vào trong cái json
@JsonInclude(JsonInclude.Include.NON_NULL) // đối tượng này được tuần tự hóa thành JSON bằng Jackson (ObjectMapper), thuộc tính sẽ bị bỏ qua vì nó có giá trị null.
public class ApiResponse <T> {
    @Builder.Default // Đảm bảo trình tạo đặt giá trị mặc định cho mã
    private int code = 1000;
    private String message;
    private T result;
}

//JsonInclude.Include.NON_NULL
//Chỉ bao gồm các thuộc tính không có giá trị null trong JSON.
//Các thuộc tính có giá trị null sẽ bị loại bỏ khỏi JSON.
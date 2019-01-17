package study.apache.study.apache.bval;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Car {
    @NotNull(message = "用户信息不能为空")
    @Valid
    User user;
    @NotNull(message = "vin不能为空")
    String vin;
}

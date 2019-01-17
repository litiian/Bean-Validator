package study.apache.study.apache.bval;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {
    @NotNull(message="用户名不能为空")
    String name;
}

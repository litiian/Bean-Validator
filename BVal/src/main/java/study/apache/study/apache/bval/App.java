package study.apache.study.apache.bval;

import org.apache.bval.jsr303.ApacheValidationProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 弃用Hibernate Validator原因：
 * 1、Hibernate Validator 太大 13M(包含了文档，源码)；BVal 400多kb
 *
 * Spring mvc3.x 自带Validator
 *1、可以对表单提交的数据进行验证，验证器比较弱，需要手工编码if (null) else warn("不能空数据")
 */
public class App
{
    public static void main( String[] args )
    {
        //方式-如果只需要验证一个bean
       ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
       Validator validator = vf.getValidator();

       User user=new User();
       Set<ConstraintViolation<User>> constraintViolations= validator.validate(user);
        for(ConstraintViolation<User> constraintViolation:constraintViolations){
            System.out.println(constraintViolation.getMessage());
        }

        //方式二如果如要验证多个bean
        //Validator是线程安全的，并且ValidatorFactory的创建是有代价的，所以尽可能的复用Validator
        ValidatorFactory avf=Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
        Validator validator2= avf.getValidator();
        ValidatorBean(validator2);

        //方式三
        Validator validator3= MyValidatorFactory.SINGLE_INSTANCE.getValidator();
        ValidatorBean(validator3);
    }

    private static void ValidatorBean(Validator validator) {
        Car car = new Car();
        User user=new User();
        car.setUser(user);
        Set<ConstraintViolation<Car>> carConstraintViolations = validator.validate(car);
        for (ConstraintViolation<Car> constraintViolation : carConstraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}

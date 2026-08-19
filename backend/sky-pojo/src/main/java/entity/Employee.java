package entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("employee")
public class Employee {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String employeeName;
    private String employeeAccount;
    private String employeePhone;
    private String employeeGender;
    private String employeeIdNumber;
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;
    private Integer status;
}

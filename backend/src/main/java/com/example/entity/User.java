// 包名必须和实际路径一致：com.example.entity
//包声明语句
package com.example.entity;

//导入语句
import javax.persistence.*;//导入JPA注解
import java.util.Date;

//JPA注解 标识这一个类是一个实体类 并且这个类对应数据库当中的一个表
@Entity
//表名叫 user
@Table(name = "user")
//定义一个公共类 User
public class User {
    //Id 标识这个是主键
    @Id
    //GeneratedValue 是一个策略 一个方法 strategy = GenerationType.IDENTITY是说 主键自增
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 必须是IDENTITY（MySQL自增）
    //主键字段 使用包装类型 Integer（允许null）
    private Integer id;

    //Column 是配置数据库表的列属性 unique 是确保手机号码的唯一性 nullable 是说不能为空 length 长度为11位
    @Column(unique = true, nullable = false, length = 11)
    //字符串类型的手机号
    private String phone;

    //列属性 nullable 密码不能为空
    @Column(nullable = false)
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    // Getter & Setter
    //字段的封装
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}

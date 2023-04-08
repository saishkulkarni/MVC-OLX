package mvc_olx.dto;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import lombok.Data;

@Entity
@Data
@Component
public class Customer {

	@Id
	@SequenceGenerator(initialValue = 45612111, allocationSize = 1, sequenceName = "custid", name = "custid")
	@GeneratedValue(generator = "custid")
	int id;
	String name;
	long mobile;
	String gender;
	String password;
	Date dob;
	int age;
	double wallet;
	String address;

	@ManyToMany(cascade = CascadeType.ALL)
	List<Product> products;

}

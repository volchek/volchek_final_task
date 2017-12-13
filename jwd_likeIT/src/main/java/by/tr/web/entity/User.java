package by.tr.web.entity;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int PRIME = 31;

	private String surname;
	private String name;
	private String phone;
	private String email;

	public static enum Fields {
		// Later default values may be saved in xml file
		SURNAME("surname"), NAME("name"), PHONE("phone"), EMAIL("email");
		
		String value;
		
		private Fields(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	
	public User() {}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}

	
	@Override
	public int hashCode() {

		int result = 1;
		result = PRIME * result + ((email == null) ? 0 : email.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((phone == null) ? 0 : phone.hashCode());
		result = PRIME * result + ((surname == null) ? 0 : surname.hashCode());
	
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		
		User other = (User) obj;
		if (email == null) {
			if (other.email != null){
				return false;
			}
		} else if (!email.equals(other.email)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (phone == null) {
			if (other.phone != null){
				return false;
			}
		} else if (!phone.equals(other.phone)){
			return false;
		}
		if (surname == null) {
			if (other.surname != null){
				return false;
			}
		} else if (!surname.equals(other.surname)){
			return false;
		}
		
		return true;
	}
	
	
	@Override 
	public String toString(){
		return "User: " + "name=" + name +
				"; surname=" + surname +
				"; phone=" + phone +
				"; email=" + email;
	}
	
}
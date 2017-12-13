package by.tr.web.entity;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int PRIME = 31;

	private String surname;
	private String name;
	private String status;
	private String login;
	private String password;
	
	
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
	
	public void setLogin(String login){
		this.login = login;
	}
	
	public String getLogin(){
		return login;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return status;
	}

	
	@Override
	public int hashCode() {

		int result = 1;
		result = PRIME * result + ((surname == null) ? 0 : surname.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((login == null) ? 0 : login.hashCode());
		result = PRIME * result + ((password == null) ? 0 : password.hashCode());
		result = PRIME * result + ((status == null) ? 0 : status.hashCode());
	
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
		if (surname == null) {
			if (other.surname != null){
				return false;
			}
		} else if (!surname.equals(other.surname)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (login == null) {
			if (other.login != null){
				return false;
			}
		} else if (!login.equals(other.login)){
			return false;
		}
		if (password == null) {
			if (other.password != null){
				return false;
			}
		} else if (!password.equals(other.password)){
			return false;
		}
		if (status == null) {
			if (other.status != null){
				return false;
			}
		} else if (!status.equals(other.status)){
			return false;
		}
		return true;
	}
	
	
	@Override 
	public String toString(){
		return "User: " + "name=" + name +
				"; surname=" + surname +
				"; login=" + login +
				"; password=" + password +
				"; status=" + status;
	}
	
}
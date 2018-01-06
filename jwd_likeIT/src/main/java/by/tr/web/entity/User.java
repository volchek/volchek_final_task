package by.tr.web.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String stringFormat = "yyyy-MM-dd";

	private String surname;
	private String name;

	private Date birthday;
	private List<String> email;
	private String avatar;

	private String login;
	private String password;

	private String status;
	private Map<Language, Integer> languages;

	private boolean isAdmin;
	private boolean isBanned;

	public User() {
		email = new LinkedList<String>();
		languages = new HashMap<Language, Integer>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return surname;
	}
	
	public void setBirthday(Date date){
		birthday = date;
	}

	public void setBirthday(int date, int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		birthday = calendar.getTime();
	}

	public void setBirthday(String date, String stringFormat) {
		DateFormat format = new SimpleDateFormat(stringFormat, Locale.getDefault());
		try {
			birthday = format.parse(date);
		} catch (ParseException e) {
			// ADD logging
			birthday = null;
		}
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public List<String> getEmail() {
		return email;
	}

	public void addEmail(String email) {
		if (email != null && !email.isEmpty()) {
			this.email.add(email);
		}
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setLanguages(Map<Language, Integer> languages) {
		this.languages = languages;
	}

	public void addLanguage(Language language, int level) {
		languages.put(language, level);
	}

	public Map<Language, Integer> getLanguages() {
		return languages;
	}

	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setBanned(boolean banned) {
		this.isBanned = banned;
	}

	public boolean isBanned() {
		return isBanned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + (isBanned ? 1231 : 1237);
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((languages == null) ? 0 : languages.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		User other = (User) obj;
		if (isAdmin != other.isAdmin) {
			return false;
		}

		if (isBanned != other.isBanned) {
			return false;
		}

		if (surname == null) {
			if (other.surname != null) {
				return false;
			}
		} else if (!surname.equals(other.surname)) {
			return false;
		}

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		if (birthday == null) {
			if (other.birthday != null) {
				return false;
			}
		} else {
			Format formatter = new SimpleDateFormat(stringFormat);
			String s1 = formatter.format(birthday);
			String s2 = formatter.format(other.birthday);
			if (!s1.equals(s2)){
				return false;
			}
		}

		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}

		if (avatar == null) {
			if (other.avatar != null) {
				return false;
			}
		} else if (!avatar.equals(other.avatar)) {
			return false;
		}

		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}

		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}

		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}

		if (languages == null) {
			if (other.languages != null) {
				return false;
			}
		} else if (!languages.equals(other.languages)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "User: " + "surname=" + surname + 
				"; name=" + name + 
				"; birthday=" + birthday + 
				"; email=" + email
				+ "; avatarPath=" + avatar + 
				"; login=" + login + 
				"; password=" + password + 
				"; status=" + status
				+ "; languages=" + languages + 
				"; isAdmin=" + isAdmin + 
				"; isBanned=" + isBanned;
	}

}